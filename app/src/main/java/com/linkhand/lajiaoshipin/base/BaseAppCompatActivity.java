package com.linkhand.lajiaoshipin.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import butterknife.ButterKnife;

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    public static final int NON_CODE = -1;
    /**
     * Log tag
     */
    protected static String TAG = null;
    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    /**
     * context
     */
    protected Context mContext = null;
    protected boolean netAvailable = true;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // overridePendingTransition
        if (getSharedPreferences("share_czf",MODE_PRIVATE).getString("LANGUAGE","").equals("english")){
            Resources resources=getResources();
            DisplayMetrics dm=resources.getDisplayMetrics();
            Configuration config=resources.getConfiguration();
            config.locale= Locale.ENGLISH;
            resources.updateConfiguration(config,dm);
        }
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        // EventBus.register
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }

        mContext = this;
        TAG = this.getClass().getSimpleName();

        // 加入到Activity栈
        BaseAppManager.getInstance().addActivity(this);

        /* 获取屏幕信息 */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        if (hasTitlebar()){
            setCustomTitle(getTitle());
            onNavigateClick();
        }
        if (hasSoft()){
            onEditOutSideClick();
        }
        initSubscription();
        initViewsAndEvents();
        super.onCreate(savedInstanceState);
    }
    protected abstract void initSubscription();

    protected abstract void setCustomTitle(CharSequence title);

    protected abstract boolean hasTitlebar();

    protected abstract void onNavigateClick();

    protected abstract boolean hasSoft();

    protected abstract void onEditOutSideClick();


    /**
     * <pre>
     * 要显示的正文内容，如拍品列表：ButterKnife.findById(this,R.id.list_auction);
     *     or
     *     ListView auctionList;
     *     return auctionList;
     * </pre>
     *
     * @return
     */
    protected View getLoadingTargetView() {
        return null;
    }

    protected abstract void onNetworkDisconnect();

    protected abstract void onNetworkConnected();

    /**
     * init activity view and bind event
     */
    protected abstract void initViewsAndEvents();

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 是否注册EventBus
     *
     * @return
     */
    protected boolean isBindEventBusHere() {
        return false;
    }

    /**
     * @param extras
     */
    protected void getBundleExtras(Bundle extras) {

    }

    /**
     * startActivity
     *
     * @param clazz target Activity
     */
    protected void go(Class<? extends Activity> clazz) {
        _goActivity(clazz, null, NON_CODE, false);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz  target Activity
     * @param bundle
     */
    protected void go(Class<? extends Activity> clazz, Bundle bundle) {
        _goActivity(clazz, bundle, NON_CODE, false);
    }


    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);

    }

    @Override
    protected void onDestroy() {
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }


    /**
     * ============= interval methods =================
     */

    /**
     * Activity 跳转
     *
     * @param clazz  目标activity
     * @param bundle 传递参数
     * @param finish 是否结束当前activity
     */
    private void _goActivity(Class<? extends Activity> clazz, Bundle bundle, int requestCode, boolean finish) {
        if (null == clazz) {
            throw new IllegalArgumentException("you must pass a target activity where to go.");
        }
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        if (requestCode > NON_CODE) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
        if (finish) {
            finish();
        }
    }

}
