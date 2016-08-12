package com.powerleader.crm.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Window;
import android.view.WindowManager;

import rx.observers.Observers;
import rx.observers.Subscribers;

/**
 * Created by ALiSir on 2016/7/11.
 */
public class MyActivity extends Activity {
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去掉标题栏
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //去掉状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //禁止横屏
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //设置当点击输入框时，自动往上浮
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);

        mContext = this.getContext();
    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    protected void onResume() {
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }
}
