package com.example.initialproject.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.initialproject.R
import com.example.initialproject.model.Info
import com.example.initialproject.presenter.PresenterImpl
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

//主activity
class  MainActivity : AppCompatActivity(), com.example.initialproject.view.MyView {
    private val myList = ArrayList<Info>()//主内容列表
    private val topList = ArrayList<Info>()//热门内容列表
    private var adapter: MainAdapter? = null//主内容recyclerView适配器
    private var viewPageAdapter: ViewPageAdapter? = null
    private var status = 1
    var presenterImpl = PresenterImpl(this@MainActivity)
    var currentDate: String? = ""//当前已加载的最早的日期

    @SuppressLint("ClickableViewAccessibility")
    fun setLayout() {
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeLayout)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)//读取刷新控件布局
        swipeRefreshLayout.setOnRefreshListener {
            //设置下拉刷新
            myList.clear()//清空主列表
            topList.clear()//清空今日热闻列表
            if (status != 1) status = 2
            presenterImpl.loadData("latest", 1)//重新读取今日的日报信息
            swipeRefreshLayout.isRefreshing = false//关闭加载动画
        }
        val viewPager = findViewById<ViewPager>(R.id.viewPage)//读取滑动控件布局
        viewPager.setOnTouchListener { v, event ->
            //设置滑动监听，防止与刷新控件冲突
            when (event.action) {
                MotionEvent.ACTION_MOVE -> swipeRefreshLayout.isEnabled = false//滑动时禁用下拉刷新
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> swipeRefreshLayout.isEnabled = true//不滑动时恢复下拉刷新
            }
            false
        }
        val view = arrayOfNulls<View>(5)
        view[0] = findViewById(R.id.view1)
        view[1] = findViewById(R.id.view2)
        view[2] = findViewById(R.id.view3)
        view[3] = findViewById(R.id.view4)
        view[4] = findViewById(R.id.view5)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}

            override fun onPageSelected(i: Int) {
                for (j in 0..4) {
                    if (i == j)
                        view[j]?.setBackgroundResource(R.drawable.dot_focused)
                    else
                        view[j]?.setBackgroundResource(R.drawable.dot_normal)
                }
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
        findViewById<View>(R.id.mainRecyclerView).isNestedScrollingEnabled = false//关闭recyclerView滑动，防止与scrollView冲突
        val scrollView = findViewById<MyScrollView>(R.id.myScrollView)
        scrollView.setMainActivity(this)//初始化scrollView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainlayout)
        setLayout()//初始化各种控件
        presenterImpl.loadData("latest", 1)
    }

    override fun showData(data: String) {
        runOnUiThread {
            if (data == "error") {
                Toast.makeText(this@MainActivity, "没有网络 (≧ω≦)", Toast.LENGTH_SHORT).show()
                return@runOnUiThread
            }
            var title: String
            var date: String? = null
            var id: Int
            try {//以下均为json解析
                val jsonObject = JSONObject(data)
                val jsonArray = JSONArray(jsonObject.getString("stories"))
                var array: JSONArray
                date = jsonObject.getString("date")//读取日期
                if (currentDate == date) return@runOnUiThread //已经加载过的不重复加载
                if (status == 1) {
                    val jsonArray1 = JSONArray(jsonObject.getString("top_stories"))
                    for (i in 0 until jsonArray1.length()) {
                        topList.add(Info(jsonArray1.getJSONObject(i).getString("title"),
                                jsonArray1.getJSONObject(i).getString("image"),
                                jsonArray1.getJSONObject(i).getInt("id"),
                                date,
                                ItemType.CONTENT
                        ))
                    }
                }

                myList.add(Info("", "", 0, date, ItemType.DATE))//把日期项目加到列表中，设置项目类型为DATE
                for (i in 0 until jsonArray.length()) {//遍历数组，读取日报信息
                    val `object` = jsonArray.getJSONObject(i)
                    id = `object`.getInt("id")//文章id
                    title = `object`.getString("title")//文章标题
                    array = `object`.getJSONArray("images")//图片，只加载第一张图片
                    myList.add(Info(title, array.getString(0), id, date, ItemType.CONTENT))
                    //向list中加入这个日报，设置项目类型为CONTENT
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            currentDate = date//更新当前加载到的最早日期
            when (status) {
                1 -> {
                    val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                    val recyclerView = findViewById<RecyclerView>(R.id.mainRecyclerView)
                    adapter = MainAdapter(myList)
                    recyclerView.layoutManager = linearLayoutManager
                    recyclerView.adapter = adapter
                    val viewPager = findViewById<ViewPager>(R.id.viewPage)
                    viewPageAdapter = ViewPageAdapter(this@MainActivity, topList)
                    viewPager.adapter = viewPageAdapter
                    status = 3
                }
                else -> {
                    adapter!!.notifyDataSetChanged()//显示更多直接更新即可
                    if (status == 2) {
                        viewPageAdapter!!.notifyDataSetChanged()
                        status = 3
                    }
                }
            }
        }
    }
}