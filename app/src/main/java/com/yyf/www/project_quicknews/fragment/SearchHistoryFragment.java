package com.yyf.www.project_quicknews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.adapter.SearchHistoryAdapter;
import com.yyf.www.project_quicknews.bean.event.SearchHistoryEvent;
import com.yyf.www.project_quicknews.bean.event.SearchEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class SearchHistoryFragment extends BaseFragment {

    private List<String> mHistories = new ArrayList<>();

    private ListView lvSearchHistory;
    private SearchHistoryAdapter mAdapter;

    public SearchHistoryFragment() {
        // Required empty public constructor
    }

    public static SearchHistoryFragment newInstance() {
        SearchHistoryFragment fragment = new SearchHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_history, null, false);
    }

    @Override
    protected void getViews() {
        lvSearchHistory = (ListView) mRootView.findViewById(R.id.lvSearchHistory);
    }

    @Override
    protected void initViews() {

        mAdapter = new SearchHistoryAdapter(getContext());
        lvSearchHistory.setAdapter(mAdapter);
    }

    @Override
    protected void setListeners() {

        lvSearchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keyword = (String) mAdapter.getItem(position);
                EventBus.getDefault().post(new SearchEvent(keyword));
            }
        });

    }

    @Override
    protected void initDatas() {

        //TODO 修改为从数据库中读取history到mHistories中
        mHistories.add("泰迪");
        mHistories.add("冰淇淋");

        mAdapter.resetDatas(mHistories);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchHistoryEvent(SearchHistoryEvent searchHistoryBean) {

        String data = searchHistoryBean.history;
        if (!mAdapter.getDatas().contains(data)) {
            mAdapter.addData(data);
        }

    }

}
