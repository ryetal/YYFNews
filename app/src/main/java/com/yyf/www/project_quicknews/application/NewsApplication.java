package com.yyf.www.project_quicknews.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.yyf.www.project_quicknews.receiver.NetworkStatusReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 子凡 on 2017/4/9.
 */

public class NewsApplication extends Application {

    private static NewsApplication instance;

    private RefWatcher mRefWatcher;
    private List<Activity> mActivities;
    private NetworkStatusReceiver mReceiver;

    public static RefWatcher getRefWatcher(Context context) {
        NewsApplication application = (NewsApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    public static void addActivity(Activity activity) {
        instance.mActivities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        instance.mActivities.remove(activity);

        //最后一个activity被移除的时候，表示app退出
        if (instance.mActivities.size() == 0) {
            instance.unregisterReceiver(instance.mReceiver);
            //ToastUtil.showToast("退出了app", Toast.LENGTH_SHORT);
        }
    }

    public static void finishApp() {

        for (int i = 0; i < instance.mActivities.size(); i++) {
            Activity activity = instance.mActivities.get(i);
            removeActivity(activity);
            activity.finish();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        mRefWatcher = installLeakCanary();
        mActivities = new ArrayList<>();
        //注册网络状态变化广播
        registerNetworkStatusReceiver();
    }

    /**
     * 注册网络状态变化广播
     */
    private void registerNetworkStatusReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new NetworkStatusReceiver();
        registerReceiver(mReceiver, intentFilter);
    }


    protected RefWatcher installLeakCanary() {
//        return RefWatcher.DISABLED;  //for release
        return LeakCanary.install(this);  //for debug
    }

    public static NewsApplication getInstance() {
        return instance;
    }

}
