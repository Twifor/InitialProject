package com.example.initialproject.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.initialproject.R;
import com.example.initialproject.model.Info;

import java.util.List;

//热门内容滑块适配器
public class ViewPageAdapter extends PagerAdapter {
    private List<Info> list;
    private Context context;

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    ViewPageAdapter(Context context, List<Info> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View view = View.inflate(context, R.layout.pagelayout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//监听点击时间
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra("id", list.get(position).getID());//开一个activity显示详情
                intent.putExtra("title", list.get(position).getName());
                context.startActivity(intent);
            }
        });
        ImageView imageView = view.findViewById(R.id.pageImage);
        TextView textView = view.findViewById(R.id.textView2);
        textView.setText(list.get(position).getName());
        View[] view0 = new View[5];
        view0[0] = view.findViewById(R.id.view1);
        view0[1] = view.findViewById(R.id.view2);
        view0[2] = view.findViewById(R.id.view3);
        view0[3] = view.findViewById(R.id.view4);
        view0[4] = view.findViewById(R.id.view5);
        for (int i = 0; i < 5; i++) {
            if (i == position) view0[i].setBackgroundResource(R.drawable.dot_focused);
            else view0[i].setBackgroundResource(R.drawable.dot_normal);
        }
        Glide.with(context).load(list.get(position).getImageName()).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
