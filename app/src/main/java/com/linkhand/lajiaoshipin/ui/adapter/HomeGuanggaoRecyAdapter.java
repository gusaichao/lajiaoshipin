package com.linkhand.lajiaoshipin.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.bean.FindvideoBean;
import com.linkhand.lajiaoshipin.bean.HomeBean;
import com.linkhand.lajiaoshipin.bean.IndexMenu;
import com.linkhand.lajiaoshipin.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeGuanggaoRecyAdapter extends RecyclerView.Adapter<HomeGuanggaoRecyAdapter.MyVh> {
    private Context context;
    private List<IndexMenu.DataBean.AdvBean> list;

    public HomeGuanggaoRecyAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<IndexMenu.DataBean.AdvBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_homeguanggao_rect_layout, parent, false);
        return new MyVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVh holder, final int position) {
        ImageUtils.setImage(holder.imageView,ConnectUrl.REQUESTURL_IMAGE + list.get(position).getA_img());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicklinster.onguanggaoclick(list.get(position).getA_url()+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyVh extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public MyVh(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    private Onclicklinster onclicklinster;

    public void setOnclicklinster(Onclicklinster onclicklinster) {
        this.onclicklinster = onclicklinster;
    }

    public interface Onclicklinster {
        void onguanggaoclick(String id);
    }

}
