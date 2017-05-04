package com.yyf.www.project_quicknews.activity;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.fragment.SearchResultFragment;

public class SearchResultActivity extends BaseActivity {

    private FragmentManager mFragmentManager;
    private SearchResultFragment mSearchResultFragment;

    private ImageView ivBack;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void init() {
        super.init();

        String keyword = getIntent().getExtras().getString("keyword");
        mFragmentManager = getSupportFragmentManager();
        mSearchResultFragment = SearchResultFragment.newInstance(keyword);
    }

    /**
     * 获取View
     */
    protected void getViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
    }

    /**
     * 初始化View
     */
    protected void initViews() {

        mFragmentManager.beginTransaction()
                .add(R.id.flytContainer, mSearchResultFragment, "tag")
                .commit();
    }

    /**
     * 设置Listener
     */
    protected void setListeners() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 初始化数据
     */
    protected void initDatas() {

    }


}
