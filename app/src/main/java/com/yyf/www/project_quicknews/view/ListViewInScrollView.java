package com.yyf.www.project_quicknews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yyf.www.project_quicknews.R;

/**
 * Created by 子凡 on 2017/4/10.
 */

public class ListViewInScrollView extends ListView {

    private Context mContext;
    private View vFooter;
    private LinearLayout llytFooter;

    public ListViewInScrollView(Context context) {
        this(context, null);
    }

    public ListViewInScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.listViewStyle);
    }

    public ListViewInScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        vFooter = inflater.inflate(R.layout.footer, null, false);
        llytFooter = (LinearLayout) vFooter.findViewById(R.id.llytFooter);
        addFooterView(vFooter);
        hideFooter();
    }

    public void showFooter() {
        llytFooter.setVisibility(VISIBLE);
    }

    public void hideFooter() {
        llytFooter.setVisibility(GONE);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
