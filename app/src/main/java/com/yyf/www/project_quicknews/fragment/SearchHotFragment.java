package com.yyf.www.project_quicknews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.adapter.SearchHotAdapter;
import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.bean.event.SearchEvent;
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
    private Call<ResultBean<List<String>>> mCall;

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

    @Override
    protected void getViews() {
        gvSearchHot = (GridView) mRootView.findViewById(R.id.gvSearchHot);
    }

    @Override
    protected void initViews() {

        mAdapter = new SearchHotAdapter(getContext().getApplicationContext(), mItemCount);
        gvSearchHot.setAdapter(mAdapter);
    }

    @Override
    protected void setListeners() {

        gvSearchHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keyword = (String) mAdapter.getItem(position);
                EventBus.getDefault().post(new SearchEvent(keyword)); //发送搜索事件
            }
        });

    }

    @Override
    protected void initDatas() {

        mCall = mSearchHotServicae.getSearchHot();
        mCall.enqueue(new Callback<ResultBean<List<String>>>() {
            @Override
            public void onResponse(Call<ResultBean<List<String>>> call, Response<ResultBean<List<String>>> response) {

                ResultBean<List<String>> result = response.body();

                if (result.code == ResultBean.CODE_ERROR) {
                    Toast.makeText(getContext().getApplicationContext(), result.msg, Toast.LENGTH_SHORT).show();
                } else if (result.code == ResultBean.CODE_DATASET_EMPTY) {
                    Toast.makeText(getContext().getApplicationContext(), "没有热门推荐数据", Toast.LENGTH_SHORT).show();
                } else if (result.code == ResultBean.CODE_DATASET_NOT_EMPTY) {
                    List<String> datas = result.data;
                    mAdapter.addDatas(datas);
                }
            }

            @Override
            public void onFailure(Call<ResultBean<List<String>>> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "网络请求失败!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mCall != null) {
            mCall.cancel();
        }
    }

}
