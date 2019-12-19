package com.linkhand.lajiaoshipin.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.BaseActivity;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.base.MyApplication;
import com.linkhand.lajiaoshipin.bean.MovieindexBean;
import com.linkhand.lajiaoshipin.ui.adapter.VideoGuanggaoRecyAdapter;
import com.linkhand.lajiaoshipin.ui.adapter.VideoLikeAdapter;
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
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoActivity extends BaseActivity implements VideoLikeAdapter.Onclicklinster, VideoGuanggaoRecyAdapter.Onclicklinster {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_text)
    TextView rightText;
    @BindView(R.id.jcvideoplayer)
    JCVideoPlayerStandard jcvideoplayer;
    @BindView(R.id.recy_like)
    RecyclerView recyLike;
    @BindView(R.id.textbofangcishu)
    TextView textbofangcishu;
    @BindView(R.id.textshoucang)
    TextView textshoucang;
    @BindView(R.id.videoname)
    TextView videoname;
    @BindView(R.id.image_shoucang)
    ImageView imageShoucang;
    @BindView(R.id.guanggaoRecy)
    RecyclerView guanggaoRecy;
    private String id;
    private MovieindexBean movieindexBean;
    private String type;
    private VideoLikeAdapter videoLikeAdapter;
    private CommonShareDialog commonShareDialog;
    private String url;
    private VideoGuanggaoRecyAdapter videoGuanggaoRecyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        initView();
        http();
    }

    private void initView() {
        jcvideoplayer.backButton.setVisibility(View.GONE);
        recyLike.setLayoutManager(new GridLayoutManager(this, 2));
        recyLike.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        videoLikeAdapter = new VideoLikeAdapter(this);
        videoLikeAdapter.setOnclicklinster(this);

        guanggaoRecy.setLayoutManager(new LinearLayoutManager(this));
        videoGuanggaoRecyAdapter = new VideoGuanggaoRecyAdapter(this);
        videoGuanggaoRecyAdapter.setOnclicklinster(this);

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            id = extras.getString("id");
        }
    }

    private void http() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.MOVIEINDEX, RequestMethod.POST);
        request.add("id", id);
        if (MyApplication.getUser() != null) {
            request.add("user_id", MyApplication.getUser().getData().getId());
        }
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        requestQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    movieindexBean = new Gson().fromJson(response.get().toString(), MovieindexBean.class);
                    try {
                        if (response.get().getInt("code") == 200) {
                            setview();
                        } else if (response.get().getInt("code") == 202) {
                            showToast("请先登录");
                        } else {
                            showToast(response.get().getString("msg"));
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

    private void setview() {
        title.setText(movieindexBean.getData().getName() + "");
        type = movieindexBean.getData().getType() + "";
        videoname.setText(movieindexBean.getData().getPost_title());
        jcvideoplayer.setUp(ConnectUrl.REQUESTURL_IMAGE + movieindexBean.getData().getMore().getFiles().get(0).getUrl(),
                JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        ImageView thumbImageView = jcvideoplayer.thumbImageView;
        Glide.with(this).load(ConnectUrl.REQUESTURL_IMAGE + movieindexBean.getData().getMore().getThumbnail()).into(thumbImageView);
        textbofangcishu.setText("已经播放" + movieindexBean.getData().getPost_hits() + "次");
        if (movieindexBean.getData().getType() == 1) {
            textshoucang.setText("已收藏");
            imageShoucang.setImageDrawable(getResources().getDrawable(R.mipmap.my_shoucang));
        } else {
            textshoucang.setText("收藏");
            imageShoucang.setImageDrawable(getResources().getDrawable(R.mipmap.shoucang));
        }
        videoGuanggaoRecyAdapter.setList(movieindexBean.getData().getAdv());
        guanggaoRecy.setAdapter(videoGuanggaoRecyAdapter);
        videoLikeAdapter.setList(movieindexBean.getData().getLike());
        recyLike.setAdapter(videoLikeAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        jcvideoplayer.releaseAllVideos();
    }

    @OnClick({R.id.back, R.id.right_text, R.id.textshoucang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_text:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                    showToast("请先登录");
                    return;
                }
                go(ShareActivity.class);
                break;
            case R.id.textshoucang:
                if (MyApplication.getUser() != null) {
                    if (type.equals("1")) {
                        type = "0";
                        textshoucang.setText("收藏");
                        imageShoucang.setImageDrawable(getResources().getDrawable(R.mipmap.shoucang));
                    } else {
                        type = "1";
                        textshoucang.setText("已收藏");
                        imageShoucang.setImageDrawable(getResources().getDrawable(R.mipmap.my_shoucang));
                    }
                    httpcollection();
                } else {
                    go(LoginActivity.class);
                    showToast("请先登录");
                }
                break;
        }
    }


    private void httpcollection() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.MOVIECOLLECTION, RequestMethod.POST);
        request.add("id", id);
        request.add("user_id", MyApplication.getUser().getData().getId());
        request.add("type", type);
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        requestQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    movieindexBean = new Gson().fromJson(response.get().toString(), MovieindexBean.class);
                    try {
                        if (response.get().getInt("code") == 200) {
//                            showToast(response.get().getString("msg"));
                        } else {
//                            showToast(response.get().getString("msg"));
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
        this.id = id;
        http();
    }

    @Override
    public void onguanggaoclick(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("url", id);
        go(WebviewActivity.class, bundle);
    }
}
