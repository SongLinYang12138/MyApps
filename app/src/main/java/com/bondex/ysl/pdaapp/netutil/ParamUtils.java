package com.bondex.ysl.pdaapp.netutil;

import android.util.Log;

import com.bondex.ysl.pdaapp.util.Constant;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ParamUtils {

    private static final String APP_ID = "test01";
    private static final String APP_KEY = "test01";
    private static final String CHAREST = "utf-8";


    public static String getParams(String param, String methodName) {

        Gson gson = new Gson();
        String app_id = APP_ID;
        String app_key = APP_KEY;
        String charset = CHAREST;//编码格式，固定为utf-8
        long timestamp = System.currentTimeMillis();//时间戳

        String method = methodName;//方法名
        String business_param = param;//业务数据，根据实际请求参数定

        String str = "app_id=" + app_id +
                "&app_key=" + app_key +
                "&business_param=" + business_param +
                "&charset=" + charset +
                "&method=" + method +
                "&timestamp=" + timestamp;


        String sign = null;
        try {
            sign = MD5.to_MD5(URLEncoder.encode(str, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            sign = "";
            Log.i(Constant.TAG, "sign == null");
        }

        ApiParam apiParam = new ApiParam();
        apiParam.setApp_id(app_id);
        apiParam.setBusiness_param(business_param);
        apiParam.setCharset(charset);
        apiParam.setMethod(method);
        apiParam.setSign(sign);
        apiParam.setTimestamp(timestamp);

        param = gson.toJson(apiParam);


        return param;

    }

}
