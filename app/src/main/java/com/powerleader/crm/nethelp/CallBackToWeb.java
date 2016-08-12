package com.powerleader.crm.nethelp;

import android.util.Log;
import android.webkit.WebView;

import rx.Subscriber;

/**
 * Created by ALiSir on 2016/8/3.
 */
public class CallBackToWeb {
    private static  final String TAG = CallBackToWeb.class.getSimpleName();
    private WebView wv;

    public CallBackToWeb(WebView wv) {
        this.wv = wv;
        rcdRxCallBack();
    }

    //录音文件上传结果
    private void rcdRxCallBack(){
        RxNetRec rxNetRec = RxNetRec.getRnrInit();
        rxNetRec.setNetRecSub(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError(回调失败): ",e);
                wv.loadUrl("javascript:recCallBack('-1:文件上传失败！')");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext(回调成功): "+s);
                wv.loadUrl("javascript:recCallBack('"+s+"')");
            }
        });

    }

}
