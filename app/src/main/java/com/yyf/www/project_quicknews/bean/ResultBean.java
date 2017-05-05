package com.yyf.www.project_quicknews.bean;

/**
 * Created by 子凡 on 2017/5/3.
 */

public class ResultBean<T> {

    //1.服务器错误时，返回{code:1,msg:xxx,data:null}
    //2.单个数据，存在时，返回{code:2,msg:xxx,data:xxx}
    //3.单个数据，没有时，返回{code:3,msg:xxx,data:null}
    //4.数据集，空，返回{code:4,msg:xxx,data:null}
    //5.数据集，非空，返回{code:5,msg:xxx,data:[xxx,xxx]}

    public static final int CODE_ERROR = 0X01; // 服务器端错误
    public static final int CODE_SINGLE_HAVE = 0X02; // 单条数据，有
    public static final int CODE_SINGLE_NOT_HAVE = 0X03; // 单条数据，没有
    public static final int CODE_DATASET_EMPTY = 0X04; // 数据集，空
    public static final int CODE_DATASET_NOT_EMPTY = 0X05; // 数据集，非空

    public int code;
    public String msg;
    public T data;

}
