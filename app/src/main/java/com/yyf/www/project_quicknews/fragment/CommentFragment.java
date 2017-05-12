package com.yyf.www.project_quicknews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.adapter.CommentAdapter;
import com.yyf.www.project_quicknews.bean.CommentBean;
import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.global.GlobalValues;
import com.yyf.www.project_quicknews.net.ICommentService;
import com.yyf.www.project_quicknews.utils.ToastUtil;
import com.yyf.www.project_quicknews.view.ListViewInScrollView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentFragment extends BaseFragment {

    private static final int SIZE = 10; //每次加载的数据量

    private ListViewInScrollView lvComments;
    private CommentAdapter mAdapter;

    private boolean isLoading; //是否正在加载
    private boolean isCompleteLoading; //是否完成了全部数据的加载
    private int mNewsId; //新闻id

    private ICommentService mCommentService;
    private Call<ResultBean<List<CommentBean>>> mCall;

    public CommentFragment() {
        // Required empty public constructor
        mFragmentName = "comment";
    }

    public static CommentFragment newInstance(int newsId) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putInt("newsId", newsId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mNewsId = getArguments().getInt("newsId");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalValues.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mCommentService = retrofit.create(ICommentService.class);
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    protected void getViews() {
        lvComments = (ListViewInScrollView) mRootView.findViewById(R.id.lvComments);
    }

    @Override
    protected void initViews() {

        mAdapter = new CommentAdapter(getContext());
        lvComments.setAdapter(mAdapter);
    }

    /*
     * 是否正在加载数据
     */
    public boolean isLoading() {
        return isLoading;
    }

    /*
     * 是否完成了全部数据的加载
     */
    public boolean isCompleteLoading() {
        return isCompleteLoading;
    }

    /*
     * 开始加载数据
     */
    public void startLoading() {
        this.isLoading = true;
        lvComments.showFooter();
        doRequest(mAdapter.getCount());
    }

    /*
     * 一次网络请求
     */
    private void doRequest(int offset) {

        mCall = mCommentService.getComments(mNewsId, offset, SIZE);
        mCall.enqueue(new Callback<ResultBean<List<CommentBean>>>() {
            @Override
            public void onResponse(Call<ResultBean<List<CommentBean>>> call, Response<ResultBean<List<CommentBean>>> response) {

                lvComments.hideFooter();
                isLoading = false;

                ResultBean<List<CommentBean>> result = response.body();

                if (result.code == ResultBean.CODE_SQL_OPERATOR_ERROR) {
                    ToastUtil.showToast(result.msg, Toast.LENGTH_SHORT);
                    return;
                }


                if (result.code == ResultBean.CODE_QUERY_SUCCESS) {

                    List<CommentBean> datas = result.data;

                    if (datas == null) {
                        isCompleteLoading = true;
                        lvComments.completeLoading();
                        mAdapter.notifyDataSetChanged();  //需要这一句，否则无法显示footer
                    } else {
                        if (datas.size() < SIZE) {
                            isCompleteLoading = true;
                            lvComments.completeLoading();
                        }
                        mAdapter.addDatas(datas);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResultBean<List<CommentBean>>> call, Throwable t) {
                if (call.isCanceled()) {
                    return;
                }
                ToastUtil.showToast("网络请求失败!", Toast.LENGTH_SHORT);
                lvComments.hideFooter();
                isLoading = false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mCall != null) {  //界面不可见的时候，cancel网络请求
            mCall.cancel();
        }
    }
}
