package com.yyf.www.project_quicknews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.bean.SearchHistoryBean;
import com.yyf.www.project_quicknews.bean.SearchKeywordBean;
import com.yyf.www.project_quicknews.fragment.SearchHotAndHistoryFragment;
import com.yyf.www.project_quicknews.fragment.SearchListFragment;
import com.yyf.www.project_quicknews.view.SearchEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SearchActivity extends BaseActivity {

    private ImageView ivBack;
    private SearchEditText etSearch;
    private FrameLayout flytContainer;

    private FragmentManager mFragmentManager;
    private SearchHotAndHistoryFragment mSearchHotAndHistoryFragment;
    private SearchListFragment mSearchListFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {
        super.init();

        EventBus.getDefault().register(this);

        mFragmentManager = getSupportFragmentManager();
        mSearchHotAndHistoryFragment = SearchHotAndHistoryFragment.newInstance();
        mSearchListFragment = SearchListFragment.newInstance();
    }

    /**
     * 获取View
     */
    @Override
    protected void getViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        etSearch = (SearchEditText) findViewById(R.id.etSearch);
        flytContainer = (FrameLayout) findViewById(R.id.flytSearchContainer);
    }

    /**
     * 初始化View
     */
    @Override
    protected void initViews() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.flytSearchContainer, mSearchHotAndHistoryFragment, "searchHotAndHistory");
        transaction.add(R.id.flytSearchContainer, mSearchListFragment, "searchList");
        transaction.hide(mSearchListFragment);
        transaction.commit();

    }

    /**
     * 设置Listener
     */
    @Override
    protected void setListeners() {

        //回退
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // SearchEditText的软键盘的 EditorAction 事件
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                doSearch(new SearchKeywordBean(v.getText().toString()));
                return false;
            }
        });

        //SearchEditText 的search图标与delete图标的点击回调事件
        etSearch.setOnSearchEditTextListener(new SearchEditText.OnSearchEditTextListener() {
            @Override
            public void onClear() {

            }

            @Override
            public void onSearch(String content) {

                if (content != null && !content.equals("")) {
                    doSearch(new SearchKeywordBean(content));
                }
            }
        });

        //SearchEditText的Text Change事件
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (TextUtils.isEmpty(s)) {
                    mFragmentManager.beginTransaction().hide(mSearchListFragment).commit();
                    mFragmentManager.beginTransaction().show(mSearchHotAndHistoryFragment).commit();
                } else {
                    mSearchListFragment.updateSearchListItems(s.toString());
                    mFragmentManager.beginTransaction().hide(mSearchHotAndHistoryFragment).commit();
                    mFragmentManager.beginTransaction().show(mSearchListFragment).commit();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doSearch(SearchKeywordBean searchKeywordBean) {

        EventBus.getDefault().post(new SearchHistoryBean(searchKeywordBean.keyword));

        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("keyword", searchKeywordBean.keyword);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
