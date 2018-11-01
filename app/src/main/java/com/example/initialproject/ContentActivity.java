package com.example.initialproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class ContentActivity extends AppCompatActivity {

    private int ID;
    private String responseData;
    private ImageView imageView;
    private WebView webView;
    private TextView textView;
    private String title;

    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Impor","New");
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
                    webView = findViewById(R.id.webView);
                    textView = findViewById(R.id.textView);
                    String content = object.getString("body");
                    content = content.replace("<img", "<img style=max-width:100%;height:auto");
                    Glide.with(ContentActivity.this).load(imageArray.getString(0)).into(imageView);
                    webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
                    textView.setText(title);
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
        title = intent.getStringExtra("title");
        init();
    }
}
