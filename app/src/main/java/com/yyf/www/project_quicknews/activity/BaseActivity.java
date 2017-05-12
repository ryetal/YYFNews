package com.yyf.www.project_quicknews.activity;

import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.yyf.www.project_quicknews.application.NewsApplication;
import com.yyf.www.project_quicknews.receiver.NetworkStatusReceiver;

public abstract class BaseActivity extends AppCompatActivity {

    private NetworkStatusReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getContentViewId());

        NewsApplication.addActivity(this, this);

        //注册网络状态变化广播
        registerNetworkStatusReceiver();

        init();
        getViews();
        initViews();
        setListeners();
        initDatas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
        NewsApplication.removeActivity(this, this);
    }

    /**
     * 注册网络状态变化广播
     */
    private void registerNetworkStatusReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new NetworkStatusReceiver();
        registerReceiver(mReceiver, intentFilter);
    }

    protected abstract int getContentViewId();

    protected void init() {

    }

    protected void getViews() {

    }

    protected void initViews() {

    }

    protected void setListeners() {

    }

    protected void initDatas() {

    }

}
