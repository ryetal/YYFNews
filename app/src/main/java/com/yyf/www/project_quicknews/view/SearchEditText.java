package com.yyf.www.project_quicknews.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.yyf.www.project_quicknews.R;

public class SearchEditText extends EditText implements TextWatcher {

    private static final int ICON_SEARCH_ID = R.drawable.search;
    private static final int ICON_CLEAR_ID = android.R.drawable.ic_delete;

    private Context mContext;

    public SearchEditText(Context context) {
        this(context, null);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        init();
    }

    private void init() {

        //设置背景
        float[] outerR = new float[]{10, 10, 10, 10, 10, 10, 10, 10};// 外部矩形弧度
        RoundRectShape roundRectShape = new RoundRectShape(outerR, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(Color.WHITE);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        setBackgroundDrawable(shapeDrawable);


        setCompoundDrawablesWithIntrinsicBounds(ICON_SEARCH_ID, 0, 0, 0);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

                float x = event.getX();
                float y = event.getY();

                //是否点击了清除图标
                if (isClearIconClicked(x, y)) {
                    setCompoundDrawablesWithIntrinsicBounds(ICON_SEARCH_ID, 0, 0, 0);
                    setText("");

                    if (mOnSearchEditTextListener != null) {
                        mOnSearchEditTextListener.onClear();
                    }

                    return true;

                }

                //是否点击了搜索图标
                if (isSearchIconClicked(x, y)) {
                    if (mOnSearchEditTextListener != null && !getText().toString().equals("")) {
                        mOnSearchEditTextListener.onSearch(getText().toString());
                    }

                    return true;
                }

                break;
        }


        return super.onTouchEvent(event);
    }

    /**
     * 是否点击了清除图标
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isClearIconClicked(float x, float y) {

        Drawable[] drawables = this.getCompoundDrawables();
        Drawable clearDrawable = drawables[2];
        if (clearDrawable == null) {
            return false;
        }

        int left = getWidth() - getCompoundPaddingRight();
        int top = getPaddingTop();
        int right = getWidth() - getPaddingRight();
        int bottom = getHeight() - getPaddingBottom();

        RectF rect = new RectF(left, top, right, bottom);
        return rect.contains(x, y);
    }

    /**
     * 是否点击了搜索图标
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isSearchIconClicked(float x, float y) {

        Drawable[] drawables = this.getCompoundDrawables();
        Drawable searchDrawable = drawables[0];
        if (searchDrawable == null) {
            return false;
        }

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getCompoundPaddingLeft();
        int bottom = getHeight() - getPaddingBottom();

        RectF rect = new RectF(left, top, right, bottom);
        return rect.contains(x, y);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) {
            setCompoundDrawablesWithIntrinsicBounds(ICON_SEARCH_ID, 0, 0, 0);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(ICON_SEARCH_ID, 0, ICON_CLEAR_ID, 0);
        }
    }

    //回调接口//////////////////////////////////////////////////////////////////////

    private OnSearchEditTextListener mOnSearchEditTextListener;

    public void setOnSearchEditTextListener(OnSearchEditTextListener onSearchEditTextListener) {
        mOnSearchEditTextListener = onSearchEditTextListener;
    }

    public static interface OnSearchEditTextListener {

        void onClear();

        void onSearch(String content);
    }

}
