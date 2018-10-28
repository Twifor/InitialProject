package com.example.initialproject;

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

    private List<Info> myList = new ArrayList<>();

    public void init() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://news-at.zhihu.com/api/3/news/latest").build();
        Log.d("Impor","233");
        String responseData;
        String title;
        Log.d("Impor","2334");
        Response response = client.newCall(request).execute();
        Log.d("Impor","2335");
        assert response.body() != null;
        responseData = response.body().toString();
        Log.d("Impor", responseData);
        Info info;
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("stories"));
            int date = jsonObject.getInt("date");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt("id");
                title = object.getString("title");
                int type = object.getInt("type");
                info = new Info(title, R.drawable.temp, type, id, date);
                myList.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Impor","The program is running");
        setContentView(R.layout.mainlayout);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        InfoAdapter adapter = new InfoAdapter(myList);
        recyclerView.setAdapter(adapter);

    }
}
