package com.yyf.www.project_quicknews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yyf.www.project_quicknews.R;

/**
 * 1.只在加载时显示【加载Footer】
 * 2.只在全部数据加载完成后显示【完成Footer】
 * 3.只有在加载失败时显示【失败Footer】
 * 4.其他情况下隐藏【Footer】
 */
public class LoadListView extends ListView implements AbsListView.OnScrollListener {

    public static final int STATUS_LOADING = 1; //正在加载数据，显示【加载Footer】
    public static final int STATUS_COMPLETE = 2; //全部数据已经加载完成，显示【完成Footer】
    public static final int STATUS_FAILED = 3; //只有在加载失败时显示【失败Footer】
    public static final int STATUS_HIDE = 4; //其他情况，footer 隐藏

    private int mCurStatus = STATUS_HIDE;

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
    private ProgressBar pbarFooter;
    private TextView tvFooter;

    private int mLastVisibleItem;
    private int mTotalItemCount;

    public LoadListView(Context context) {
        this(context, null);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.listViewStyle);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOnScrollListener(this);
        addFooter(); //添加Footer
        hideFooter();//隐藏Footer
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (mOnLoadScrollListener != null) {
            mOnLoadScrollListener.onScrollStateChanged(view, scrollState);
        }

        //已经显示了footer时，直接返回
        if (mCurStatus == STATUS_LOADING || mCurStatus == STATUS_COMPLETE) {
            return;
        }

        //未显示footer时，滚动到底则显示【加载footer】，加载更多数据
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && mLastVisibleItem == mTotalItemCount) {

            mCurStatus = STATUS_LOADING;
            showLoadingFooter();

            if (mOnLoadScrollListener != null) {
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


    /**
     * 添加footer
     */
    private void addFooter() {
        vFooter = LayoutInflater.from(getContext()).inflate(R.layout.footer, null);
        llytFooter = (LinearLayout) vFooter.findViewById(R.id.llytFooter);
        pbarFooter = (ProgressBar) vFooter.findViewById(R.id.pbarFooter);
        tvFooter = (TextView) vFooter.findViewById(R.id.tvFooter);
        addFooterView(vFooter);
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
     * 显示【加载Footer】
     */
    private void showLoadingFooter() {
        pbarFooter.setVisibility(View.VISIBLE);
        tvFooter.setText("正在加载...");
        showFooter();
    }

    /**
     * 显示【完成Footer】
     */
    private void showCompleteFooter() {
        pbarFooter.setVisibility(View.GONE);
        tvFooter.setText("没有更多内容了");
        showFooter();
    }

    /**
     * 显示【失败Footer】
     */
    private void showFailedFooter() {
        pbarFooter.setVisibility(View.GONE);
        tvFooter.setText("加载失败，请重试");
        showFooter();
    }

    /**
     * 一次Loading结束
     */
    public void doAfterOneLoading(int status) {

        switch (status) {
            case STATUS_COMPLETE:
                showCompleteFooter();
                break;
            case STATUS_FAILED:
                showFailedFooter();
                break;
            case STATUS_HIDE:
                hideFooter();
                break;
            default:
                throw new IllegalArgumentException("status must be STATUS_COMPLETE or " +
                        "STATUS_FAILED or STATUS_NONE");
        }

        mCurStatus = status;
    }
}
