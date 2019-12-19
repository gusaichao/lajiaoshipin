package com.linkhand.lajiaoshipin.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.bean.HomeBean;
import com.linkhand.lajiaoshipin.ui.linstener.HomeRecyBottomItemClickLinster;
import com.linkhand.lajiaoshipin.utils.ImageUtils;
import com.linkhand.lajiaoshipin.widget.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyBottomItemAdapter extends RecyclerView.Adapter<HomeRecyBottomItemAdapter.MyVh> {
    private Context context;
    private List<HomeBean.DataBean.ListBean.VideoBean> list;

    public HomeRecyBottomItemAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<HomeBean.DataBean.ListBean.VideoBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_rect_bottom_item_layout, parent, false);
        return new MyVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVh holder, final int position) {
        holder.text_bofang.setText(list.get(position).getPost_hits() + "次播放");
        holder.text_shichang.setText("时长" + list.get(position).getPost_play_time());
        holder.text_videoname.setText(list.get(position).getPost_title());
        ImageUtils.setImage(holder.imageView, ConnectUrl.REQUESTURL_IMAGE + list.get(position).getMore().getThumbnail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicklinster.onBottomItemclick(list.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyVh extends RecyclerView.ViewHolder {

        private final TextView text_bofang, text_shichang, text_videoname;
        private final ImageView imageView;
        private final RelativeLayout relativeLayout;

        public MyVh(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            text_bofang = itemView.findViewById(R.id.text_bofang);
            text_shichang = itemView.findViewById(R.id.text_shichang);
            text_videoname = itemView.findViewById(R.id.text_videoname);
            relativeLayout = itemView.findViewById(R.id.ll_layout);
        }
    }

    private Onclicklinster onclicklinster;

    public void setOnclicklinster(Onclicklinster onclicklinster) {
        this.onclicklinster = onclicklinster;
    }

    public interface Onclicklinster {
        void onBottomItemclick(String id);
    }

}
