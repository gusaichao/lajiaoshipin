package com.linkhand.lajiaoshipin.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.bean.HomeBean;
import com.linkhand.lajiaoshipin.bean.IndexMenu;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyTopAdapter extends RecyclerView.Adapter<HomeRecyTopAdapter.MyVh> {
    private Context context;
    private List<IndexMenu.DataBean.MenuBean> list;

    public HomeRecyTopAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<IndexMenu.DataBean.MenuBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_rect_top_layout, parent, false);
        return new MyVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVh holder, final int position) {
        holder.textView.setText(list.get(position).getName());
        Glide.with(context).load(ConnectUrl.REQUESTURL_IMAGE + list.get(position).getIcon()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicklinster.onTopclick(list.get(position).getId()+"", list.get(position).getHref());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyVh extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final ImageView imageView;

        public MyVh(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);
        }
    }

    private Onclicklinster onclicklinster;

    public void setOnclicklinster(Onclicklinster onclicklinster) {
        this.onclicklinster = onclicklinster;
    }

    public interface Onclicklinster {
        void onTopclick(String id, String href);
    }

}
