package com.yyf.www.project_quicknews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.adater.SearchHotAdapter;
import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.bean.SearchKeywordBean;
import com.yyf.www.project_quicknews.global.GlobalValues;
import com.yyf.www.project_quicknews.net.ISearchHotService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchHotFragment extends BaseFragment {

    private GridView gvSearchHot;
    private SearchHotAdapter mAdapter;
    private int mItemCount = 9;

    private ISearchHotService mSearchHotServicae;

    public SearchHotFragment() {
        // Required empty public constructor
    }

    public static SearchHotFragment newInstance(int hotCount) {
        SearchHotFragment fragment = new SearchHotFragment();
        Bundle args = new Bundle();
        args.putInt("hotCount", hotCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemCount = getArguments().getInt("hotCount");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalValues.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mSearchHotServicae = retrofit.create(ISearchHotService.class);
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_hot, container, false);
    }

    /**
     * 获取View
     */
    @Override
    protected void getViews() {
        gvSearchHot = (GridView) mRootView.findViewById(R.id.gvSearchHot);
    }

    /**
     * 初始化View
     */
    @Override
    protected void initViews() {

        mAdapter = new SearchHotAdapter(getContext(), mItemCount);
        gvSearchHot.setAdapter(mAdapter);
    }

    /**
     * 设置Listener
     */
    @Override
    protected void setListeners() {

        gvSearchHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        Call<ResultBean<List<String>>> call = mSearchHotServicae.getSearchHot();
        call.enqueue(new Callback<ResultBean<List<String>>>() {
            @Override
            public void onResponse(Call<ResultBean<List<String>>> call, Response<ResultBean<List<String>>> response) {
                List<String> datas = response.body().data;
                mAdapter.addDatas(datas);
            }

            @Override
            public void onFailure(Call<ResultBean<List<String>>> call, Throwable t) {

            }
        });
    }

}
