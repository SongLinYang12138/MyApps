package com.bondex.ysl.pdaapp.util.net;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface NetApi {

    @FormUrlEncoded
    @POST("/wms/api/")
    Call<String> testHello(@Field("param")String hello);



    @FormUrlEncoded
    @POST("/wms/api/")
    Call<String> login(@Field("param")String param);

}
