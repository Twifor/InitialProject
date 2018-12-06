package com.example.initialproject.model;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    @GET("{id}")
    Call<ResponseBody> getLatestData(@Path("id") String url);

    @GET("before/{id}")
    Call<ResponseBody> getAddData(@Path("id") String url);
}
