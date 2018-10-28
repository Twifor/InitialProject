package com.example.initialproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.viewHolder> {
    private List<Info> myList;

    public InfoAdapter(List<Info> list) {
        myList = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Info info = myList.get(i);
        viewHolder.imageView.setImageResource(info.getImageName());
        viewHolder.textView.setText(info.getName());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        viewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.imageView);

        }
    }
}
