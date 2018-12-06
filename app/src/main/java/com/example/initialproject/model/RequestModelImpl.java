package com.example.initialproject.model;

import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class RequestModelImpl implements RequestModel {

    @Override
    public void getData(final RequestListener requestListener, final String url, int type) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://news-at.zhihu.com/api/3/news/").build();
        Api api = retrofit.create(Api.class);
        Call<ResponseBody> call = null;
        if (type == 1) call = api.getLatestData(url);
        else call = api.getAddData(url);
        Log.d("Impor", "访问" + url);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        requestListener.onSuccess(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        requestListener.onFail();
                    }
                } else {
                    requestListener.onFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                requestListener.onFail();
            }
        });
    }
}
