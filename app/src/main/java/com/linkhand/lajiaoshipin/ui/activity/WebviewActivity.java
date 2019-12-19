package com.linkhand.lajiaoshipin.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        WebSettings webSettings = webview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);

        webview.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉
        webview.loadUrl(url);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras!=null){
            url = extras.getString("url");
        }
    }
}
