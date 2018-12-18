package com.example.initialproject.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import java.io.IOException

open class RequestModelImpl : RequestModel {
    override fun getData(requestListener: com.example.initialproject.model.RequestListener, url: String, type: Int) {
        val retrofit = Retrofit.Builder().baseUrl("https://news-at.zhihu.com/api/3/news/").build()
        val api = retrofit.create(Api::class.java)
        val call: Call<ResponseBody>?
        call = if (type == 1)
            api.getLatestData(url)
        else
            api.getAddData(url)
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        requestListener.onSuccess(response.body().string())
                    } catch (e: IOException) {
                        e.printStackTrace()
                        requestListener.onFail()
                    }

                } else {
                    requestListener.onFail()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                requestListener.onFail()
            }
        })
    }
}