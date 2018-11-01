package com.example.initialproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, final int i) {
        final Info info = myList.get(i);
        Glide.with(viewHolder.context).load(info.getImageName()).into(viewHolder.imageView);
        viewHolder.textView.setText(info.getName());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.context, ContentActivity.class);
                intent.putExtra("id",info.getID());
                intent.putExtra("title",info.getName());
                viewHolder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView textView;
        private ImageView imageView;
        private CardView cardView;

        viewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.imageView);
            context = view.getContext();
            cardView = view.findViewById(R.id.cardView);
        }
    }
}
