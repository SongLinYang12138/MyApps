package com.bondex.ysl.pdaapp.util.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public interface NetApi {

    @FormUrlEncoded
    @POST("wms/api/")
    Call<String> connect(@Field("param")String param);



    @FormUrlEncoded
    @POST("wms/api/")
    Call<String> login(@Field("param")String param);


    @GET("appversion?type=wms")
    Call<String> getVersion();


    @Streaming
    @GET("7253b41f4c6b6faf41fdb974a5c59147.jpg")
    Call<ResponseBody> downloadFile();

}
