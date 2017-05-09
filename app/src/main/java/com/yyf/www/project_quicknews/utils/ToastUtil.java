package com.yyf.www.project_quicknews.utils;

import android.widget.Toast;

import com.yyf.www.project_quicknews.application.NewsApplication;

/**
 * Created by 子凡 on 2017/5/9.
 */

public class ToastUtil {

    private static Toast toast;

    public static void showToast(String text, int length) {
        if (toast == null) {
            toast = Toast.makeText(NewsApplication.getAppContext(), text, length);
        } else {
            toast.setDuration(length);
            toast.setText(text);
        }

        toast.show();
    }

}
