package com.linkhand.lajiaoshipin.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.BaseActivity;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
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

import static com.linkhand.lajiaoshipin.base.ConnectUrl.VERTIFY;

public class RegisterActivity extends BaseActivity {

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
    @BindView(R.id.ed_phoneyanzhengma)
    EditText edPhoneyanzhengma;
    @BindView(R.id.text_phoneyanzhengma)
    TextView textPhoneyanzhengma;
    @BindView(R.id.text_reg)
    TextView textReg;
    @BindView(R.id.text_login)
    TextView textLogin;
    private RequestQueue mRequestQueue = NoHttp.newRequestQueue();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        httpImage();
    }

    private void initView() {
        title.setText("注册");
    }

    private void httpImage() {
        Request<Bitmap> request = NoHttp.createImageRequest(ConnectUrl.VERTIFY, RequestMethod.POST);
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

    @OnClick({R.id.back, R.id.image_yanzhengma, R.id.text_phoneyanzhengma, R.id.text_reg, R.id.text_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.image_yanzhengma:
                httpImage();
                break;
            case R.id.text_phoneyanzhengma:
                if ("".equals(edPhone.getText().toString())){
                    showToast("请输入手机号");
                    return;
                }
                httpPhoneYanzheng();
                break;
            case R.id.text_reg:
                if ("".equals(edPhone.getText().toString())){
                    showToast("请输入用户名");
                    return;
                }
                if ("".equals(edPassword.getText().toString())){
                    showToast("请输入密码");
                    return;
                }
                if ("".equals(edYanzhengma.getText().toString())){
                    showToast("请输入验证码");
                    return;
                }
                if ("".equals(edPhoneyanzhengma.getText().toString())){
                    showToast("请输入手机验证码");
                    return;
                }
                http();
                break;
            case R.id.text_login:
                finish();
                break;
        }
    }

    private void httpPhoneYanzheng() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.SMS, RequestMethod.POST);
        request.add("phone",edPhone.getText().toString());
        mRequestQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what==1){
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getInt("code") == 200){
                            showToast(jsonObject.getString("info"));
                            timer.start();
                            textPhoneyanzhengma.setClickable(false);
                            textPhoneyanzhengma.setEnabled(false);
                        }else {
                            showToast(jsonObject.getString("info"));
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

    private void http() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.LOGINZHU, RequestMethod.POST);
        request.add("phone",edPhone.getText().toString());
        request.add("user_pass",edPassword.getText().toString());
        request.add("vertify",edYanzhengma.getText().toString());
        request.add("code",edPhoneyanzhengma.getText().toString());
        mRequestQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                showLoading();
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what==1){
                    JSONObject jsonObject = response.get();
                    try {
                        if (jsonObject.getInt("code") == 200){
                            showToast(jsonObject.getString("info"));
                            finish();
                        }else {
                            showToast(jsonObject.getString("info"));
                        }
                        Log.i("NoHttpSample",response.get().toString());
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

    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            textPhoneyanzhengma.setText("还剩" + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            textPhoneyanzhengma.setText("重新发送");
            textPhoneyanzhengma.setClickable(true);
            textPhoneyanzhengma.setEnabled(true);
        }
    };

}
