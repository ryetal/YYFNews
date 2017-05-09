package com.yyf.www.project_quicknews.bean;

/**
 * Created by 子凡 on 2017/5/3.
 */

public class ResultBean<T> {

    // 1.服务器错误时，返回{code:1,msg:MSG_ERROR,data:null}
    // 2.查询单个数据，存在时，返回{code:2,msg:MSG_SINGLE_HAVE,data:xxx}
    // 3.查询单个数据，没有时，返回{code:3,MSG_SINGLE_NOT_HAVE,data:null}
    // 4.查询数据集，空，返回{code:4,msg:MSG_DATASET_EMPTY,data:null}
    // 5.查询数据集，非空，返回{code:5,msg:MSG_DATASET_NOT_EMPTY,data:[xxx,xxx]}
    // 6.更新数据，更新数据已经存在,返回{code:6,msg:MSG_UPDATE_FAILED,data:null}
    // 7.更新数据，更新数据成功,返回{code:7,msg:MSG_UPDATE_SUCCESS,data:更新数据量}
    // 8. 插入数据，插入数据已经存在，返回{code:8,msg:MSG_INSERT_FAILED,data:null}
    // 9. 插入数据，插入数据成功，返回{code:9,msg:MSG_INSERT_SUCCESS,data:插入数据量}

    public static final int CODE_ERROR = 0X01; // 服务器端错误
    public static final int CODE_SINGLE_HAVE = 0X02; // 查询单条数据，有
    public static final int CODE_SINGLE_NOT_HAVE = 0X03; // 查询单条数据，没有
    public static final int CODE_DATASET_EMPTY = 0X04; // 查询数据集，空
    public static final int CODE_DATASET_NOT_EMPTY = 0X05; // 查询数据集，非空
    public static final int CODE_UPDATE_FAILED = 0X06; // 更新数据，更新数据已经存在
    public static final int CODE_UPDATE_SUCCESS = 0X07; // 更新数据成功
    public static final int CODE_INSERT_FAILED = 0X08; // 插入数据，插入数据已经存在
    public static final int CODE_INSERT_SUCCESS = 0X09; // 插入数据成功

    public int code;
    public String msg;
    public T data;

}
