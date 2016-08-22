package com.powerleader.crm;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.powerleader.crm.jca.JSObject;
import com.powerleader.crm.nethelp.CallBackToWeb;
import com.powerleader.crm.service.CrmLbs;
import com.powerleader.crm.view.MyActivity;
import com.powerleader.crm.view.MyWebView;

public class MainActivity extends MyActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MyWebView mvb;
    private static FloatingActionButton fab;
    public static String lbStr = "-1";
    private Intent mIntent;
    private String errorUrl = "file:///android_asset/error/index.html";
    private String indexUrl = "http://192.168.1.102:8080/Retrofit_test/lbs.html";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x123:
                    fab.show();
                    break;
                case 0x234:
                    fab.hide();
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initRec();
        //启动定位服务
        startLBS();
        //初始化WebView设置
        initWVSet();
        //初始化android调用网页类
        CallBackToWeb callBackToWeb = new CallBackToWeb(mvb);
        //初始化加载界面
        initLoadView();

        mvb.setWebChromeClient(new WebChromeClient());

        mvb.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                WebSettings wss = view.getSettings();
                wss.setLoadWithOverviewMode(true);
                wss.setUseWideViewPort(true);
                view.loadUrl(errorUrl);
            }

             @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try{
                    StartLoadActivity sla = StartLoadActivity.getSla();
                    sla.finish();
                }catch (Exception e){
                    Log.e(TAG, "onPageFinished(页面加载完成，关闭正在加载界面异常...): ",e );
                }
            }
        });

//        mvb.setWebChromeClient(new WebChromeClient());
//        String url = "http://192.168.215.83/oa";
       String url = "http://crm.plcdn.com.cn/";
//        String url = "https://www.baidu.com/";
//            String url = "file:///android_asset/error/index.html"     ;
//        String url = "file:///android_asset/lbs_test.html";
        indexUrl = url;
        mvb.loadUrl(indexUrl);

    }

    private void initLoadView(){
        Intent mIntent = new Intent();
        mIntent.setClass(MainActivity.this,StartLoadActivity.class);
        startActivity(mIntent);
    }

    @Override
    protected void onResume() {
        mvb.loadUrl(indexUrl);
        //测试用
        fab.hide();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        stopService(mIntent);
        super.onDestroy();
    }

    private void startLBS(){
        mIntent = new Intent(MainActivity.this, CrmLbs.class);
        startService(mIntent);
    }

    private void initView(){
        mvb = (MyWebView) findViewById(R.id.webView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initRec(){
        ActionClick ac = null;
        try{
            ac = new ActionClick(fab.getContext());
        }catch (Exception e){
            //权限不足，请确认已经开启录音权限
            Log.e(TAG, "initView(录音权限不足): ",e );
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = null;
                    if(toast == null){
                        toast = Toast.makeText(MainActivity.this,"权限不足，请设置应用允许录音！",Toast.LENGTH_LONG);
                    }
                    toast.show();
                }
            });
            fab.hide();
            return;
        }
        fab.setOnLongClickListener(ac);
        fab.setOnTouchListener(ac);
        fab.hide();
    }

    private void initWVSet(){
        WebSettings ws = mvb.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setUserAgentString("ACRMBrowser");
        //配置JS调用android程序
        JSObject jso = new JSObject(MyActivity.getContext(),mHandler);
        mvb.addJavascriptInterface(jso,"jca");
    }

}
