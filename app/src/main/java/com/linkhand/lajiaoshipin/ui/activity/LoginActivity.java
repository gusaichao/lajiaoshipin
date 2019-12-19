package com.linkhand.lajiaoshipin.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.BaseActivity;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.base.MyApplication;
import com.linkhand.lajiaoshipin.bean.User;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_yanzhengma)
    EditText edYanzhengma;
    @BindView(R.id.image_yanzhengma)
    ImageView imageYanzhengma;
    @BindView(R.id.text_login)
    TextView textLogin;
    @BindView(R.id.text_zhuce)
    TextView textZhuce;
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    private User user;
	// 更新了 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText("登录");
        httpImage();
    }

    @OnClick({R.id.back, R.id.image_yanzhengma, R.id.text_login, R.id.text_zhuce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.image_yanzhengma:
                httpImage();
                break;
            case R.id.text_login:
                if ("".equals(edPhone.getText().toString())) {
                    showToast("请输入用户名");
                    return;
                }
                if ("".equals(edPassword.getText().toString())) {
                    showToast("请输入密码");
                    return;
                }
                if ("".equals(edYanzhengma.getText().toString())) {
                    showToast("请输入验证码");
                    return;
                }
                http();
                break;
            case R.id.text_zhuce:
                go(RegisterActivity.class);
                break;
        }
    }

    private void httpImage() {
        Request<Bitmap> request = NoHttp.createImageRequest(ConnectUrl.VERTIFY, RequestMethod.POST);
        Gson gson = new Gson();
        mRequestQueue.add(1, request, new OnResponseListener<Bitmap>() {


            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<Bitmap> response) {
                imageYanzhengma.setImageBitmap(response.get());

            }

            @Override
            public void onFailed(int what, Response<Bitmap> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    private void http() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.LOGIN_MIMA, RequestMethod.POST);
        request.add("phone", edPhone.getText().toString());
        request.add("password", edPassword.getText().toString());
        request.add("vertify", edYanzhengma.getText().toString());
        mRequestQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    user = new Gson().fromJson(response.get().toString(), User.class);
                    if (user.getCode() == 200) {
                        showToast(user.getInfo());
                        MyApplication.setUser(user);
                        go(MainActivity.class);
                        finish();
                    } else {
                        showToast(user.getInfo());
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
