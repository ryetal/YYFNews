package com.yyf.www.project_quicknews.bean;

/**
 * Created by 子凡 on 2017/4/5.
 */

public class TabBean {

    private String mTitle;
    private int mDrawableId;
    private Class mClazz;
    private String mTag;

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    public Class getClazz() {
        return mClazz;
    }

    public void setClazz(Class clazz) {
        this.mClazz = clazz;
    }

    public int getDrawableId() {
        return mDrawableId;
    }

    public void setDrawableId(int drawableId) {
        this.mDrawableId = drawableId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }
}
