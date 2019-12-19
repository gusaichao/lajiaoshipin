package com.linkhand.lajiaoshipin.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.bean.HomeBean;
import com.linkhand.lajiaoshipin.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyBottomAdapter extends RecyclerView.Adapter<HomeRecyBottomAdapter.MyVh> implements HomeRecyBottomItemAdapter.Onclicklinster {
    private Context context;
    private List<HomeBean.DataBean.ListBean> list;

    public HomeRecyBottomAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<HomeBean.DataBean.ListBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_rect_bottom_layout, parent, false);
        return new MyVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVh holder, final int position) {
        holder.name.setText(list.get(position).getName());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerview.setLayoutManager(new GridLayoutManager(context, 2));
        holder.recyclerview.addItemDecoration(new GridSpacingItemDecoration(2, 30, true));
        HomeRecyBottomItemAdapter homeRecyBottomItemAdapter = new HomeRecyBottomItemAdapter(context);
        homeRecyBottomItemAdapter.setList(list.get(position).getVideo());
        homeRecyBottomItemAdapter.setOnclicklinster(this);
        holder.recyclerview.setAdapter(homeRecyBottomItemAdapter);

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicklinster.onMoreClick(list.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBottomItemclick(String id) {
        onclicklinster.onBottomclick(id);
    }

    public class MyVh extends RecyclerView.ViewHolder {

        private final RecyclerView recyclerview;
        private final TextView name, more;

        public MyVh(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            more = itemView.findViewById(R.id.more);
            recyclerview = itemView.findViewById(R.id.recyclerview);
        }
    }

    private Onclicklinster onclicklinster;

    public void setOnclicklinster(Onclicklinster onclicklinster) {
        this.onclicklinster = onclicklinster;
    }

    public interface Onclicklinster {
        void onBottomclick(String id);

        void onMoreClick(String id);
    }

}
