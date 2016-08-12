package com.powerleader.crm.jca;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.powerleader.crm.MainActivity;

/**
 * Created by ALiSir on 2016/7/15.
 */
public class JSObject {
    private static final String TAG = JSObject.class.getSimpleName();
    private Context context;
    private Handler mHandler;

    public JSObject(Context context,Handler mHandler) {
        this.context = context;
        this.mHandler = mHandler;
    }

    @JavascriptInterface
    public String JSCallAndroid(){
        return MainActivity.lbStr;
    }

    @JavascriptInterface
    public boolean JSCallAndForRec(){
        try{
           mHandler.sendEmptyMessage(0x123);
            Log.i(TAG, "JSCallAndForRec(调出录音按钮事件): 显示按钮！");
            return true;
        }catch (Exception e){
            Log.e(TAG, "JSCallAndForRec(显示按钮异常): ",e);
            return false;
        }
    }

    @JavascriptInterface
    public boolean JCAEndRecBtn(){
        try{
            mHandler.sendEmptyMessage(0x234);
            Log.i(TAG, "JSCallAndForRec(隐藏按钮): 隐藏按钮！");
            return true;
        }catch (Exception e){
            Log.e(TAG, "JSCallAndForRec(隐藏按钮异常): ",e);
            return false;
        }
    }

}
