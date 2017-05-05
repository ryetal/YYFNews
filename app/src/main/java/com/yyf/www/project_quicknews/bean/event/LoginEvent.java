package com.yyf.www.project_quicknews.bean.event;

import com.yyf.www.project_quicknews.bean.UserBean;

/**
 * Created by 子凡 on 2017/5/4.
 */

public class LoginEvent {

    public UserBean user;

    public LoginEvent(UserBean user){
        this.user = user;
    }

}
