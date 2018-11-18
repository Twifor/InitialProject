package com.example.initialproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.initialproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//显示日报详情的activity
public class ContentActivity extends AppCompatActivity {

    private int ID;
    private String responseData=null;
    private ImageView imageView;
    private WebView webView;
    private TextView textView;
    private String title;

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
                    if (response != null) {
                        if (response.body() != null) {
                            responseData = response.body().string();
                        }
                    }
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
                if (str == null) {
                    Toast.makeText(ContentActivity.this, "没有网络 (≧ω≦)", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    JSONObject object = new JSONObject(str);
                    JSONArray imageArray = object.getJSONArray("images");
                    imageView = findViewById(R.id.contentImageView);
                    webView = findViewById(R.id.webView);
                    textView = findViewById(R.id.textView);
                    String content = object.getString("body");
                    content = content.replace("<img", "<img style=max-width:100%;height:auto");
                    //这一步使图片适应屏幕，用户体验++
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
        title = intent.getStringExtra("title");
        ID = intent.getIntExtra("id", 233);
        init();
    }
}
