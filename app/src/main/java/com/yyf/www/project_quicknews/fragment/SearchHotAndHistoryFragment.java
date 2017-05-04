package com.yyf.www.project_quicknews.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyf.www.project_quicknews.R;


public class SearchHotAndHistoryFragment extends BaseFragment {

    private FragmentManager mFragmentManager;
    private SearchHotFragment mSearchHotFragment;
    private SearchHistoryFragment mSearchHistoryFragment;

    public SearchHotAndHistoryFragment() {
        // Required empty public constructor
    }

    public static SearchHotAndHistoryFragment newInstance() {
        SearchHotAndHistoryFragment fragment = new SearchHotAndHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getChildFragmentManager();
        mSearchHotFragment = SearchHotFragment.newInstance(9);
        mSearchHistoryFragment = SearchHistoryFragment.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //生命周期:end//////////////////////////////////////////////////////////////////////////////////

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_hot_and_history, container, false);
    }

    /**
     * 获取View
     */
    @Override
    protected void getViews() {

    }

    /**
     * 初始化View
     */
    @Override
    protected void initViews() {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flytSearchHotContainer, mSearchHotFragment, "tag1");
        fragmentTransaction.add(R.id.flytSearchHistoryContainer, mSearchHistoryFragment, "tag2");
        fragmentTransaction.commit();

    }

    /**
     * 设置Listener
     */
    @Override
    protected void setListeners() {

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initDatas() {

    }

}
