package com.example.initialproject;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    private MainActivity mainActivity;
    private boolean key = true;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (clampedY && scrollY > 0 && key) {
            Log.d("Impor","https://news-at.zhihu.com/api/3/news/before/" + mainActivity.currentDate);
            mainActivity.init("https://news-at.zhihu.com/api/3/news/before/" + mainActivity.currentDate);
            key=false;
        }
        else if(clampedY&&scrollY>0)key=true;
    }
}
