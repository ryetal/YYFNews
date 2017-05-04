package com.yyf.www.project_quicknews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.adater.CommentAdapter;
import com.yyf.www.project_quicknews.bean.CommentBean;
import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.global.GlobalValues;
import com.yyf.www.project_quicknews.net.ICommentService;
import com.yyf.www.project_quicknews.view.ListViewInScrollView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentFragment extends BaseFragment {

    private static final int SIZE = 10;

    private ListViewInScrollView lvComments;
    private CommentAdapter mAdapter;
    private boolean isLoading;
    private int mNewsId;

    private ICommentService mCommentService;

    public CommentFragment() {
        // Required empty public constructor
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

    /**
     * 获取View
     */
    @Override
    protected void getViews() {
        lvComments = (ListViewInScrollView) mRootView.findViewById(R.id.lvComments);
    }

    /**
     * 初始化View
     */
    @Override
    protected void initViews() {

        mAdapter = new CommentAdapter(getContext());
        lvComments.setAdapter(mAdapter);
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

    public boolean isLoading() {
        return isLoading;
    }

    public void startLoading() {
        this.isLoading = true;
        lvComments.showFooter();
        requestComments(mAdapter.getCount());
    }

    /**
     * 发起网络请求
     */
    private void requestComments(int offset) {

        Call<ResultBean<List<CommentBean>>> call = mCommentService.getComments("getComments", mNewsId, offset, SIZE);
        call.enqueue(new Callback<ResultBean<List<CommentBean>>>() {
            @Override
            public void onResponse(Call<ResultBean<List<CommentBean>>> call, Response<ResultBean<List<CommentBean>>> response) {

                List<CommentBean> datas = response.body().data;

                if (datas.size() == 0) {
                    Toast.makeText(getContext(), "没有评论了", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAdapter.addDatas(datas);
                lvComments.hideFooter();
                isLoading = false;
            }

            @Override
            public void onFailure(Call<ResultBean<List<CommentBean>>> call, Throwable t) {
                Toast.makeText(getContext(), "请求失败" + t.getMessage(), Toast.LENGTH_SHORT).show();
                lvComments.hideFooter();
                isLoading = false;
            }
        });
    }

}
