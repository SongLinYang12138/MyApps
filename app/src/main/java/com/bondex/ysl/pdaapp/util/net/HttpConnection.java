package com.bondex.ysl.pdaapp.util.net;

import android.util.Log;

import com.bondex.ysl.pdaapp.application.PdaApplication;
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
import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpConnection {

    private static final String BASE_URL = "http://wol.bondex.com.cn:8089/";
    private static final String VERSION_URL = "http://cas.bondex.com.cn:8080/";
    private static final OkHttpClient httpClient = new OkHttpClient
            .Builder()
            .connectTimeout(30000, TimeUnit.SECONDS)
            .build();

    public static Retrofit getRretrofit(String url) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient)
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
        return netApi.connect(hello);
    }


    public static Call<String> login(String name, String password) {

        JSONObject json = new JSONObject();
        try {
            json.put("userid", name);
            json.put("pwd", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String param = json.toString();
        param = ParamUtils.getParams(param, "login");
        Logger.i("登录参数 " + param);
        NetApi netApi = getRretrofit(BASE_URL).create(NetApi.class);

        return netApi.connect(param);
    }

    public static Call<String> traceId(String traceId, int num) {

        String method = "inv.MovementBefore";//方法名
        JSONObject object = new JSONObject();
        try {
            object.put("userid", PdaApplication.LOGINBEAN.getUserid());

            object.put("warehouseno", num);//此处为仓库编号，成都为1，烟台为2这样
            object.put("traceid", traceId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String param = object.toString();

        param = ParamUtils.getParams(param, method);

        NetApi netApi = getRretrofit(BASE_URL).create(NetApi.class);
        return netApi.connect(param);
    }


    public static Call<String> removeStowrage(String traceId, int num, String stowrage) {

        String method = "inv.movementID";

        JSONObject object = new JSONObject();
        try {
            object.put("locationid", stowrage);
            object.put("userid", PdaApplication.LOGINBEAN.getUserid());
            object.put("warehouseno", 1);
            object.put("traceid", traceId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String param = object.toString();
        param = ParamUtils.getParams(param, method);

        NetApi netApi = HttpConnection.getRretrofit(BASE_URL).create(NetApi.class);
        return netApi.connect(param);
    }

    public static Call<String> consigement(String id, int num) {

        String method = "so.shipment";//方法名

        JSONObject map = new JSONObject();

        try {
            map.put("warehouseno", Integer.valueOf(num));//当前登录用户选择的仓库ID
            map.put("Action", "Ship");//固定值
            map.put("ProcessBy", "OrderNo");//固定值
            map.put("userid", PdaApplication.LOGINBEAN.getUserid());//系统当前登录人ID
            map.put("OrderNO", id);//第一步扫描的发运单号

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = map.toString();
        params = ParamUtils.getParams(params, method);

        NetApi netApi = HttpConnection.getRretrofit(BASE_URL).create(NetApi.class);
        return netApi.connect(params);
    }

    public static Call<String> getVersion(){

        NetApi netApi = HttpConnection.getRretrofit(VERSION_URL).create(NetApi.class);
        return netApi.getVersion();
    }


}
