package com.yyf.www.project_quicknews.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.activity.SearchActivity;
import com.yyf.www.project_quicknews.adater.HomeAdapter;

public class HomeFragment extends BaseFragment {

    private ViewPager vpHome;
    private TabLayout tlytHome;
    private ImageView ivSearch;

    private HomeAdapter mAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    //生命周期:begin////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //生命周期：end/////////////////////////////////////////////////////////////////////////////////

    /**
     * inflate root view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /**
     * 获取View
     */
    @Override
    protected void getViews() {

        super.getViews();

        vpHome = (ViewPager) mRootView.findViewById(R.id.vpVideo);
        tlytHome = (TabLayout) mRootView.findViewById(R.id.tlytVideo);
        ivSearch = (ImageView) mRootView.findViewById(R.id.ivSearch);
    }

    /**
     * 初始化View
     */
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

    /**
     * 设置Listener
     */
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
