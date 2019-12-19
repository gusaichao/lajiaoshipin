package com.linkhand.lajiaoshipin.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.BaseFragment;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.base.MyApplication;
import com.linkhand.lajiaoshipin.bean.HomeBean;
import com.linkhand.lajiaoshipin.bean.IndexMenu;
import com.linkhand.lajiaoshipin.ui.activity.AllVideoActivity;
import com.linkhand.lajiaoshipin.ui.activity.LoginActivity;
import com.linkhand.lajiaoshipin.ui.activity.VideoActivity;
import com.linkhand.lajiaoshipin.ui.activity.WebviewActivity;
import com.linkhand.lajiaoshipin.ui.adapter.HomeGuanggaoRecyAdapter;
import com.linkhand.lajiaoshipin.ui.adapter.HomeRecyBottomAdapter;
import com.linkhand.lajiaoshipin.ui.adapter.HomeRecyLikeAdapter;
import com.linkhand.lajiaoshipin.ui.adapter.HomeRecyTopAdapter;
import com.linkhand.lajiaoshipin.ui.linstener.HomeRecyBottomItemClickLinster;
import com.linkhand.lajiaoshipin.widget.GridSpacingItemDecoration;
import com.stx.xhb.xbanner.XBanner;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment
        implements HomeRecyTopAdapter.Onclicklinster,
        HomeRecyLikeAdapter.Onclicklinster,
        HomeRecyBottomAdapter.Onclicklinster,
        HomeRecyBottomItemClickLinster
        , HomeGuanggaoRecyAdapter.Onclicklinster {


    @BindView(R.id.banner)
    XBanner banner;
    @BindView(R.id.recy_top)
    RecyclerView recyTop;
    @BindView(R.id.recy_bottom)
    RecyclerView recyBottom;
    Unbinder unbinder;
    @BindView(R.id.recy_like)
    RecyclerView recyLike;
    @BindView(R.id.guanggaoRecy)
    RecyclerView guanggaoRecy;
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    private HomeRecyTopAdapter homeRecyTopAdapter;
    private HomeBean homeBean;
    private HomeRecyLikeAdapter homeRecyLikeAdapter;
    private HomeRecyBottomAdapter homeRecyBottomAdapter;
    private IndexMenu indexMenu;
    private HomeGuanggaoRecyAdapter homeGuanggaoRecyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        guanggaoRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeGuanggaoRecyAdapter = new HomeGuanggaoRecyAdapter(getActivity());
        homeGuanggaoRecyAdapter.setOnclicklinster(this);
        recyTop.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        homeRecyTopAdapter = new HomeRecyTopAdapter(getActivity());
        homeRecyTopAdapter.setOnclicklinster(this);
        recyLike.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyLike.addItemDecoration(new GridSpacingItemDecoration(2, 30, true));
        recyLike.setHasFixedSize(true);
        homeRecyLikeAdapter = new HomeRecyLikeAdapter(getActivity());
        homeRecyLikeAdapter.setOnclicklinster(this);
        recyBottom.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeRecyBottomAdapter = new HomeRecyBottomAdapter(getActivity());
        homeRecyBottomAdapter.setOnclicklinster(this);
        httpBanner();
        http();
        initView();
    }

    private void initView() {
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", indexMenu.getData().getImg().get(position).getA_url());
                go(WebviewActivity.class, bundle);
            }
        });
    }

    private void httpBanner() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.SHOUVIDEOCAT, RequestMethod.POST);
        mRequestQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    indexMenu = new Gson().fromJson(response.get().toString(), IndexMenu.class);
                    if (indexMenu.getCode() == 200) {

                        List<String> title = new ArrayList<>();
                        final List<String> image = new ArrayList<>();
                        for (int i = 0; i < indexMenu.getData().getImg().size(); i++) {
                            image.add(ConnectUrl.REQUESTURL_IMAGE + indexMenu.getData().getImg().get(i).getA_img());
                            title.add("");
                        }
                        banner.setData(image, title);
                        banner.setAutoPalyTime(3000);
                        banner.setmAdapter(new XBanner.XBannerAdapter() {
                            @Override
                            public void loadBanner(XBanner banner, Object model, View view, int position) {
                                //设置图片圆角角度
                                Glide.with(getActivity()).load(image.get(position)).into((ImageView) view);
                            }
                        });

                        homeRecyTopAdapter.setList(indexMenu.getData().getMenu());
                        recyTop.setAdapter(homeRecyTopAdapter);

                        homeGuanggaoRecyAdapter.setList(indexMenu.getData().getAdv());
                        guanggaoRecy.setAdapter(homeGuanggaoRecyAdapter);
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

    private void http() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.VIDEOINDEX, RequestMethod.POST);
        mRequestQueue.add(2, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 2) {
                    homeBean = new Gson().fromJson(response.get().toString(), HomeBean.class);
                    if (homeBean.getCode() == 200) {

                        homeRecyLikeAdapter.setList(homeBean.getData().getLike());
                        recyLike.setAdapter(homeRecyLikeAdapter);

                        homeRecyBottomAdapter.setList(homeBean.getData().getList());
                        recyBottom.setAdapter(homeRecyBottomAdapter);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onBottomItemclick(String id) {
        if (MyApplication.getUser() == null) {
            go(LoginActivity.class);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            go(VideoActivity.class, bundle);
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

    @Override
    public void onTopclick(String id, String href) {
        if (!id.equals("12")) {
            Bundle bundle = new Bundle();
            bundle.putString("type", href);
            go(AllVideoActivity.class, bundle);
        }
    }

    @Override
    public void onBottomclick(String id) {
        if (MyApplication.getUser() == null) {
            go(LoginActivity.class);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            go(VideoActivity.class, bundle);
        }
    }

    @Override
    public void onMoreClick(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("type", id);
        go(AllVideoActivity.class, bundle);
    }

    @Override
    public void onguanggaoclick(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("url", id);
        go(WebviewActivity.class, bundle);
    }
}
