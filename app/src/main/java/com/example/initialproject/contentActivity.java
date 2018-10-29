package com.example.initialproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class contentActivity extends AppCompatActivity {

    private int ID;
    private String responseData;
    private ImageView imageView;

    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("https://news-at.zhihu.com/api/3/news/" + ID).build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    assert response != null;
                    assert response.body() != null;
                    responseData = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                show(responseData);
            }
        }).start();
    }

    public void show(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject object = new JSONObject(str);
                    JSONArray imageArray = object.getJSONArray("images");
                    imageView = findViewById(R.id.contentImageView);
                    WebView webView = findViewById(R.id.webView);
                    Glide.with(contentActivity.this).load(imageArray.getString(0)).into(imageView);
                    webView.loadDataWithBaseURL(null, object.getString("body"), "text/html", "utf-8", null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contentlayout);
        Intent intent = getIntent();
        ID = intent.getIntExtra("id", 233);
        init();
    }
}
