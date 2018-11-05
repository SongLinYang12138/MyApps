package com.bondex.ysl.pdaapp.util.net;

import android.util.Log;

import com.bondex.ysl.pdaapp.util.netutil.ApiParam;
import com.bondex.ysl.pdaapp.util.netutil.MD5;
import com.bondex.ysl.pdaapp.util.netutil.ParamUtils;
import com.bondex.ysl.pdaapp.util.Constant;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpConnection {

    private static final String BASE_URL = "http://wol.bondex.com.cn:8089/";

    public static Retrofit getRretrofit(String url) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit;
    }


    public static Call<String> testHello(String hello) {

        Gson gson = new Gson();
        String app_id = "test01";
        String app_key = "test01";
        String charset = "utf-8";//编码格式，固定为utf-8
        long timestamp = System.currentTimeMillis();//时间戳

        String method = "hello";//方法名
        String business_param = "test123";//业务数据，根据实际请求参数定

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

        hello = gson.toJson(apiParam);

        NetApi netApi = getRretrofit(BASE_URL).create(NetApi.class);
        return netApi.testHello(hello);
    }


    public static Call<String> login(String name, String password) {

        JSONObject json = new JSONObject();
        try {
            json.put("userid",name);
            json.put("pwd",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String param = json.toString();
        param = ParamUtils.getParams(param, "login");
        Logger.i("登录参数 "+param);
        NetApi netApi = getRretrofit(BASE_URL).create(NetApi.class);

        return netApi.testHello(param);
    }


}
