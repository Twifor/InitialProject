package com.example.initialproject.model;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestModelImplement implements RequestModel {

    @Override
    public void getData(final RequestListener requestListener, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();//发起网络请求
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String responseData = null;
                if (response != null) {
                    try {
                        if (response.body() != null) {
                            responseData = response.body().string();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (responseData == null) requestListener.onFail();
                else requestListener.onSuccess(responseData);
            }
        }).start();
    }
}
