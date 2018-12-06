package com.bondex.ysl.pdaapp.application;

import android.app.Application;
import android.content.Context;

import com.bondex.ysl.pdaapp.bean.loginebean.LoginBean;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class PdaApplication extends Application implements Thread.UncaughtExceptionHandler {


    public static LoginBean LOGINBEAN = null;

    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogAdapter(new AndroidLogAdapter());

        context = getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        String result = getStackTrace(e);
        Logger.i(" 异常 "+result);
    }

    private String getStackTrace(Throwable th) {

        final Writer result = new StringWriter();

        final PrintWriter printWriter = new PrintWriter(result);

        // If the exception was thrown in a background thread inside

        // AsyncTask, then the actual exception can be found with getCause

        Throwable cause = th;

        while (cause != null) {

            cause.printStackTrace(printWriter);

            cause = cause.getCause();

        }
        final String stacktraceAsString = result.toString();

        printWriter.close();

        return stacktraceAsString;
    }

}
