package com.yyf.www.project_quicknews.bean.event;

import com.yyf.www.project_quicknews.utils.NetUtils;

/**
 * Created by 子凡 on 2017/5/5.
 */

public class NetworkStatusEvent {

    public NetUtils.NetworkStatus status;

    public NetworkStatusEvent(NetUtils.NetworkStatus status) {
        this.status = status;
    }
}
