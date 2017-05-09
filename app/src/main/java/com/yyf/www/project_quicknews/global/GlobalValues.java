package com.yyf.www.project_quicknews.global;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.bean.TabBean;
import com.yyf.www.project_quicknews.fragment.CareFragment;
import com.yyf.www.project_quicknews.fragment.HomeFragment;
import com.yyf.www.project_quicknews.fragment.MineFragment;
import com.yyf.www.project_quicknews.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 子凡 on 2017/4/5.
 */

public class GlobalValues {

    private static final String[] TABHOST_TAB_TITLES = new String[]{"首页", "视频", "关注", "我的"};
    private static final String[] TABHOST_TAB_TAGS = new String[]{"home", "video", "care", "mine"};
    private static final int[] TABHOST_TAB_DRAWABLES = new int[]{
            R.drawable.selector_home,
            R.drawable.selector_video,
            R.drawable.selector_care,
            R.drawable.selector_mine
    };
    private static final Class[] TABHOST_FRAGMENT = new Class[]{
            HomeFragment.class,
            VideoFragment.class,
            CareFragment.class,
            MineFragment.class
    };

    public static List<TabBean> generateTabs() {

        List<TabBean> tabs = new ArrayList<>();
        for (int i = 0; i < TABHOST_TAB_TITLES.length; i++) {
            TabBean tab = new TabBean();
            tab.setTitle(TABHOST_TAB_TITLES[i]);
            tab.setDrawableId(TABHOST_TAB_DRAWABLES[i]);
            tab.setClazz(TABHOST_FRAGMENT[i]);
            tab.setTag(TABHOST_TAB_TAGS[i]);
            tabs.add(tab);
        }

        return tabs;
    }

    ////////////////////////////////////////////////////////////////////////////

    public static final String BASE_URL = "http://192.168.0.104:8080/YYFNewsServer/";

    public static final String SP_LOGIN = "login";

    public static final String TAG = "YYFNews";

}
