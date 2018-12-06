package com.example.initialproject.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.initialproject.R;
import com.example.initialproject.model.Info;

import java.util.ArrayList;
import java.util.List;

//主适配器,负责更新处理recyclerView
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Info> list;

    public MainAdapter(List<Info> list) {
        this.list=list;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == ItemType.DATE) return ItemType.DATE.ordinal();
        return ItemType.CONTENT.ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (i == ItemType.DATE.ordinal()) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.date, viewGroup, false);
            return new DateViewHolder(view);
        }
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        final Info info = list.get(i);
        if (viewHolder instanceof DateViewHolder) {
            String d = info.getDate();
            DateViewHolder holder = (DateViewHolder) viewHolder;
            holder.textView.setText(d.substring(0, 4) + "年" + d.substring(4, 6) + "月" + d.substring(6, 8) + "日");
        } else if (viewHolder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            Glide.with(itemViewHolder.context).load(info.getImageName()).into(itemViewHolder.imageView);
            itemViewHolder.textView.setText(info.getName());
            itemViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemViewHolder.context, ContentActivity.class);
                    intent.putExtra("id", info.getID());
                    intent.putExtra("title", info.getName());
                    itemViewHolder.context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Context context;

        DateViewHolder(View view) {
            super(view);
            context = view.getContext();
            textView = view.findViewById(R.id.dateText);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView textView;
        private ImageView imageView;
        private CardView cardView;

        ItemViewHolder(View view) {
            super(view);
            context = view.getContext();
            textView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.imageView);
            cardView = view.findViewById(R.id.cardView);
        }
    }
}
