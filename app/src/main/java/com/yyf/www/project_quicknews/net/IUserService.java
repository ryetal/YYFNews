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
    @POST("servlet/UserServlet?action=login")
    Call<ResultBean<UserBean>> login(@Field("telephone") String telephone,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("servlet/UserServlet?action=resetPassword")
    Call<ResultBean<Integer>> resetPassword(@Field("telephone") String telephone,
                                            @Field("newPassword") String newPassword);

    @FormUrlEncoded
    @POST("servlet/UserServlet?action=register")
    Call<ResultBean<Object>> regiseter(@Field("telephone") String telephone,
                                       @Field("password") String password);

}
