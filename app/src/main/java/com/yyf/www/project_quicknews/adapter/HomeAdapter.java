package com.yyf.www.project_quicknews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yyf.www.project_quicknews.fragment.HomeChildFragment;

public class HomeAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    private static final String[] HOME_INDICATORS = new String[]{
            "推荐",
            "热点",
            "视频",
            "社会",
            "段子",
            "图片",
            "娱乐",
            "科技"
    };

    public HomeAdapter(FragmentManager fm) {
        super(fm);

        fragments = new Fragment[]{
                HomeChildFragment.newInstance(HomeChildFragment.ChildType.Recommend),
                HomeChildFragment.newInstance(HomeChildFragment.ChildType.Hot),
                HomeChildFragment.newInstance(HomeChildFragment.ChildType.Video),
                HomeChildFragment.newInstance(HomeChildFragment.ChildType.Society),
                HomeChildFragment.newInstance(HomeChildFragment.ChildType.Episode),
                HomeChildFragment.newInstance(HomeChildFragment.ChildType.Picture),
                HomeChildFragment.newInstance(HomeChildFragment.ChildType.Entertainment),
                HomeChildFragment.newInstance(HomeChildFragment.ChildType.Technology),
        };
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];

    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return HOME_INDICATORS[position];
    }
}
