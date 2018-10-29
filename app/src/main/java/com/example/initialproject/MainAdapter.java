package com.example.initialproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> {
    private List<MainRecyclerView> list;

    public MainAdapter(List<MainRecyclerView> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main, viewGroup, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        final MainRecyclerView mainRecyclerView = list.get(i);
        viewHolder.textView.setText(mainRecyclerView.getDate());
        Log.d("Impor", "3");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(viewHolder.context);
        viewHolder.recyclerView.setLayoutManager(linearLayoutManager);
        InfoAdapter adapter = new InfoAdapter(mainRecyclerView.getList());
        viewHolder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private RecyclerView recyclerView;
        private Context context;

        public viewHolder(View view) {
            super(view);
            context = view.getContext();
            recyclerView = view.findViewById(R.id.recyclerView);
            textView = view.findViewById(R.id.mainTextView);
        }
    }
}
