package com.bondex.ysl.pdaapp.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

public class CommonUtil {

    public static boolean isEmpty(String str){

        if(str == null || str.isEmpty())  return true;
        return false;
    }

    public static boolean isNotEmpty(String str){

        if(str == null || str.isEmpty()) return false;
        return true;
    }

    public static int getScreentW(Context context){

        DisplayMetrics dp  = context.getResources().getDisplayMetrics();
        int width = dp.widthPixels;

        return width;
    }

    public static int getScreenH(Context context){

        DisplayMetrics dp = context.getResources().getDisplayMetrics();
        int height = dp.heightPixels;
        return height;
    }


}
