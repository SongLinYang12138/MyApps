package com.bondex.ysl.pdaapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.ToastUtils;

public class SharedPreferecneUtils {


    public static void saveValue(Context context,String name,String key,String value){

        if(context == null) {
            return;
        }
        if(CommonUtil.isEmpty(key) || CommonUtil.isEmpty(value)){
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key,value);
        editor.commit();
        editor.clear();
    }

    public static String getValue(Context context,String name,String key){

        SharedPreferences preferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return preferences.getString(key,"");
    }

}