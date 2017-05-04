package com.yyf.www.project_quicknews.activity;

import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.bean.TabBean;
import com.yyf.www.project_quicknews.global.GlobalValues;

import java.util.List;

public class MainActivity extends BaseActivity {

    private FragmentTabHost mTabHost;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
    }

    /**
     * 获取View
     */
    @Override
    protected void getViews() {
        mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
    }

    /**
     * 初始化View
     */
    @Override
    protected void initViews() {
        initTabHost();
    }

    /**
     * 初始化TabHost
     */
    private void initTabHost() {

        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        List<TabBean> tabs = GlobalValues.generateTabs();
        for (int i = 0; i < tabs.size(); i++) {
            TabBean tab = tabs.get(i);
            mTabHost.addTab(mTabHost.newTabSpec(tab.getTag())
                            .setIndicator(generateIndicator(tab.getTitle(), tab.getDrawableId())),
                    tab.getClazz(), null);
        }

    }

    /**
     * generate indicator
     *
     * @param title
     * @param drawableId
     * @return
     */
    private View generateIndicator(String title, int drawableId) {

        View view = LayoutInflater.from(this).inflate(R.layout.tabhost_indicator, null);
        ImageView ivIndicator = (ImageView) view.findViewById(R.id.ivIndicator);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        ivIndicator.setImageResource(drawableId);
        tvTitle.setText(title);

        return view;
    }


}
