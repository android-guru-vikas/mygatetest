package com.dev.mygatedemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dev.mygatedemo.utils.AppLogs;
import com.dev.mygatedemo.utils.AppToast;

public abstract class BaseActivity extends AppCompatActivity {
    public Context pContext;
    public AppToast pAppToast;
    public AppLogs pAppLogs;
    public String pTAG = BaseActivity.class.getName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initObjects();
    }

    private void initObjects() {
        pContext = this;
        pAppToast = AppToast.getInstance();
        pAppLogs = AppLogs.getInstance();
    }
}
