package com.yyf.www.project_quicknews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.activity.NewsDetailActivity;
import com.yyf.www.project_quicknews.adapter.SearchResultAdapter;
import com.yyf.www.project_quicknews.bean.NewsBean;
import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.global.GlobalValues;
import com.yyf.www.project_quicknews.net.INewsService;
import com.yyf.www.project_quicknews.utils.ToastUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultFragment extends BaseFragment {

    private ListView lvSearchResult;
    private LinearLayout llytEmptyView;
    private TextView tvEmptyView;

    private String mKeyword;
    private SearchResultAdapter mAdapter;

    private INewsService mNewsService;
    private Call<ResultBean<List<NewsBean>>> mCall;

    public SearchResultFragment() {
        // Required empty public constructor
        mFragmentName = "searchResult";
    }

    /**
     * 工厂方法，获取Fragment实例
     *
     * @param keyword
     * @return
     */
    public static SearchResultFragment newInstance(String keyword) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeyword = getArguments().getString("keyword");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalValues.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mNewsService = retrofit.create(INewsService.class);
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }

    @Override
    protected void getViews() {
        super.getViews();

        lvSearchResult = (ListView) mRootView.findViewById(R.id.lvSearchResult);
        llytEmptyView = (LinearLayout) mRootView.findViewById(R.id.llytEmptyView);
        tvEmptyView = (TextView) mRootView.findViewById(R.id.tvEmptyView);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //初始化ListView
        mAdapter = new SearchResultAdapter(getContext().getApplicationContext(), mKeyword);
        lvSearchResult.setAdapter(mAdapter);
        lvSearchResult.setEmptyView(llytEmptyView);
        tvEmptyView.setText(getString(R.string.loading));
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        lvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        mCall = mNewsService.getNewsByKeyword(mKeyword);
        mCall.enqueue(new Callback<ResultBean<List<NewsBean>>>() {
                          @Override
                          public void onResponse(Call<ResultBean<List<NewsBean>>> call, Response<ResultBean<List<NewsBean>>> response) {

                              ResultBean<List<NewsBean>> result = response.body();

                              if (result.code == ResultBean.CODE_SQL_OPERATOR_ERROR) {
                                  ToastUtil.showToast(result.msg, Toast.LENGTH_SHORT);
                                  return;
                              }

                              if (result.code == ResultBean.CODE_QUERY_SUCCESS) {

                                  List<NewsBean> datas = result.data;
                                  if (datas == null) {
                                      ToastUtil.showToast(getString(R.string.no_data), Toast.LENGTH_SHORT);
                                      tvEmptyView.setText(getString(R.string.empty));
                                  } else {
                                      mAdapter.resetDatas(result.data);
                                  }
                              }

                          }

                          @Override
                          public void onFailure(Call<ResultBean<List<NewsBean>>> call, Throwable t) {
                              if (call.isCanceled()) {
                                  return;
                              }
                              ToastUtil.showToast("网络请求失败!", Toast.LENGTH_SHORT);
                          }
                      }

        );

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mCall != null) {
            mCall.cancel();
        }
    }

}
