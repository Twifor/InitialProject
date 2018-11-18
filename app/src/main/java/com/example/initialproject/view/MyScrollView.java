package com.example.initialproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.example.initialproject.view.MainActivity;

//自定义滑动控件，实现上拉加载更多
public class MyScrollView extends ScrollView {
    private MainActivity mainActivity;//保存主类
    private boolean key = true;//用来防止一次加载过多

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (clampedY && scrollY > 0 && key) {//滑到底端时加载前一天日报
            mainActivity.presenterImplement.loadData("https://news-at.zhihu.com/api/3/news/before/" + mainActivity.currentDate);
            key = false;
        } else if (clampedY && scrollY > 0) key = true;
    }
}
