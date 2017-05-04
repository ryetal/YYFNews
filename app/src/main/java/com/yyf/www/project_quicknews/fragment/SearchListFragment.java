package com.yyf.www.project_quicknews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.adater.SearchListAdapter;
import com.yyf.www.project_quicknews.bean.SearchKeywordBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class SearchListFragment extends BaseFragment {

    private ListView lvSearchList;
    private SearchListAdapter mAdapter;

    public SearchListFragment() {
        // Required empty public constructor
    }

    public static SearchListFragment newInstance() {
        SearchListFragment fragment = new SearchListFragment();
        return fragment;
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_list, container, false);
    }

    /**
     * 获取View
     */
    @Override
    protected void getViews() {
        lvSearchList = (ListView) mRootView.findViewById(R.id.lvSearchList);
    }

    /**
     * 初始化View
     */
    @Override
    protected void initViews() {

        mAdapter = new SearchListAdapter(getContext());
        lvSearchList.setAdapter(mAdapter);
    }

    /**
     * 设置Listener
     */
    @Override
    protected void setListeners() {

        lvSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keyword = (String) mAdapter.getItem(position);
                EventBus.getDefault().post(new SearchKeywordBean(keyword));

            }
        });

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initDatas() {

    }

    public void updateSearchListItems(String keyword) {

        //TODO 修改为从服务器上获取数据
        List<String> datas = new ArrayList<>();
        int size = (int) (Math.random() * 30);
        for (int i = 0; i < size; i++) {
            datas.add(keyword + " " + i);
        }

        mAdapter.resetDatas(datas);
    }

}
