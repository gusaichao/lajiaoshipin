package com.linkhand.lajiaoshipin.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.BaseFragment;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.base.MyApplication;
import com.linkhand.lajiaoshipin.bean.UserHistory;
import com.linkhand.lajiaoshipin.ui.activity.LoginActivity;
import com.linkhand.lajiaoshipin.ui.activity.ShareActivity;
import com.linkhand.lajiaoshipin.ui.activity.UserCollectionActivity;
import com.linkhand.lajiaoshipin.ui.activity.UserHistoryActivity;
import com.linkhand.lajiaoshipin.ui.activity.VideoActivity;
import com.linkhand.lajiaoshipin.ui.adapter.MyRecyHistoryAdapter;
import com.linkhand.lajiaoshipin.widget.GridSpacingItemDecoration;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment implements MyRecyHistoryAdapter.Onclicklinster {

    @BindView(R.id.userImage)
    ImageView userImage;
    @BindView(R.id.userphone)
    TextView userphone;
    @BindView(R.id.ll_shoucang)
    RelativeLayout llShoucang;
    @BindView(R.id.ll_jilu)
    RelativeLayout llJilu;
    @BindView(R.id.ll_fenxiang)
    RelativeLayout llFenxiang;
    @BindView(R.id.ll_tuichu)
    RelativeLayout llTuichu;
    Unbinder unbinder;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private UserHistory userHistory;
    private MyRecyHistoryAdapter myRecyHistoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (MyApplication.getUser() != null) {
            userphone.setText(MyApplication.getUser().getData().getMobile());
            getVideoHistory();
            getUserInfo();
        }
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerview.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        myRecyHistoryAdapter = new MyRecyHistoryAdapter(getActivity());
        myRecyHistoryAdapter.setOnclicklinster(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(String xiugai){
        if ("1".equals(xiugai)){
            getVideoHistory();
        }
    }


    private void getVideoHistory() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.VIDEOHISTORY, RequestMethod.POST);
        request.add("user_id", MyApplication.getUser().getData().getId());
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
                            userHistory = new Gson().fromJson(response.get().toString(), UserHistory.class);
                            myRecyHistoryAdapter.setList(userHistory.getData());
                            recyclerview.setAdapter(myRecyHistoryAdapter);
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
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getUserInfo();
            getVideoHistory();
        } else {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.ll_shoucang, R.id.ll_jilu, R.id.ll_fenxiang, R.id.ll_tuichu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shoucang:
                go(UserCollectionActivity.class);
                break;
            case R.id.ll_jilu:
                if (userHistory != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userHistory", userHistory);
                    go(UserHistoryActivity.class, bundle);
                } else {
                    showToast("请先登录");
                    go(LoginActivity.class);
                }
                break;
            case R.id.ll_fenxiang:
                go(ShareActivity.class);
                break;
            case R.id.ll_tuichu:
                MyApplication.setUser(null);
                text.setText("");
                userphone.setText("请登录");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }


    public void getUserInfo() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.USERINDEX, RequestMethod.POST);
        request.add("user_id", MyApplication.getUser().getData().getId());
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
                            String string = jsonObject.getJSONObject("data").getString("data");
                            text.setText(string);
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
            }
        });
    }

    @Override
    public void onLikeclick(String id) {
        if (MyApplication.getUser() == null) {
            go(LoginActivity.class);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            go(VideoActivity.class, bundle);
        }
    }
}
