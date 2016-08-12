package com.powerleader.crm.nethelp;

import android.util.Base64;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import cz.msebera.android.httpclient.Header;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by ALiSir on 2016/8/2.
 */
public class RecordUpload {
    private final static String TAG = RecordUpload.class.getSimpleName();
    private File f ;
    private String name;
    private String url;
    private RxNetRec rxNetRec;

    public RecordUpload(File f, String name, String url) {
        this.f = f;
        this.name = name;
        this.url = url;
        //上传结果用RX反馈
        initRx();
    }

    private void initRx(){
        rxNetRec = RxNetRec.getRnrInit();
    }

    public void  upRecFile(){
        byte[] bs = readFile(f);
        //将录音的字节流加密成Base64字符流输出
        String bms = Base64.encodeToString(bs,0,bs.length,Base64.DEFAULT);
//        bms = URLEncoder.encode(bms,"UTF-8");
        RequestParams params = new RequestParams();
        params.put("redFile",bms);
        params.put("name",name);
        AsyncHttpClient client = new AsyncHttpClient();
        //TODO:开始文件上传
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.i(TAG, "onSuccess(请求成功回调值：):返回码 "+i+";返回头:"+headers.toString()+";返回内容:"+new String(bytes));
                rxNetRec.setNetRecObs(Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Log.i(TAG, "call(成功，开始回调)！ ");
                        subscriber.onNext("1:文件上传成功！");
                    }
                }));
                rxNetRec.getNetRecObs().subscribe(rxNetRec.getNetRecSub());
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e(TAG, "onFailure(请求失败回调值：): " + new String(bytes), throwable);
                rxNetRec.setNetRecObs(Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Log.i(TAG, "call(失败，开始回调)！ ");
                        subscriber.onNext("-1:文件上传失败！");
                    }
                }));
                rxNetRec.getNetRecObs().subscribe(rxNetRec.getNetRecSub());
            }
        });
    }


    private byte[] readFile(File fs){
        byte[] b = null;
        try{
            InputStream in = new FileInputStream(fs);
            b = new byte[(int)fs.length()];
            in.read(b);
            in.close();
        }catch (Exception e){
            e.printStackTrace();
            return b;
        }
        return b;
    }

}