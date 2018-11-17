package com.example.initialproject;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private List<Info> myList = new ArrayList<>();//主内容列表
    private List<Info> topList = new ArrayList<>();//热门内容列表
    private MainAdapter adapter;
    private boolean lock = true;
    public String currentDate = "";

    public void init(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
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
                show(responseData);
            }
        }).start();
    }

    public void show(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String title;
                String date = null;
                int id;
                if(str==null){
                    Toast.makeText(MainActivity.this, "没有网络 (≧ω≦)", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("stories"));
                    JSONArray array;
                    date = jsonObject.getString("date");
                    if (lock) {
                        JSONArray jsonArray1 = new JSONArray(jsonObject.getString("top_stories"));
                        for (int i = 0; i < 5; i++) {
                            topList.add(new Info(jsonArray1.getJSONObject(i).getString("title"),
                                    jsonArray1.getJSONObject(i).getString("image"),
                                    jsonArray1.getJSONObject(i).getInt("id"),
                                    date,
                                    ItemType.CONTENT
                            ));
                        }
                    }
                    if (currentDate.equals(date)) return;
                    myList.add(new Info("", "", 0, date, ItemType.DATE));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        id = object.getInt("id");
                        title = object.getString("title");
                        array = object.getJSONArray("images");
                        myList.add(new Info(title, array.getString(0), id, date, ItemType.CONTENT));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentDate = date;
                if (lock) {
                    ViewPager viewPager = findViewById(R.id.viewPage);
                    ViewPageAdapter viewPageAdapter = new ViewPageAdapter(MainActivity.this, topList);
                    viewPager.setAdapter(viewPageAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
                    adapter = new MainAdapter(myList);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                    lock = false;
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setLayout() {
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lock = true;
                myList.clear();
                topList.clear();
                init("https://news-at.zhihu.com/api/3/news/latest");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        ViewPager viewPager = findViewById(R.id.viewPage);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        swipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        swipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        findViewById(R.id.mainRecyclerView).setNestedScrollingEnabled(false);
        MyScrollView scrollView = findViewById(R.id.myScrollView);
        scrollView.setMainActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        setLayout();
        init("https://news-at.zhihu.com/api/3/news/latest");
    }
}
