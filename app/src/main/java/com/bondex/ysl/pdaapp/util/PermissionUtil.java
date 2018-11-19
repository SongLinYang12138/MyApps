package com.bondex.ysl.pdaapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class PermissionUtil {

    public static final int READ_REQUEST = 1000;
    public static final int WRITE_REQUEST = 1001;



    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void checkPermission(Activity context) {

        if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            context.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_REQUEST);
        }
    }
}
