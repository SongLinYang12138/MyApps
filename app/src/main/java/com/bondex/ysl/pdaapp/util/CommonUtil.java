package com.bondex.ysl.pdaapp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.Display;

public class CommonUtil {

    public static boolean isEmpty(String str) {

        if (str == null || str.isEmpty()) return true;
        return false;
    }

    public static boolean isNotEmpty(String str) {

        if (str == null || str.isEmpty()) return false;
        return true;
    }

    public static int getScreentW(Context context) {

        DisplayMetrics dp = context.getResources().getDisplayMetrics();
        int width = dp.widthPixels;

        return width;
    }

    public static int getScreenH(Context context) {

        DisplayMetrics dp = context.getResources().getDisplayMetrics();
        int height = dp.heightPixels;
        return height;
    }

    public static String getVersionName(Context context) {

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), 0);

            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getVersionCode(Context context) {

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public static boolean isNumber(String str){

        try {

            Double.valueOf(str);
            return true;
        }catch (Exception e){

            return false;
        }
    }

}
