package com.example.initialproject.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView


//自定义滑动控件，实现上拉加载更多
class MyScrollView(context: Context, attrs: AttributeSet) : ScrollView(context, attrs) {
    private var mainActivity: MainActivity? = null//保存主类
    private var key = true//用来防止一次加载过多

    fun setMainActivity(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        if (clampedY && scrollY > 0 && key) {//滑到底端时加载前一天日报
            mainActivity!!.presenterImpl.loadData(mainActivity!!.currentDate!!, 2)
            key = false
        } else if (clampedY && scrollY > 0) key = true
    }
}