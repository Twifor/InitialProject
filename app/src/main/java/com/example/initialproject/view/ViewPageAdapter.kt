package com.example.initialproject.view

import android.content.Context
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.initialproject.R
import com.example.initialproject.model.Info


//热门内容滑块适配器
class ViewPageAdapter internal constructor(private val context: Context, private val list: List<Info>) : PagerAdapter() {

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = View.inflate(context, R.layout.pagelayout, null)
        view.setOnClickListener {
            //监听点击时间
            val intent = Intent(context, ContentActivity::class.java)
            intent.putExtra("id", list[position].id)//开一个activity显示详情
            intent.putExtra("title", list[position].name)
            context.startActivity(intent)
        }
        val imageView = view.findViewById<ImageView>(R.id.pageImage)
        val textView = view.findViewById<TextView>(R.id.textView2)
        textView.text = list[position].name
        Glide.with(context).load(list[position].imageName).into(imageView)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }
}