package com.yyf.www.project_quicknews.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 子凡 on 2017/4/9.
 */

public class NewsApplication extends Application {

    private RefWatcher refWatcher;

    private List<Activity> mActivities;

    public static RefWatcher getRefWatcher(Context context) {
        NewsApplication application = (NewsApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    public static void addActivity(Context context, Activity activity) {
        NewsApplication application = (NewsApplication) context.getApplicationContext();
        application.mActivities.add(activity);
    }

    public static void removeActivity(Context context, Activity activity) {
        NewsApplication application = (NewsApplication) context.getApplicationContext();
        application.mActivities.remove(activity);
    }

    public static void finishApp(Context context) {
        NewsApplication application = (NewsApplication) context.getApplicationContext();
        for (int i = 0; i < application.mActivities.size(); i++) {
            Activity activity = application.mActivities.get(i);
            removeActivity(context, activity);
            activity.finish();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = installLeakCanary();

        mActivities = new ArrayList<>();
    }

    protected RefWatcher installLeakCanary() {
//        return RefWatcher.DISABLED;  //for release
        return LeakCanary.install(this);  //for debug
    }

    private static Context context;

    public static Context getAppContext() {
        return context;
    }

}
