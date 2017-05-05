package com.yyf.www.project_quicknews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yyf.www.project_quicknews.R;

/**
 * Created by 子凡 on 2017/4/10.
 */

public class ListViewInScrollView extends ListView {

    private Context mContext;
    private View vFooter;
    private LinearLayout llytFooter;
    private ProgressBar pbarFooter;
    private TextView tvFooter;

    public ListViewInScrollView(Context context) {
        this(context, null);
    }

    public ListViewInScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.listViewStyle);
    }

    public ListViewInScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        addFooter();
    }

    /**
     * 添加footer
     */
    private void addFooter() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        vFooter = inflater.inflate(R.layout.footer, null, false);
        llytFooter = (LinearLayout) vFooter.findViewById(R.id.llytFooter);
        pbarFooter = (ProgressBar) vFooter.findViewById(R.id.pbarFooter);
        tvFooter = (TextView) vFooter.findViewById(R.id.tvFooter);
        tvFooter.setText("正在加载...");
        addFooterView(vFooter);
    }

    public void showFooter() {
        llytFooter.setVisibility(VISIBLE);
    }

    public void hideFooter() {
        llytFooter.setVisibility(GONE);
    }

    public void completeLoading() {
        pbarFooter.setVisibility(View.GONE);
        tvFooter.setText("没有更多内容了");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
