package com.bondex.ysl.pdaapp.application;

import android.app.Application;
import android.content.Context;

import com.bondex.ysl.pdaapp.bean.loginebean.LoginBean;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class PdaApplication extends Application {


    public static LoginBean LOGINBEAN = null;

    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogAdapter(new AndroidLogAdapter());

        context = getApplicationContext();
    }
}
