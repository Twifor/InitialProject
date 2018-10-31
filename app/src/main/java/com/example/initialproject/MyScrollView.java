package com.example.initialproject;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    private MainActivity mainActivity;
    private MainAdapter adapter;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setAdapter(MainAdapter adapter) {
        this.adapter = adapter;
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (clampedY) {
            mainActivity.init("https://news-at.zhihu.com/api/3/news/before/" + mainActivity.currentDate);
        }
    }
}
