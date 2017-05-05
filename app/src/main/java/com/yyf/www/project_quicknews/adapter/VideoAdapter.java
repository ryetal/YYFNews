package com.yyf.www.project_quicknews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yyf.www.project_quicknews.fragment.VideoAllFragment;

public class VideoAdapter extends FragmentPagerAdapter {

    private final Fragment[] FRAGMENTS = new Fragment[]{
            VideoAllFragment.newInstance(),
            VideoAllFragment.newInstance(),
            VideoAllFragment.newInstance(),
            VideoAllFragment.newInstance(),
            VideoAllFragment.newInstance(),
            VideoAllFragment.newInstance(),
            VideoAllFragment.newInstance()
    };

    private static final String[] VIDEO_INDICATORS = new String[]{
            "全部",
            "逗逼剧",
            "好声音",
            "看天下",
            "小品",
            "摄影",
            "最娱乐"
    };

    public VideoAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FRAGMENTS[position];

    }

    @Override
    public int getCount() {
        return FRAGMENTS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return VIDEO_INDICATORS[position];
    }
}
