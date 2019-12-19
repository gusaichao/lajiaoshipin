package com.linkhand.lajiaoshipin.ui.fragment;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.linkhand.lajiaoshipin.bean.FindvideoBean;
import com.linkhand.lajiaoshipin.ui.activity.LoginActivity;
import com.linkhand.lajiaoshipin.ui.activity.ShareActivity;
import com.linkhand.lajiaoshipin.ui.activity.VideoActivity;
import com.linkhand.lajiaoshipin.ui.adapter.FaxianRecyAdapter;
import com.linkhand.lajiaoshipin.ui.adapter.HomeRecyLikeAdapter;
import com.linkhand.lajiaoshipin.utils.ZXingUtils;
import com.linkhand.lajiaoshipin.widget.CommonShareDialog;
import com.linkhand.lajiaoshipin.widget.GridSpacingItemDecoration;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaXianFragment extends BaseFragment implements FaxianRecyAdapter.Onclicklinster {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_text)
    TextView rightText;
    @BindView(R.id.header_layout)
    RelativeLayout headerLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    Unbinder unbinder;
    private FindvideoBean findvideoBean;
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    private FaxianRecyAdapter faxianRecyAdapter;
    private CommonShareDialog commonShareDialog;
    private String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fa_xian, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        back.setVisibility(View.GONE);
        title.setText("发现视频");
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerview.addItemDecoration(new GridSpacingItemDecoration(2, 30, true));
        faxianRecyAdapter = new FaxianRecyAdapter(getActivity());
        faxianRecyAdapter.setOnclicklinster(this);
        http();
    }

    private void http() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.FINDVIDEO, RequestMethod.POST);
        mRequestQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    findvideoBean = new Gson().fromJson(response.get().toString(), FindvideoBean.class);
                    if (findvideoBean.getCode() == 200) {
                        faxianRecyAdapter.setList(findvideoBean.getData());
                        recyclerview.setAdapter(faxianRecyAdapter);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back, R.id.right_text, R.id.texthuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.right_text:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                    showToast("请先登录");
                    return;
                }
//                httpShare();
                go(ShareActivity.class);
                break;
            case R.id.texthuan:
                httphuan();
                break;
        }
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


    private void httphuan() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.ANOTHERBATCH, RequestMethod.POST);
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        requestQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {

                    findvideoBean = new Gson().fromJson(response.get().toString(), FindvideoBean.class);
                    Log.v("NoHttpSample1111", findvideoBean.getCode() + "");
                    if (findvideoBean.getCode() == 200) {
                        faxianRecyAdapter.setList(findvideoBean.getData());
                        recyclerview.setAdapter(faxianRecyAdapter);
                    } else {
                        faxianRecyAdapter.setList(findvideoBean.getData());
                        recyclerview.setAdapter(faxianRecyAdapter);
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

}
