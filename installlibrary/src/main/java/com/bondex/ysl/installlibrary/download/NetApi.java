package com.bondex.ysl.installlibrary.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;


public interface NetApi {


    @Streaming
    @GET("7253b41f4c6b6faf41fdb974a5c59147.jpg")
    Call<ResponseBody> downloadFile();

}
