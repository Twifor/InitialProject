package com.example.initialproject.model

import kotlinx.coroutines.experimental.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import java.io.IOException

open class RequestModelImpl : RequestModel {
    override fun getData(requestListener: com.example.initialproject.model.RequestListener, url: String, type: Int) {
        val api = Retrofit
                .Builder()
                .baseUrl("https://news-at.zhihu.com/api/3/news/")
                .build()
                .create(Api::class.java)
        val call: Call<ResponseBody>?
        call = if (type == 1)
            api.getLatestData(url)
        else
            api.getAddData(url)
        launch {//协程
            val response: ResponseBody?
            try {
                response = call.execute().body()
                requestListener.onSuccess(response.string())
            } catch (e: IOException) {
                e.printStackTrace()
                requestListener.onFail()
            }
        }
    }
}