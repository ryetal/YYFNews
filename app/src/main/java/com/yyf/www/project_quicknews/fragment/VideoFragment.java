package com.yyf.www.project_quicknews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyf.www.project_quicknews.R;

public class VideoFragment extends BaseFragment {

    public VideoFragment() {
        // Required empty public constructor
        mFragmentName = "video";
    }


    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

}
