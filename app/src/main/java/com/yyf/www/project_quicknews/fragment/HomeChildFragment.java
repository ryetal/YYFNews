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

import com.google.gson.reflect.TypeToken;
import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.activity.NewsDetailActivity;
import com.yyf.www.project_quicknews.adapter.HomeChildAdapter;
import com.yyf.www.project_quicknews.bean.NewsBean;
import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.global.GlobalValues;
import com.yyf.www.project_quicknews.okhttp.GsonParser;
import com.yyf.www.project_quicknews.utils.ToastUtil;
import com.yyf.www.project_quicknews.view.LoadListView;

import java.lang.reflect.Type;
import java.util.List;

public class HomeChildFragment<T extends ResultBean<List<NewsBean>>> extends BaseNetworkFragment<T> {

    private static final int SIZE = 20;  //每次加载20条数据

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
    private String mURL;
    private HomeChildAdapter mAdapter;
    private boolean mCleared;

    private Type mType = new TypeToken<ResultBean<List<NewsBean>>>() {
    }.getType();

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

        //设置Fragment的名称，方便日志查看
        switch (childType) {
            case Recommend:
                fragment.mFragmentName = "recommend";
                break;
            case Hot:
                fragment.mFragmentName = "hot";
                break;
            case Video:
                fragment.mFragmentName = "video";
                break;
            case Society:
                fragment.mFragmentName = "society";
                break;
            case Episode:
                fragment.mFragmentName = "episode";
                break;
            case Picture:
                fragment.mFragmentName = "picture";
                break;
            case Entertainment:
                fragment.mFragmentName = "enterainment";
                break;
            case Technology:
                fragment.mFragmentName = "techonology";
                break;
        }

        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChildType = (ChildType) getArguments().getSerializable("childType");
        switch (mChildType) {
            case Recommend:
                mURL = GlobalValues.BASE_URL + "servlet/NewsServlet?action=getNews&type=0";
                break;
            case Hot:
                mURL = GlobalValues.BASE_URL + "servlet/NewsServlet?action=getNews&type=1";
                break;
            case Video:
                mURL = GlobalValues.BASE_URL + "servlet/NewsServlet?action=getNews&type=2";
                break;
            case Society:
                mURL = GlobalValues.BASE_URL + "servlet/NewsServlet?action=getNews&type=3";
                break;
            case Episode:
                mURL = GlobalValues.BASE_URL + "servlet/NewsServlet?action=getNews&type=4";
                break;
            case Picture:
                mURL = GlobalValues.BASE_URL + "servlet/NewsServlet?action=getNews&type=5";
                break;
            case Entertainment:
                mURL = GlobalValues.BASE_URL + "servlet/NewsServlet?action=getNews&type=6";
                break;
            case Technology:
                mURL = GlobalValues.BASE_URL + "servlet/NewsServlet?action=getNews&type=7";
                break;
        }

        mURL = mURL + "&offset=%d&size=" + SIZE;
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_child, container, false);
    }

    @Override
    protected void getViews() {
        super.getViews();

        srlytHomeChild = (SwipeRefreshLayout) mRootView.findViewById(R.id.srlytHomeChild);
        lvHomeChild = (LoadListView) mRootView.findViewById(R.id.lvHomeChild);
        llytEmptyView = (LinearLayout) mRootView.findViewById(R.id.llytEmptyView);
        tvEmptyView = (TextView) mRootView.findViewById(R.id.tvEmptyView);
    }

    @Override
    protected void initViews() {
        super.initViews();

        tvEmptyView.setText(getString(R.string.empty));

        //初始化ListView
        mAdapter = new HomeChildAdapter(getContext().getApplicationContext());
        lvHomeChild.setAdapter(mAdapter);
        lvHomeChild.setEmptyView(llytEmptyView);

        srlytHomeChild.setRefreshing(false);
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        //刷新
        srlytHomeChild.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (doRequest(String.format(mURL, 0), new GsonParser<T>(mType))) {
                    mCleared = true; //发起了一次请求
                } else {
                    //没有发起请求：比如之前上拉加载更多发起了一次请求，但该次请求还未处理完，就进行了刷新。
                    srlytHomeChild.setRefreshing(false);
                }
            }
        });

        lvHomeChild.setOnLoadScrollListener(new LoadListView.OnLoadScrollListener() {

            @Override
            public void onLoad() {

                if (doRequest(String.format(mURL, mAdapter.getCount()), new GsonParser<T>(mType))) {
                    mCleared = false; //发起一次请求
                } else {
                    //没有发起请求：比如之前刷新发起了一次请求，但该次请求还未处理完，就进行了上拉加载更多。
                    lvHomeChild.doAfterOneLoading(LoadListView.STATUS_HIDE);
                }
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
                Intent intent = new Intent(getContext().getApplicationContext(), NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                NewsBean news = (NewsBean) mAdapter.getItem(position);
                bundle.putSerializable("news", news);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initDatas() {
        super.initDatas();

        if (doRequest(String.format(mURL, 0), new GsonParser<T>(mType))) {
            mCleared = true;
        }
    }

    @Override
    void doBeforeOneRequest() {
        tvEmptyView.setText(getResources().getString(R.string.loading));
    }

    @Override
    void doOnResponseSuccess(T result) {

        resetViewsAfterOneLoading();

        if (result.code == ResultBean.CODE_SQL_OPERATOR_ERROR) {
            ToastUtil.showToast(result.msg, Toast.LENGTH_SHORT);
            lvHomeChild.doAfterOneLoading(LoadListView.STATUS_HIDE);
            return;
        }

        if (result.code == ResultBean.CODE_QUERY_SUCCESS) {
            List<NewsBean> datas = result.data;
            if (datas == null) {
                lvHomeChild.doAfterOneLoading(LoadListView.STATUS_COMPLETE);
            } else {
                lvHomeChild.doAfterOneLoading(datas.size() < SIZE ?
                        LoadListView.STATUS_COMPLETE : LoadListView.STATUS_HIDE);
                resetViewsAfterOneLoading();
                if (mCleared) {
                    mAdapter.resetDatas(datas);
                } else {
                    mAdapter.addDatas(datas);
                }
            }
        }

    }

    @Override
    void doOnResponseFailure() {
        resetViewsAfterOneLoading();
        lvHomeChild.doAfterOneLoading(LoadListView.STATUS_HIDE);
    }

    @Override
    void doOnFailure() {
        resetViewsAfterOneLoading();
        ToastUtil.showToast("网络请求失败!", Toast.LENGTH_SHORT);
        lvHomeChild.doAfterOneLoading(LoadListView.STATUS_FAILED);
    }

    /**
     * 一次加载后，重置 View
     */
    private void resetViewsAfterOneLoading() {
        srlytHomeChild.setRefreshing(false);
        tvEmptyView.setText(getString(R.string.empty));
    }

}
