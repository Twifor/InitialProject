package com.example.initialproject.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface Api{
    @GET("{id}")
    fun getLatestData(@Path("id") url: String): Call<ResponseBody>

    @GET("before/{id}")
    fun getAddData(@Path("id") url: String): Call<ResponseBody>
}