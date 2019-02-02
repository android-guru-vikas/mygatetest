package com.dev.mygatedemo;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.dev.mygatedemo.realmdatabase.RealmController;

public class MyGateApplication extends Application {

    private static final String TAG = "MyGateApplication";
    private static MyGateApplication applicationInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationInstance = this;



        RealmController.getInstance().initRealm(applicationInstance);
    }

    public static MyGateApplication getInstance() {
        return applicationInstance;
    }


    public static Context getAppContext() {
        return applicationInstance.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
