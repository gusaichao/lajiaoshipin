package com.linkhand.lajiaoshipin.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.BaseActivity;
import com.linkhand.lajiaoshipin.base.ConnectUrl;
import com.linkhand.lajiaoshipin.base.MyApplication;
import com.linkhand.lajiaoshipin.bean.User;
import com.linkhand.lajiaoshipin.ui.fragment.FaXianFragment;
import com.linkhand.lajiaoshipin.ui.fragment.HomeFragment;
import com.linkhand.lajiaoshipin.ui.fragment.MovieFragment;
import com.linkhand.lajiaoshipin.ui.fragment.MyFragment;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    private FragmentManager fragmentManager;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;

    private HomeFragment homeFragment;
    private MovieFragment movieFragment;
    private FaXianFragment faXianFragment;
    private MyFragment userFragment;
    private static final int HOME = 1;
    private static final int NOTICE = 2;
    private static final int RELEASE = 3;
    private static final int USER = 4;

    private String[] name = {"首页", "视频", "发现", "我的"};
    private int[] image = {
            R.mipmap.shouye_false,
            R.mipmap.shouye_true,
            R.mipmap.xiaoshuo_false,
            R.mipmap.xiaoshuo_true,
            R.mipmap.faxian_false,
            R.mipmap.faxian_true,
            R.mipmap.wode_false,
            R.mipmap.wode2
    };

    private String url = null;
    private boolean success;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();

        initView();
        initData();
        httpCheck();
    }

    private void initView() {

        linearLayout1 = (LinearLayout) findViewById(R.id.linearlayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearlayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearlayout3);
        linearLayout4 = (LinearLayout) findViewById(R.id.linearlayout4);

        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
    }

    private void initData() {
        showFragment(HOME);

        if (PackageManager.PERMISSION_GRANTED == ContextCompat.
                checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        } else {
            //提示用户开户权限
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }

        if (MyApplication.getUser() != null) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    Looper.prepare();
                    //需要执行的任务
                    activetime();
                    Looper.loop();
                }
            }, 600000);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!success) {
            if (MyApplication.getUser() != null) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        Looper.prepare();
                        //需要执行的任务
                        activetime();
                        Looper.loop();
                    }
                }, 600000);
            }
        }
    }

    private void activetime() {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.ACTIVETIME, RequestMethod.POST);
        request.add("user_id", MyApplication.getUser().getData().getId());
        request.add("time", System.currentTimeMillis() / 1000 + "");
        RequestQueue mQueue = NoHttp.newRequestQueue();
        mQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    String resultCode = null;
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            success = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout1:
                showFragment(HOME);
                break;
            case R.id.linearlayout2:
                showFragment(NOTICE);
                break;
            case R.id.linearlayout3:
                showFragment(RELEASE);
                break;
            case R.id.linearlayout4:
                if (MyApplication.getUser() == null) {
                    go(LoginActivity.class);
                    return;
                }
                showFragment(USER);
                break;
        }
    }

    private void showFragment(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏所有的Fragment
        hideFragment(fragmentTransaction);
        setDefaultBottom();
        setName(index);
        //显示指定的Fragment

        switch (index) {
            //首页
            case HOME:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.frameLayout, homeFragment);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                break;
            //预告
            case NOTICE:
                if (movieFragment == null) {
                    movieFragment = new MovieFragment();
                    fragmentTransaction.add(R.id.frameLayout, movieFragment);
                } else {
                    fragmentTransaction.show(movieFragment);
                }
                break;
            //发布
            case RELEASE:
                if (faXianFragment == null) {
                    faXianFragment = new FaXianFragment();
                    fragmentTransaction.add(R.id.frameLayout, faXianFragment);
                } else {
                    fragmentTransaction.show(faXianFragment);
                }
                break;
            //用户中心
            case USER:
                if (userFragment == null) {
                    userFragment = new MyFragment();
                    fragmentTransaction.add(R.id.frameLayout, userFragment);
                } else {
                    fragmentTransaction.show(userFragment);
                }
                break;
        }

        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (movieFragment != null) {
            fragmentTransaction.hide(movieFragment);
        }
        if (faXianFragment != null) {
            fragmentTransaction.hide(faXianFragment);
        }
        if (userFragment != null) {
            fragmentTransaction.hide(userFragment);
        }
    }


    private void setName(int index) {
        ImageView imageView;
        TextView text;
        switch (index) {
            case HOME:
                imageView = (ImageView) linearLayout1.findViewById(R.id.imageview);
                text = (TextView) linearLayout1.findViewById(R.id.textview_icon);
                imageView.setImageDrawable(getResources().getDrawable(image[1]));
                text.setText(name[0]);
                text.setTextColor(getResources().getColor(R.color.colorTopic));
                break;
            case NOTICE:
                imageView = (ImageView) linearLayout2.findViewById(R.id.imageview);
                imageView.setImageDrawable(getResources().getDrawable(image[3]));
                text = (TextView) linearLayout2.findViewById(R.id.textview_icon);
                text.setText(name[1]);
                text.setTextColor(getResources().getColor(R.color.colorTopic));
                break;
            case RELEASE:
                imageView = (ImageView) linearLayout3.findViewById(R.id.imageview);
                imageView.setImageDrawable(getResources().getDrawable(image[5]));
                text = (TextView) linearLayout3.findViewById(R.id.textview_icon);
                text.setText(name[2]);
                text.setTextColor(getResources().getColor(R.color.colorTopic));
                break;
            case USER:
                imageView = (ImageView) linearLayout4.findViewById(R.id.imageview);
                imageView.setImageDrawable(getResources().getDrawable(image[7]));
                text = (TextView) linearLayout4.findViewById(R.id.textview_icon);
                text.setText(name[3]);
                text.setTextColor(getResources().getColor(R.color.colorTopic));
                break;
        }
    }


    private void setDefaultBottom() {
        ImageView imageView = (ImageView) linearLayout1.findViewById(R.id.imageview);
        imageView.setImageDrawable(getResources().getDrawable(image[0]));
        TextView text = (TextView) linearLayout1.findViewById(R.id.textview_icon);
        text.setText(name[0]);
        text.setTextColor(getResources().getColor(R.color.colorBlack));

        imageView = (ImageView) linearLayout2.findViewById(R.id.imageview);
        imageView.setImageDrawable(getResources().getDrawable(image[2]));
        text = (TextView) linearLayout2.findViewById(R.id.textview_icon);
        text.setText(name[1]);
        text.setTextColor(getResources().getColor(R.color.colorBlack));

        imageView = (ImageView) linearLayout3.findViewById(R.id.imageview);
        imageView.setImageDrawable(getResources().getDrawable(image[4]));
        text = (TextView) linearLayout3.findViewById(R.id.textview_icon);
        text.setText(name[2]);
        text.setTextColor(getResources().getColor(R.color.colorBlack));

        imageView = (ImageView) linearLayout4.findViewById(R.id.imageview);
        imageView.setImageDrawable(getResources().getDrawable(image[6]));
        text = (TextView) linearLayout4.findViewById(R.id.textview_icon);
        text.setText(name[3]);
        text.setTextColor(getResources().getColor(R.color.colorBlack));
    }


    //对比本程序的版本号和最新程序的版本号
    public void checkVersion() {
        //按钮！
        //如果检测本程序的版本号小于服务器的版本号，那么提示用户更新
        try {
            if (url != null) {
                showDialogUpdate();
            } else {
                Toast.makeText(MainActivity.this, "当前已经是最新的版本", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提示版本更新的对话框
     */
    private void showDialogUpdate() {

        // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置提示框的标题
        builder.setTitle("版本升级").
                // 设置提示框的图标
                        setIcon(R.mipmap.icon_con).
                // 设置要显示的信息
                        setMessage("发现新版本！请及时更新").
                // 设置确定按钮
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "选择确定哦", 0).show();
                        loadNewVersionProgress();//下载最新的版本程序
                    }
                }).

                // 设置取消按钮,null是什么都不做，并关闭对话框
                        setNegativeButton("取消", null);

        // 生产对话框
        AlertDialog alertDialog = builder.create();
        // 显示对话框
        alertDialog.show();


    }

    /**
     * 下载新版本程序，需要子线程
     */
    private void loadNewVersionProgress() {
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);



