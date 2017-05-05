package com.yyf.www.project_quicknews.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.yyf.www.project_quicknews.bean.event.NetworkStatusEvent;
import com.yyf.www.project_quicknews.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;

public class NetWorkStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            String msg = "";

            switch (NetUtils.getAPNType(context)) {
                case NONE:  //没有网络
                    EventBus.getDefault().post(new NetworkStatusEvent(NetUtils.NetworkStatus.NONE));
                    msg = "网络断开";
                    break;
                case WIFI:  //wifi
                    EventBus.getDefault().post(new NetworkStatusEvent(NetUtils.NetworkStatus.WIFI));
                    msg = "已经切换到wifi";
                    break;
                case MOBILE:  //mobile
                    EventBus.getDefault().post(new NetworkStatusEvent(NetUtils.NetworkStatus.MOBILE));
                    msg = "已经切换到手机网络，请注意流量";
                    break;
            }

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        }
    }
}
