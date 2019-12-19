package com.linkhand.lajiaoshipin.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.bean.UserCollectionBean;
import com.linkhand.lajiaoshipin.bean.UserHistory;
import com.linkhand.lajiaoshipin.utils.ImageUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class UserCollectionAdapter extends CommonAdapter {
    private Context mContext;
    private List<UserCollectionBean.DataBean> mList;

    public UserCollectionAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.mList = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, final int position) {
        ImageView imageView = viewHolder.getView(R.id.image);
        TextView textname = viewHolder.getView(R.id.textname);
        TextView textshichang = viewHolder.getView(R.id.textshichang);
        TextView textshanchu = viewHolder.getView(R.id.textshanchu);
        ImageUtils.setImage(imageView, ConnectUrl.REQUESTURL_IMAGE + mList.get(position).getMore().getThumbnail());
        textname.setText(mList.get(position).getTitle());
        textshichang.setText(mList.get(position).getPost_hits()+"次播放");
        textshanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicklistener.Onclick(mList.get(position).getId(), position);
            }
        });

    }

    private ItemClicklistener itemClicklistener;

    public void setItemClicklistener(ItemClicklistener itemClicklistener) {
        this.itemClicklistener = itemClicklistener;
    }

    public interface ItemClicklistener {
        void Onclick(String id, int position);
    }

}
