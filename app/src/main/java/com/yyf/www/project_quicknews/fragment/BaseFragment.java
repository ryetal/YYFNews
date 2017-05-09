package com.yyf.www.project_quicknews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.yyf.www.project_quicknews.application.NewsApplication;
import com.yyf.www.project_quicknews.global.GlobalValues;

public abstract class BaseFragment extends Fragment {

    protected String mFragmentName = "NoName"; //为Fragment指定一个名字

    private boolean mIsFirst;
    protected View mRootView; //root view

    protected abstract View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected void getViews() {
    }

    protected void initViews() {
    }

    protected void setListeners() {
    }

    protected void initDatas() {
    }

    //生命周期：begin///////////////////////////////////////////////////////////////////////////////

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(GlobalValues.TAG, mFragmentName + " - 【onAttach】");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(GlobalValues.TAG, mFragmentName + " - 【onCreate】");

        mIsFirst = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(GlobalValues.TAG, mFragmentName + " - 【onCreateView】");

        if (mRootView == null) {
            mRootView = inflateRootView(inflater, container, savedInstanceState);
            mIsFirst = true;
            return mRootView;
        }

        mIsFirst = false;
        ViewGroup parentView = (ViewGroup) mRootView.getParent();
        if (parentView != null) {
            parentView.removeView(mRootView);
        }

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(GlobalValues.TAG, mFragmentName + " - 【onViewCreated】");

        if (mIsFirst) {
            getViews();
            initViews();
            setListeners();
            initDatas();
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(GlobalValues.TAG, mFragmentName + " - 【onActivityCreated】");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(GlobalValues.TAG, mFragmentName + " - 【onResume】");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(GlobalValues.TAG, mFragmentName + " - 【onPause】");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(GlobalValues.TAG, mFragmentName + " - 【onStop】");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(GlobalValues.TAG, mFragmentName + " - 【onDestroyView】");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(GlobalValues.TAG, mFragmentName + " - 【onDestroy】");

        //内存泄漏watcher
        RefWatcher refWatcher = NewsApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(GlobalValues.TAG, mFragmentName + " - 【onDetach】");
    }

    //生命周期：end/////////////////////////////////////////////////////////////////////////////////
}
