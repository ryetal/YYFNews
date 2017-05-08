package com.yyf.www.project_quicknews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.yyf.www.project_quicknews.application.NewsApplication;

public abstract class  BaseFragment extends Fragment {

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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIsFirst = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

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

        //内存泄漏watcher
        RefWatcher refWatcher = NewsApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //生命周期：end/////////////////////////////////////////////////////////////////////////////////
}
