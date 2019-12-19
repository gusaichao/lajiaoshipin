package com.linkhand.lajiaoshipin.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.BaseActivity;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.base.MyApplication;
import com.linkhand.lajiaoshipin.bean.UserHistory;
import com.linkhand.lajiaoshipin.ui.adapter.UserHistoryAdapter;
import com.linkhand.lajiaoshipin.widget.CommonShareDialog;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserHistoryActivity extends BaseActivity implements UserHistoryAdapter.ItemClicklistener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_text)
    TextView rightText;
    @BindView(R.id.header_layout)
    RelativeLayout headerLayout;
    @BindView(R.id.listview)
    PullToRefreshListView listview;
    private UserHistoryAdapter userHistoryAdapter;
    private UserHistory userHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_collection);
        ButterKnife.bind(this);
        initRefreshListView(listview);
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        initView();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            userHistory = (UserHistory) extras.getSerializable("userHistory");
        }
    }

    private void initView() {
        title.setText("观看记录");
        userHistoryAdapter = new UserHistoryAdapter(this, R.layout.item_layout_history, userHistory.getData());
        userHistoryAdapter.setItemClicklistener(this);
        listview.setAdapter(userHistoryAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", userHistory.getData().get(position - 1).getId());
                    go(VideoActivity.class, bundle);
                }
            }
        });
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshView.onRefreshComplete();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void Onclick(String id, int position) {
        showdialog(id, position);
    }

    private void showdialog(final String id, final int position) {

        final CommonShareDialog commonShareDialog = new CommonShareDialog(this, R.style.MyDialog);
        commonShareDialog.setImage(R.mipmap.icon_con);
        commonShareDialog.setMessage("确定要删除记录吗");
        commonShareDialog.setOptionOneClickListener("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                commonShareDialog.dismiss();
            }
        });
        commonShareDialog.setOptionfuzhiClickListener("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                httpdelete(id, position);
                commonShareDialog.dismiss();
            }
        });
        commonShareDialog.show();
    }

    private void httpdelete(String id, final int position) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.VIDEOHISTORYDEL, RequestMethod.POST);
        request.add("user_id", MyApplication.getUser().getData().getId());
        request.add("portal_id", id);
        RequestQueue queue = NoHttp.newRequestQueue();
        queue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    try {
                        JSONObject jsonObject = response.get();
                        int code = jsonObject.getInt("code");
                        if (code == 200) {
                            userHistory.getData().remove(position);
                            String xiugai = "1";
                            EventBus.getDefault().post(xiugai);
                        } else {
                            showToast(jsonObject.getString("msg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                hideLoading();
            }

            @Override
            public void onFinish(int what) {
                hideLoading();
                listview.onRefreshComplete();
                userHistoryAdapter.notifyDataSetChanged();
            }
        });
    }

}
