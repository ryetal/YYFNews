package com.yyf.www.project_quicknews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.activity.NewsDetailActivity;
import com.yyf.www.project_quicknews.adater.HomeChildAdapter;
import com.yyf.www.project_quicknews.bean.NewsBean;
import com.yyf.www.project_quicknews.global.GlobalValues;
import com.yyf.www.project_quicknews.view.LoadListView;

import java.util.List;

public class HomeChildFragment extends BaseNetworkFragment<NewsBean> {

    private static final int SIZE = 20;

    private String mBaseURL;
    private String mURL;

    //Child Type
    public enum ChildType {
        Recommend, Hot, Video, Society, Episode, Picture, Entertainment, Technology
    }

    //View
    private SwipeRefreshLayout srlytHomeChild;
    private LoadListView lvHomeChild;
    private LinearLayout llytEmptyView;
    private TextView tvEmptyView;

    private ChildType mChildType;
    private HomeChildAdapter mAdapter;

    private boolean mCleared;

    public HomeChildFragment() {
        // Required empty public constructor
    }

    /**
     * 工厂方法，获取Fragment实例
     *
     * @param childType
     * @return
     */
    public static HomeChildFragment newInstance(ChildType childType) {
        HomeChildFragment fragment = new HomeChildFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("childType", childType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChildType = (ChildType) getArguments().getSerializable("childType");
        switch (mChildType) {
            case Recommend:
                mClazz = NewsBean.class;
                mIsDataset = true;
                mBaseURL = GlobalValues.URL_NEWS + "&type=0";
                index = 0;
                break;
            case Hot:
                mClazz = NewsBean.class;
                mIsDataset = true;
                mBaseURL = GlobalValues.URL_NEWS + "&type=1";
                index = 1;
                break;
            case Video:
                mClazz = NewsBean.class;
                mIsDataset = true;
                mBaseURL = GlobalValues.URL_NEWS + "&type=2";
                index = 2;
                break;
            case Society:
                mClazz = NewsBean.class;
                mIsDataset = true;
                mBaseURL = GlobalValues.URL_NEWS + "&type=3";
                index = 3;
                break;
            case Episode:
                mClazz = NewsBean.class;
                mIsDataset = true;
                mBaseURL = GlobalValues.URL_NEWS + "&type=4";
                index = 4;
                break;
            case Picture:
                mClazz = NewsBean.class;
                mIsDataset = true;
                mBaseURL = GlobalValues.URL_NEWS + "&type=5";
                index = 5;
                break;
            case Entertainment:
                mClazz = NewsBean.class;
                mIsDataset = true;
                mBaseURL = GlobalValues.URL_NEWS + "&type=6";
                index = 6;
                break;
            case Technology:
                mClazz = NewsBean.class;
                mIsDataset = true;
                mBaseURL = GlobalValues.URL_NEWS + "&type=7";
                index = 7;
                break;
        }
        mURL = mBaseURL + "&offset=%d&size=" + SIZE;
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_child, container, false);
    }

    /**
     * 获取View
     */
    @Override
    protected void getViews() {
        super.getViews();

        srlytHomeChild = (SwipeRefreshLayout) mRootView.findViewById(R.id.srlytHomeChild);
        lvHomeChild = (LoadListView) mRootView.findViewById(R.id.lvHomeChild);
        llytEmptyView = (LinearLayout) mRootView.findViewById(R.id.llytEmptyView);
        tvEmptyView = (TextView) mRootView.findViewById(R.id.tvEmptyView);
    }

    /**
     * 初始化View
     */
    @Override
    protected void initViews() {
        super.initViews();

        //初始化ListView
        mAdapter = new HomeChildAdapter(getContext());
        lvHomeChild.setAdapter(mAdapter);
        lvHomeChild.setEmptyView(llytEmptyView);

        srlytHomeChild.setRefreshing(false);
    }

    /**
     * 设置Listener
     */
    @Override
    protected void setListeners() {
        super.setListeners();

        //刷新
        srlytHomeChild.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mCleared = true;
                doRequest(String.format(mURL, 0));
            }
        });

        lvHomeChild.setOnLoadScrollListener(new LoadListView.OnLoadScrollListener() {

            @Override
            public void onLoad() {

                mCleared = false;
                doRequest(String.format(mURL, mAdapter.getCount()));
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //设置SwipeRefreshLayout，解决与EmptyView的冲突
                if (lvHomeChild.getChildCount() == 0) {
                    srlytHomeChild.setEnabled(true);
                } else if (firstVisibleItem == 0 && lvHomeChild.getChildAt(0).getTop() == 0) {
                    srlytHomeChild.setEnabled(true);
                } else {
                    srlytHomeChild.setEnabled(false);
                }
            }
        });

        lvHomeChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                NewsBean news = (NewsBean) mAdapter.getItem(position);
                bundle.putInt("newsId",news.getId());
                bundle.putString("newsDetailURL", news.getUrl());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initDatas() {
        super.initDatas();

        doRequest(String.format(mURL, 0));
    }

    @Override
    void doBeforeOneRequest() {
        tvEmptyView.setText("正在加载...");
    }

    @Override
    void doWhenGetDatasFromServerSuccess() {

        reInitViews();

        List<NewsBean> datas = (List<NewsBean>) mResult.data;
        if (datas.size() == 0) {
            if (this.getUserVisibleHint()) {
                Toast.makeText(getContext(), "没有数据了", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        if (mCleared) {
            mAdapter.resetDatas(datas);
        } else {
            mAdapter.addDatas(datas);
        }

    }

    @Override
    void doWhenGetDatasFromServerFailed() {
        reInitViews();
    }

    @Override
    void doWhenRequestFailed() {
        reInitViews();
    }

    private void reInitViews() {

        lvHomeChild.loadingFinished();
        srlytHomeChild.setRefreshing(false);
        tvEmptyView.setText("没有数据");
    }

}
