package com.yyf.www.project_quicknews.bean;

/**
 * Created by 子凡 on 2017/5/3.
 */

public class ResultBean<T> {

    // 1. 插入数据，返回{code:1,msg:XXX,data:插入数据量}
    // 2. 删除数据，返回{code:2,msg:XXX,data:删除数据量}
    // 3. 更新数据，返回{code:3,msg:XXX,data:更新数据量}
    // 4. 查询数据，返回{code:4,msg:XXX,data:null/bean/List<bean>}，null表示没有查询到相应的数据
    // 5. 服务器端数据库操作失败，返回{code:5,msg:XXX,data:null}
    // 6. 数据是否已经存在，返回{code:6,msg:XXX,data:true/false}

    public static final int CODE_INSERT_SUCCESS = 0X01; // 插入成功
    public static final int CODE_DELETE_SUCCESS = 0X02; // 删除成功
    public static final int CODE_UPDATE_SUCCESS = 0X03; // 更新成功
    public static final int CODE_QUERY_SUCCESS = 0X04; // 查询成功
    public static final int CODE_SQL_OPERATOR_ERROR = 0X05; // 服务器端数据库操作失败
    public static final int CODE_IS_EXSITED = 0X06; // 数据是否已经存在

    public int code;
    public String msg;
    public T data;

}
