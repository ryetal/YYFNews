package com.yyf.www.project_quicknews.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.yyf.www.project_quicknews.application.NewsApplication;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        NewsApplication.addActivity(this, this);

        setContentView(getContentViewId());
        init();
        getViews();
        initViews();
        setListeners();
        initDatas();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        NewsApplication.removeActivity(this, this);
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
