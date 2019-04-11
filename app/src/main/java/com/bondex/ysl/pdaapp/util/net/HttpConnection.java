package com.bondex.ysl.pdaapp.util.net;


import com.bondex.ysl.pdaapp.bean.HttpRequestParam;
import com.bondex.ysl.pdaapp.util.interf.HtppReuquest;
import com.bondex.ysl.pdaapp.util.netutil.ParamUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpConnection {

        private static final String BASE_URL = "http://wol.bondex.com.cn:8089/";
    private static final String VERSION_URL = "http://pubdoc.bondex.com.cn:8087/fileking/app/";
//    private static final String BASE_URL = "http://172.16.75.43:8084/";


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

        String method = "login";
        String param = json.toString();
        param = ParamUtils.getParams(param, method);
        Logger.i("登录参数 " + param);

        return getRretrofit(BASE_URL).connect(param);
    }


    public static Call<String> getVersion() {


        return getRretrofit(VERSION_URL).getVersion();
    }

    private static Call<String> getCall(String params) {

        return getRretrofit(BASE_URL).connect(params);
    }

    public static void connect(String params, HtppReuquest request) {


        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                getCall(params).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {


                        if (response.body() == null) {

                            emitter.onNext("N");
                        } else {

                            Logger.i("" + response.body());
                            emitter.onNext(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        emitter.onNext("N");
                    }
                });
            }
        });
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {


                if (s.equals("N")) {

                    request.httpError("当前没有数据");
                } else {

                    Gson gson = new Gson();
                    HttpRequestParam param = gson.fromJson(s, HttpRequestParam.class);

                    if (param.isSuccess()) {
                        request.httpSuccess(param);
                    } else {
                        request.httpError(param.getErrormsg());
                    }

                }

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);


    }


}
