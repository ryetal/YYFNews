package com.yyf.www.project_quicknews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.activity.SearchActivity;
import com.yyf.www.project_quicknews.adapter.HomeAdapter;

public class HomeFragment extends BaseFragment {

    private ViewPager vpHome;
    private TabLayout tlytHome;
    private ImageView ivSearch;

    private HomeAdapter mAdapter;

    public HomeFragment() {
        // Required empty public constructor
        mFragmentName = "home";
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void getViews() {
        super.getViews();

        tlytHome = (TabLayout) mRootView.findViewById(R.id.tlytVideo);
        ivSearch = (ImageView) mRootView.findViewById(R.id.ivSearch);
        vpHome = (ViewPager) mRootView.findViewById(R.id.vpVideo);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //设置ViewPager
        mAdapter = new HomeAdapter(getChildFragmentManager());
        vpHome.setAdapter(mAdapter);

        //设置TabLayout
        tlytHome.setTabMode(TabLayout.MODE_SCROLLABLE);
        tlytHome.setupWithViewPager(vpHome);
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

}
