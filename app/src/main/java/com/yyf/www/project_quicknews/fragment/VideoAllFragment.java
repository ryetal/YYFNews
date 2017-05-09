package com.yyf.www.project_quicknews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyf.www.project_quicknews.R;

public class VideoAllFragment extends BaseFragment {

    public VideoAllFragment() {
        // Required empty public constructor
        mFragmentName = "videoAll";
    }


    public static VideoAllFragment newInstance() {
        VideoAllFragment fragment = new VideoAllFragment();
        return fragment;
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_all, container, false);
    }
}
