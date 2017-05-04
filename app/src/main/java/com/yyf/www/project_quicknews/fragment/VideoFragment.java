package com.yyf.www.project_quicknews.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.adater.VideoAdapter;

public class VideoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private View mRootView;
    private ViewPager vpVideo;
    private TabLayout tlytVideo;
    private VideoAdapter mAdapter;

    public VideoFragment() {
        // Required empty public constructor
    }


    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_video, container, false);
            getViews();
            initViews();
            return mRootView;
        }

        ViewGroup parentView = (ViewGroup) mRootView.getParent();
        if (parentView != null) {
            parentView.removeView(mRootView);
        }
        return mRootView;
    }

    /**
     * 获取View
     */
    private void getViews() {
        vpVideo = (ViewPager) mRootView.findViewById(R.id.vpVideo);
        tlytVideo = (TabLayout) mRootView.findViewById(R.id.tlytVideo);
    }

    /**
     * 初始化View
     */
    private void initViews() {

        //设置ViewPager
        mAdapter = new VideoAdapter(getChildFragmentManager());
        vpVideo.setAdapter(mAdapter);

        //设置TabLayout
        tlytVideo.setTabMode(TabLayout.MODE_SCROLLABLE);
        tlytVideo.setupWithViewPager(vpVideo);

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
        mListener = null;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
