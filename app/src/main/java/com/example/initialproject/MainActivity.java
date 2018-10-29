package com.example.initialproject;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private List<MainRecyclerView> myList = new ArrayList<>();
    private List<String> pageImageList = new ArrayList<>();
    private boolean lock = true;

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
                assert response != null;
                assert response.body() != null;

                String responseData = null;
                try {
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
                String title;
                String date = null;
                Info info;
                List<Info> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("stories"));
                    JSONArray array;
                    date = jsonObject.getString("date");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int id = object.getInt("id");
                        title = object.getString("title");
                        int type = object.getInt("type");
                        array = object.getJSONArray("images");
                        info = new Info(title, array.getString(0), type, id, date);
                        list.add(info);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainAdapter adapter = null;
                if (lock) {
                    for (int i = 0; i < 5; i++) pageImageList.add(list.get(i).getImageName());
                    ViewPager viewPager = findViewById(R.id.viewPage);
                    ViewPageAdapter viewPageAdapter = new ViewPageAdapter(MainActivity.this, pageImageList);
                    viewPager.setAdapter(viewPageAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
                    MainRecyclerView mainRecyclerView = new MainRecyclerView("今日热闻", list);
                    myList.add(mainRecyclerView);
                    adapter = new MainAdapter(myList);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                    lock=false;
                } else {
                    MainRecyclerView mainRecyclerView = new MainRecyclerView(date, list);
                    myList.add(mainRecyclerView);
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        init("https://news-at.zhihu.com/api/3/news/latest");
    }
}
