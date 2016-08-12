package com.powerleader.crm;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.powerleader.crm.record.RecordMain;

/**
 * Created by ALiSir on 2016/7/18.
 */
public class ActionClick implements View.OnLongClickListener,View.OnTouchListener{
    private static final String TAG = ActionClick.class.getSimpleName();
    private Context context;
    //TODO:添加录音文件名，测试用文件上传路径
    private String url = "http://192.168.1.102:8080/Retrofit_test/retrofit.do";
    private RecordMain rm;
    //上滑取消
    private float startY = 0;
    private float endY = 0;

    public ActionClick(Context context) {
        this.context = context;
        //TODO:上传文件名和路径待定
        rm =  new RecordMain("test.3pg",url);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch(点击点为): X="+event.getX()+"Y="+event.getY());
        if(event.getAction() == MotionEvent.ACTION_UP){
            endY = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            startY = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP && rm.getSave() && (startY - endY < 120) ){
            Log.i(TAG, "onTouch(停止录音，录音成功，并上传录音文件！): ");
            rm.stopRecord();
            rm.upRecFile();
        }else if(event.getAction() == MotionEvent.ACTION_UP && rm.getSave() && (startY - endY >= 120)  ){
            Log.i(TAG, "onTouch(上滑取消录音...): ");
            rm.stopRecord();
        }
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        Log.i(TAG, "onLongClick(长按开始录音):录音开始... ");
        try {
            rm.setF();
            rm.startMyRM();
            rm.setSave(true);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "onLongClick(录音开始异常): ",e );
        }
        return true;
    }

}
