package com.yyf.www.project_quicknews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyf.www.project_quicknews.R;

public class CareFragment extends BaseFragment {

    public CareFragment() {
        // Required empty public constructor
        mFragmentName = "care";
    }

    public static CareFragment newInstance() {
        CareFragment fragment = new CareFragment();
        return fragment;
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_care, container, false);
    }

}
