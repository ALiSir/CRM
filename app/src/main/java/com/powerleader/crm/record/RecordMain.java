package com.powerleader.crm.record;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.powerleader.crm.nethelp.RecordUpload;
import com.powerleader.crm.view.MyActivity;
import java.io.File;

/**
 * Created by ALiSir on 2016/8/2.
 */
public class RecordMain {
    private static final String TAG = RecordMain.class.getSimpleName();
    private Context mContext;
    private MediaRecorder myMR;
    private String perEr = "当前无法录音，请设置录音权限！";
    private boolean isSave = true;//是否保存
    private static final String AudioName = "/a.3pg";//本地录音文件存储名
    private File f;//录音文件路径
    private String fileName;//文件名
    private String url;//文件上传路径

    public RecordMain(String fileName,String url){
        this.fileName = fileName;
        this.url = url;
        mContext = MyActivity.getContext();
        f = findFile();
        myMR = new MediaRecorder();
        initMyRM();
    }

    private void initMyRM(){
            myMR.setAudioSource(MediaRecorder.AudioSource.MIC);
            myMR.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myMR.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            myMR.setOutputFile(f.getPath());
    }

    public void setF(){
        f = findFile();
    }

    private File findFile(){
        String filePath = Environment.getExternalStorageDirectory().getPath();
        File fs = new File(filePath + "/crm");
        if(!fs.exists()){
            fs.mkdirs();
            Log.i(TAG, "创建文件："+fs.mkdirs());
        }
        File file = new File(fs.getPath()+ AudioName);
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        }catch (Exception e){
            Log.e(TAG, "findFile: " + e.toString());
            e.printStackTrace();
        }
    return file;
    }

    public void setSave(boolean isSaves){
        this.isSave = isSaves;
    }

    public boolean getSave(){
        return isSave;
    }

    public void upRecFile(){
        RecordUpload ru = new RecordUpload(f,fileName,url);
        ru.upRecFile();
    }

    public void stopRecord(){
        //TODO:值是单纯停止，没有释放资源
        myMR.stop();
    }

    public void startMyRM()throws Exception{
        myMR.reset();
        initMyRM();
        myMR.prepare();
        myMR.start();
    }

}