//        final ProgressDialog pd;    //进度条对话框
//        pd = new ProgressDialog(this);
//        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        pd.setMessage("正在下载更新");
//        pd.show();
//        //启动子线程下载任务
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    file = getFileFromServer(url, pd);
//                    sleep(3000);
////                    installProcess(file);
//                    installApk(file);
//                    pd.dismiss(); //结束掉进度条对话框
//                } catch (Exception e) {
//                    //下载apk失败
//                    Looper.prepare();
//                    Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
//                    Looper.loop();
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }

    //安装应用的流程
//    private void installProcess(File file) {
//        boolean haveInstallPermission;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            //先获取是否有安装未知来源应用的权限
//            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
//            if (!haveInstallPermission) {//没有权限
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("开启权限").
//                                setIcon(R.mipmap.icon_con).
//                                setMessage("安装应用需要打开未知来源权限，请去设置中开启权限").
//                                setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    startInstallPermissionSettingActivity();
//                                }
//                            }
//                        }).
//                                setNegativeButton("取消", null);
//                // 生产对话框
//                AlertDialog alertDialog = builder.create();
//                // 显示对话框
//                alertDialog.show();
//                return;
//            }
//        }
//        //有权限，开始安装应用程序
////        installApk(file);
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void startInstallPermissionSettingActivity() {
//        Uri packageURI = Uri.parse("package:" + getPackageName());
//        //注意这个是8.0新API
//        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
//        startActivityForResult(intent, 10086);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == 10086) {
//            installProcess(file);//再次执行安装流程，包含权限判等
//        }
//    }

    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
//    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception {
//        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            URL url = new URL(uri);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(5000);
//            //获取到文件的大小
//            pd.setMax(conn.getContentLength());
//            InputStream is = conn.getInputStream();
//            long time = System.currentTimeMillis();//当前时间的毫秒数
//            File file = new File(Environment.getExternalStorageDirectory(), time + "updata.apk");
//            FileOutputStream fos = new FileOutputStream(file);
//            BufferedInputStream bis = new BufferedInputStream(is);
//            byte[] buffer = new byte[1024];
//            int len;
//            int total = 0;
//            while ((len = bis.read(buffer)) != -1) {
//                fos.write(buffer, 0, len);
//                total += len;
//                //获取当前下载量
//                pd.setProgress(total);
//            }
//            fos.close();
//            bis.close();
//            is.close();
//            return file;
//        } else {
//            return null;
//        }
//    }

    /**
     * 安装apk
     */
//    protected void installApk(File file) {
//        Intent intent = new Intent();
//        //执行动作
//        intent.setAction(Intent.ACTION_VIEW);
//        //执行的数据类型
//        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        startActivity(intent);
//    }

    //获取apk
    private void httpCheck() {

        if (MyApplication.getUser() == null) {
            return;
        }
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConnectUrl.USER_VISION, RequestMethod.POST);
        try {
            /*上传本地项目版本号*/
            request.add("version", getVersionCode());
            request.add("type", "android");
        } catch (Exception e) {
            /*打印异常信息*/
            e.printStackTrace();
        }
        /*与服务端交互数据*/
        RequestQueue mQueue = NoHttp.newRequestQueue();
        mQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                if (what == 1) {
                    String resultCode = null;
                    try {
                        JSONObject jsonObject = response.get();
                        resultCode = jsonObject.getString("code");
                        if (resultCode.equals("200")) {
                            url = jsonObject.getString("data");
                            checkVersion();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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

}
