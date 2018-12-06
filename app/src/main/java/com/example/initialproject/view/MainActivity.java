package com.example.initialproject.view;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.initialproject.R;
import com.example.initialproject.model.Info;
import com.example.initialproject.presenter.PresenterImpl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//主activity
public class MainActivity extends AppCompatActivity implements com.example.initialproject.view.MyView {
    private List<Info> myList = new ArrayList<>();//主内容列表
    private List<Info> topList = new ArrayList<>();//热门内容列表
    private MainAdapter adapter;//主内容recyclerView适配器
    private ViewPageAdapter viewPageAdapter;
    private int status = 1;
    PresenterImpl presenterImpl = new PresenterImpl(MainActivity.this);
    public String currentDate = "";//当前已加载的最早的日期

    @SuppressLint("ClickableViewAccessibility")
    public void setLayout() {
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);//读取刷新控件布局
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//设置下拉刷新
                myList.clear();//清空主列表
                topList.clear();//清空今日热闻列表
                if (status != 1) status = 2;
                presenterImpl.loadData("latest", 1);//重新读取今日的日报信息
                swipeRefreshLayout.setRefreshing(false);//关闭加载动画
            }
        });
        ViewPager viewPager = findViewById(R.id.viewPage);//读取滑动控件布局
        viewPager.setOnTouchListener(new View.OnTouchListener() {//设置滑动监听，防止与刷新控件冲突
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        swipeRefreshLayout.setEnabled(false);//滑动时禁用下拉刷新
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swipeRefreshLayout.setEnabled(true);//不滑动时恢复下拉刷新
                        break;
                }
                return false;
            }
        });
        final View[] view = new View[5];
        view[0] = findViewById(R.id.view1);
        view[1] = findViewById(R.id.view2);
        view[2] = findViewById(R.id.view3);
        view[3] = findViewById(R.id.view4);
        view[4] = findViewById(R.id.view5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                for (int j = 0; j < 5; j++) {
                    if (i == j) view[j].setBackgroundResource(R.drawable.dot_focused);
                    else view[j].setBackgroundResource(R.drawable.dot_normal);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        findViewById(R.id.mainRecyclerView).setNestedScrollingEnabled(false);//关闭recyclerView滑动，防止与scrollView冲突
        MyScrollView scrollView = findViewById(R.id.myScrollView);
        scrollView.setMainActivity(this);//初始化scrollView
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        setLayout();//初始化各种控件
        presenterImpl.loadData("latest", 1);
    }

    @Override
    public void showData(final String data) {
        runOnUiThread(new Runnable() {//UI线程更新UI
            @Override
            public void run() {
                if (data.equals("error")) {
                    Toast.makeText(MainActivity.this, "没有网络 (≧ω≦)", Toast.LENGTH_SHORT).show();
                    return;
                }
                String title;
                String date = null;
                int id;
                try {//以下均为json解析
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("stories"));
                    JSONArray array;
                    date = jsonObject.getString("date");//读取日期
                    if (currentDate.equals(date)) return;//已经加载过的不重复加载
                    if (status == 1) {
                        JSONArray jsonArray1 = new JSONArray(jsonObject.getString("top_stories"));
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            topList.add(new Info(jsonArray1.getJSONObject(i).getString("title"),
                                    jsonArray1.getJSONObject(i).getString("image"),
                                    jsonArray1.getJSONObject(i).getInt("id"),
                                    date,
                                    ItemType.CONTENT
                            ));
                        }
                    }

                    myList.add(new Info("", "", 0, date, ItemType.DATE));//把日期项目加到列表中，设置项目类型为DATE
                    for (int i = 0; i < jsonArray.length(); i++) {//遍历数组，读取日报信息
                        JSONObject object = jsonArray.getJSONObject(i);
                        id = object.getInt("id");//文章id
                        title = object.getString("title");//文章标题
                        array = object.getJSONArray("images");//图片，只加载第一张图片
                        myList.add(new Info(title, array.getString(0), id, date, ItemType.CONTENT));
                        //向list中加入这个日报，设置项目类型为CONTENT
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentDate = date;//更新当前加载到的最早日期
                if (status == 1) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
                    adapter = new MainAdapter(myList);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                    ViewPager viewPager = findViewById(R.id.viewPage);
                    viewPageAdapter = new ViewPageAdapter(MainActivity.this, topList);
                    viewPager.setAdapter(viewPageAdapter);
                    status = 3;
                } else if (status != 1) {
                    adapter.notifyDataSetChanged();//显示更多直接更新即可
                    if (status == 2) {
                        viewPageAdapter.notifyDataSetChanged();
                        status = 3;
                    }
                }
            }
        });
    }
}
