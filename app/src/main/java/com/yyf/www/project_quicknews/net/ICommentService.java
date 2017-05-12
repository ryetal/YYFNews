package com.yyf.www.project_quicknews.net;

import com.yyf.www.project_quicknews.bean.CommentBean;
import com.yyf.www.project_quicknews.bean.ResultBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 子凡 on 2017/5/4.
 */

public interface ICommentService {

    @GET("servlet/CommentServlet?action=getComments")
    Call<ResultBean<List<CommentBean>>> getComments(@Query("newsId") int newsId,
                                                    @Query("offset") int offset,
                                                    @Query("size") int size);

}
