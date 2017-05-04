package com.yyf.www.project_quicknews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.fragment.CommentFragment;
import com.yyf.www.project_quicknews.view.BottomScrollView;

public class NewsDetailActivity extends BaseActivity {

    private BottomScrollView scrollView;
    private Toolbar tbarNews;
    private ProgressBar pbarNews;
    private WebView wvNews;
    private FrameLayout flytCommentContainer;

    private int mNewsId;
    private String mNewsDetailURL;

    private FragmentManager mFragmentManager;
    private CommentFragment mCommentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wvNews.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wvNews.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ((ViewGroup) wvNews.getParent()).removeView(wvNews);
        wvNews.destroy();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void init() {
        super.init();

        mFragmentManager = getSupportFragmentManager();

        mNewsDetailURL = getIntent().getExtras().getString("newsDetailURL");
        mNewsId = getIntent().getExtras().getInt("newsId");
    }

    /**
     * 获取View
     */
    @Override
    protected void getViews() {

        scrollView = (BottomScrollView) findViewById(R.id.scrollView);
        tbarNews = (Toolbar) findViewById(R.id.tbarNews);
        pbarNews = (ProgressBar) findViewById(R.id.pbarNews);
        wvNews = (WebView) findViewById(R.id.wvNews);
        flytCommentContainer = (FrameLayout) findViewById(R.id.flytCommentContainer);
    }

    /**
     * 初始化View
     */
    @Override
    protected void initViews() {

        //初始化Toolbar
        tbarNews.setNavigationIcon(R.drawable.back);
        tbarNews.setTitle("新闻");
        setSupportActionBar(tbarNews);

        //初始化WebView
        wvNews.getSettings().setJavaScriptEnabled(true);

        //添加comment fragment
        mCommentFragment = CommentFragment.newInstance(mNewsId);
        mFragmentManager.beginTransaction().add(R.id.flytCommentContainer,
                mCommentFragment, "tag").commit();

    }

    /**
     * 设置Listener
     */
    @Override
    protected void setListeners() {

        tbarNews.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        scrollView.setOnScrollToBottomLintener(new BottomScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollBottomListener(boolean isBottom) {

                if (isBottom) {

                    if (mCommentFragment.isLoading()) {
                        return;
                    }

                    mCommentFragment.startLoading();
                }
            }
        });

        wvNews.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });

        wvNews.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {
                    pbarNews.setVisibility(View.GONE);
                    flytCommentContainer.setVisibility(View.VISIBLE);
                } else {
                    flytCommentContainer.setVisibility(View.GONE);
                    if (pbarNews.getVisibility() == View.GONE) {
                        pbarNews.setVisibility(View.VISIBLE);
                    }
                    pbarNews.setProgress(newProgress);
                }
            }
        });

    }

    /**
     * 初始化data
     */
    @Override
    protected void initDatas() {
        wvNews.loadUrl(mNewsDetailURL);
    }

}
