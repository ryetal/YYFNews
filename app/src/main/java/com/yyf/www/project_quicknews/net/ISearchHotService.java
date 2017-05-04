package com.yyf.www.project_quicknews.net;

import com.yyf.www.project_quicknews.bean.ResultBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 子凡 on 2017/5/4.
 */

public interface ISearchHotService {

    @GET("servlet/SearchHotServlet")
    Call<ResultBean<List<String>>> getSearchHot();

}
