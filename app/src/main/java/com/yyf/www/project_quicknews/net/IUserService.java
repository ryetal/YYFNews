package com.yyf.www.project_quicknews.net;

import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.bean.UserBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 子凡 on 2017/5/4.
 */

public interface IUserService {

    @FormUrlEncoded
    @POST("servlet/UserServlet")
    Call<ResultBean<UserBean>> login(@Field("action") String action,
                                     @Field("userName") String userName,
                                     @Field("password") String password);

}
