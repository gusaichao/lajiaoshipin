package com.linkhand.lajiaoshipin.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.BaseActivity;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.base.MyApplication;
import com.linkhand.lajiaoshipin.bean.AllVideoBean;
import com.linkhand.lajiaoshipin.bean.CatBean;
import com.linkhand.lajiaoshipin.ui.adapter.AllVideoAdapter;
import com.stx.xhb.xbanner.XBanner;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllVideoActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_text)
    TextView rightText;
    @BindView(R.id.header_layout)
    RelativeLayout headerLayout;
    @BindView(R.id.tablayout1)
    TabLayout tablayout1;
    @BindView(R.id.gridview)
    PullToRefreshGridView gridview;
    @BindView(R.id.radioBtn1)
    RadioButton radioBtn1;
    @BindView(R.id.radioBtn2)
    RadioButton radioBtn2;
    @BindView(R.id.radioBtn3)
    RadioButton radioBtn3;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.banner)
    XBanner banner;
    private String type;
    private CatBean catBean;
    private AllVideoBean allVideoBean;
    private List<AllVideoBean.DataBean> list;
    private AllVideoAdapter allVideoAdapter;
    private String choice = "0";
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_video);
        ButterKnife.bind(this);
        httpCat();

    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            type = extras.getString("type");
        }
    }

    private void initView() {
        title.setText("全部高清影片");
        initRefreshGridView(gridview);
        gridview.setMode(PullToRefreshBase.Mode.BOTH);


        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", allVideoBean.getAdv().get(position).getA_url());
                go(WebviewActivity.class, bundle);
            }
        });

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtn1:
                        choice = "0";
                        page = 0;
                        http();
                        break;
                    case R.id.radioBtn2:
                        choice = "1";
                        page = 0;
                        http();
                        break;
                    case R.id.radioBtn3:
                        choice = "2";
                        page = 0;
                        http();
                        break;
                }
            }
        });

        for (int i = 0; i < catBean.getData().size(); i++) {
            tablayout1.addTab(tablayout1.newTab().setText(catBean.getData().get(i).getName()).setTag(catBean.getData().get(i).getId()));
        }
        tablayout1.setOnTabSelectedListener(tabSelectedListener);

        for (int i = 0; i < catBean.getData().size(); i++) {
            if (type.equals(catBean.getData().get(i).getId())) {
                tablayout1.getTabAt(i).select();
            }
        }

        list = new ArrayList<>();
        allVideoAdapter = new AllVideoAdapter(AllVideoActivity.this, R.layout.item_allvideo_layout, list);
        gridview.setAdapter(allVideoAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", list.get(position).getId() + "");
                    go(VideoActivity.class, bundle);
                }

            }
        });
        gridview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                page = 0;
                http();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                http();
            }
        });
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            type = (String) tab.getTag();
            page = 0;
            http();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    private void http() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.CHOICEVIDEO, RequestMethod.POST);
        request.add("category_id", type);
        request.add("choice", choice);
        request.add("page", page);
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        requestQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    try {
                        if (page == 0) {
                            list.clear();
                        }
                        if (response.get().getInt("code") == 200) {
                            allVideoBean = new Gson().fromJson(response.get().toString(), AllVideoBean.class);
                            for (int i = 0; i < allVideoBean.getData().size(); i++) {
                                list.add(allVideoBean.getData().get(i));
                            }
                            List<String> title = new ArrayList<>();
                            final List<String> image = new ArrayList<>();
                            for (int i = 0; i < allVideoBean.getAdv().size(); i++) {
                                image.add(ConnectUrl.REQUESTURL_IMAGE + allVideoBean.getAdv().get(i).getA_img());
                                title.add("");
                            }
                            banner.setData(image, title);
                            banner.setAutoPalyTime(3000);
                            banner.setmAdapter(new XBanner.XBannerAdapter() {
                                @Override
                                public void loadBanner(XBanner banner, Object model, View view, int position) {
                                    //设置图片圆角角度
                                    Glide.with(AllVideoActivity.this).load(image.get(position)).into((ImageView) view);
                                }
                            });
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
                page++;
                gridview.onRefreshComplete();
                allVideoAdapter.notifyDataSetChanged();
            }
        });
    }

    private void httpCat() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.VIDEOCAT, RequestMethod.POST);
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        requestQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    try {
                        if (response.get().getInt("code") == 200) {
                            catBean = new Gson().fromJson(response.get().toString(), CatBean.class);
                            initView();
                            http();
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

    @OnClick({R.id.back, R.id.right_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_text:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                } else {
                    go(ShareActivity.class);
                }
                break;
        }
    }
}
