package com.linkhand.lajiaoshipin.ui.activity;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.BaseActivity;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.base.MyApplication;
import com.linkhand.lajiaoshipin.utils.ZXingUtils;
import com.linkhand.lajiaoshipin.widget.ScreenUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_text)
    TextView rightText;
    @BindView(R.id.header_layout)
    RelativeLayout headerLayout;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.lianjie)
    TextView lianjie;
    @BindView(R.id.image)
    ImageView image;
    private String url;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        int screenSize = new ScreenUtil(this).getScreenSize(ScreenUtil.WIDTH);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenSize - 80, screenSize - 80);
        image.setLayoutParams(layoutParams);
        title.setText("分享");
        httpShare();

        lianjie.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(url);
                showToast("已复制");
                return true;
            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(ShareActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //没有权限则申请权限
                        ActivityCompat.requestPermissions(ShareActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        //有权限直接执行,docode()不用做处理
                        saveImageToGallery(ShareActivity.this, bitmap);
                    }
                } else {
                    //小于6.0，不用申请权限，直接执行
                    saveImageToGallery(ShareActivity.this, bitmap);
                }
                return true;
            }
        });
    }

    private void httpShare() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.SHAREVIDEO, RequestMethod.POST);
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
                    try {
                        if (response.get().getInt("code") == 200) {
                            JSONObject jsonObject = response.get();
                            url = jsonObject.getString("url");
                            bitmap = ZXingUtils.createQRImage(url, image.getWidth(), image.getHeight());
                            image.setImageBitmap(bitmap);
                            lianjie.setText(url + "   长按复制链接");
                            text.setText(jsonObject.getString("data"));
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

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片 创建文件夹
        File appDir = new File(Environment.getExternalStorageDirectory(), "LJSP");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //图片文件名称
        String fileName = "LJSP_" + System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.e("111", e.getMessage());
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        String path = file.getAbsolutePath();
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
        } catch (FileNotFoundException e) {
            Log.e("333", e.getMessage());
            e.printStackTrace();
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
    }
}
