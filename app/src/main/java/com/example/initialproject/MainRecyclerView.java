package com.example.initialproject;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainRecyclerView {
    private String date;
    private List<Info> list;

    public MainRecyclerView(String date, List<Info> list) {
        this.date = date;
        this.list=list;
    }

    public String getDate() {
        return date;
    }

    public List<Info> getList() {
        return list;
    }
}
