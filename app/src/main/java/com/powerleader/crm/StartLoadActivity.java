package com.powerleader.crm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.powerleader.crm.view.MyActivity;

public class StartLoadActivity extends MyActivity {
    private static StartLoadActivity sla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_load);
        sla = this;
    }

    public static StartLoadActivity getSla(){
        return sla;
    }

}
