package com.yyf.www.project_quicknews.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.fragment.SearchResultFragment;

public class SearchResultActivity extends BaseActivity {

    private Toolbar tbarSearchResult;
    private FragmentManager mFragmentManager;
    private SearchResultFragment mSearchResultFragment;

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

    protected void getViews() {
        super.getViews();

        tbarSearchResult = (Toolbar) findViewById(R.id.tbarSearchResult);
    }

    protected void initViews() {
        super.initViews();

        mFragmentManager.beginTransaction()
                .add(R.id.flytContainer, mSearchResultFragment, "tag")
                .commit();
    }

    protected void setListeners() {
        super.setListeners();

        tbarSearchResult.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
