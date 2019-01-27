package com.example.initialproject.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.initialproject.R
import com.example.initialproject.model.Info

//主适配器,负责更新处理recyclerView
class MainAdapter(private val list: List<Info>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (list[position].type == ItemType.DATE) ItemType.DATE.ordinal else ItemType.CONTENT.ordinal
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val view: View
        if (i == ItemType.DATE.ordinal) {
            view = LayoutInflater.from(viewGroup.context).inflate(R.layout.date, viewGroup, false)
            return DateViewHolder(view)
        }
        view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item, viewGroup, false)
        return ItemViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val info = list[i]
        if (viewHolder is DateViewHolder) {
            val d = info.date
            viewHolder.textView.text = d.substring(0, 4) + "年" + d.substring(4, 6) + "月" + d.substring(6, 8) + "日"
        } else if (viewHolder is ItemViewHolder) {
            Glide.with(viewHolder.context).load(info.imageName).into(viewHolder.imageView)
            viewHolder.textView.text = info.name
            viewHolder.cardView.setOnClickListener {
                val intent = Intent(viewHolder.context, ContentActivity::class.java)
                intent.putExtra("id", info.id)
                intent.putExtra("title", info.name)
                viewHolder.context.startActivity(intent)
            }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    internal class DateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.dateText)

    }

    internal class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context: Context = view.context
        val textView: TextView = view.findViewById(R.id.textView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val cardView: CardView = view.findViewById(R.id.cardView)

    }
}