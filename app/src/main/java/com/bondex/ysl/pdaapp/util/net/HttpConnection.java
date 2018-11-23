package com.bondex.ysl.pdaapp.util.net;

import com.bondex.ysl.pdaapp.application.PdaApplication;
import com.bondex.ysl.pdaapp.util.netutil.ParamUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpConnection {

    private static final String BASE_URL = "http://wol.bondex.com.cn:8089/";
    private static final String VERSION_URL = "http://pubdoc.bondex.com.cn:8087/fileking/app/";


    private static final OkHttpClient httpClient = new OkHttpClient
            .Builder()
            .connectTimeout(30000, TimeUnit.SECONDS)
            .build();

    public static NetApi getRretrofit(String url) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        return retrofit.create(NetApi.class);

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

        return getRretrofit(BASE_URL).connect(param);
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
        return getRretrofit(BASE_URL).connect(param);
    }


    public static Call<String> removeStowrage(String traceId, int num, String stowrage) {

        String method = "inv.movementID";

        JSONObject object = new JSONObject();
        try {
            object.put("locationid", stowrage);
            object.put("userid", PdaApplication.LOGINBEAN.getUserid());
            object.put("warehouseno", num);
            object.put("traceid", traceId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String param = object.toString();
        param = ParamUtils.getParams(param, method);

        return getRretrofit(BASE_URL).connect(param);
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

        return getRretrofit(BASE_URL).connect(params);
    }

    public static Call<String> getVersion() {


        return getRretrofit(VERSION_URL).getVersion();
    }

    public static Call<String> getCall(String params){

        return getRretrofit(BASE_URL).connect(params);
    }


}
