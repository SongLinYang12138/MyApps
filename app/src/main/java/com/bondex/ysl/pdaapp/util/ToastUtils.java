package com.bondex.ysl.pdaapp.util;

import android.content.Context;
import android.widget.Toast;

import com.bondex.ysl.pdaapp.application.PdaApplication;

public class ToastUtils {

    public static void showToast(Context context,String msg) {

        if(context == null)return;
        if (CommonUtil.isNotEmpty(msg))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

}
