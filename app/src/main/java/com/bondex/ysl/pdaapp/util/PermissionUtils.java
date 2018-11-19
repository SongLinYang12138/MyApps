package com.bondex.ysl.pdaapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * date: 2018/11/14
 * Author: ysl
 * description:
 */
public class PermissionUtils {

    private static final int READ_PERMISSION_REQUEST = 111;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void checkpermisssion(Activity context) {

        if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            context.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, READ_PERMISSION_REQUEST);
        }
    }


}
