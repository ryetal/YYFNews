package com.yyf.www.project_quicknews.net;

import com.yyf.www.project_quicknews.bean.NewsBean;
import com.yyf.www.project_quicknews.bean.ResultBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 子凡 on 2017/5/4.
 */

public interface INewsService {

    @GET("servlet/NewsServlet?action=getNewsByKeyword")
    Call<ResultBean<List<NewsBean>>> getNewsByKeyword(@Query("keyword") String keyword);

}
