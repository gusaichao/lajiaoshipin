package com.linkhand.lajiaoshipin.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.bean.AllVideoBean;
import com.linkhand.lajiaoshipin.bean.UserHistory;
import com.linkhand.lajiaoshipin.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class AllVideoAdapter extends CommonAdapter {
    private Context mContext;
    private List<AllVideoBean.DataBean> mList;

    public AllVideoAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, final int position) {
        ImageView imageView = viewHolder.getView(R.id.image);
        TextView textname = viewHolder.getView(R.id.text_bofang);
        TextView textshichang = viewHolder.getView(R.id.text_shichang);
        TextView textshanchu = viewHolder.getView(R.id.text_videoname);

        textname.setText(mList.get(position).getPost_hits() + "次播放");
        textshichang.setText("时长" + mList.get(position).getPost_play_time());
        textshanchu.setText(mList.get(position).getPost_title());
        ImageUtils.setImage(imageView, ConnectUrl.REQUESTURL_IMAGE + mList.get(position).getMore().getThumbnail());

    }

}
