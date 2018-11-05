package com.bondex.ysl.pdaapp.util;

import android.widget.Toast;

import com.bondex.ysl.pdaapp.application.PdaApplication;

public class ToastUtils {

    public static void showToast(String msg) {

        if (CommonUtil.isNotEmpty(msg))
            Toast.makeText(PdaApplication.context, msg, Toast.LENGTH_SHORT).show();

    }

}
