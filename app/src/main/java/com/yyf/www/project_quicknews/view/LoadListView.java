package com.yyf.www.project_quicknews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yyf.www.project_quicknews.R;

public class LoadListView extends ListView implements AbsListView.OnScrollListener {

    //回调接口///////////////////////////////////////////////////////
    public interface OnLoadScrollListener extends AbsListView.OnScrollListener {
        void onLoad();
    }

    private OnLoadScrollListener mOnLoadScrollListener;

    public void setOnLoadScrollListener(OnLoadScrollListener onLoadScrollListener) {
        mOnLoadScrollListener = onLoadScrollListener;
    }
    /////////////////////////////////////////////////////////////////

    private View vFooter;
    private LinearLayout llytFooter;

    private int mLastVisibleItem;
    private int mTotalItemCount;
    private boolean isLoading;

    public LoadListView(Context context) {
        this(context, null);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.listViewStyle);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOnScrollListener(this);
        initFooter(); //添加Footer
    }

    /**
     * 添加footer
     */
    private void initFooter() {
        vFooter = LayoutInflater.from(getContext()).inflate(R.layout.footer, null);
        llytFooter = (LinearLayout) vFooter.findViewById(R.id.llytFooter);
        this.addFooterView(vFooter);
    }

    /**
     * 隐藏footer
     */
    private void hideFooter() {
        llytFooter.setVisibility(GONE);
    }

    /**
     * 显示footer
     */
    private void showFooter() {
        llytFooter.setVisibility(VISIBLE);
    }

    /**
     * 加载完成
     */
    public void loadingFinished() {
        isLoading = false;
        hideFooter();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (mOnLoadScrollListener != null) {
            mOnLoadScrollListener.onScrollStateChanged(view, scrollState);
        }

        //当停止滚动并且显示最后一条数据时，加载更多数据
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && mLastVisibleItem == mTotalItemCount) {

            if (isLoading) {
                return;
            }
            isLoading = true;
            if (mOnLoadScrollListener != null) {
                showFooter();
                mOnLoadScrollListener.onLoad();
            }
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (mOnLoadScrollListener != null) {
            mOnLoadScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

        mLastVisibleItem = firstVisibleItem + visibleItemCount;
        mTotalItemCount = totalItemCount;
    }
}
