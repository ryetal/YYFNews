package com.yyf.www.project_quicknews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.global.GlobalValues;
import com.yyf.www.project_quicknews.okhttp.CallbackOnMainThread;
import com.yyf.www.project_quicknews.okhttp.Parser;
import com.yyf.www.project_quicknews.type.ParameterizedTypeImpl;
import com.yyf.www.project_quicknews.utils.NetUtils;
import com.yyf.www.project_quicknews.utils.ToastUtil;

import java.io.IOException;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public abstract class BaseNetworkFragment<T> extends BaseFragment {

    //static 内部类，防止内存泄漏
    private static class Callback<T> extends CallbackOnMainThread<T> {

        private WeakReference mWeakReference;

        public Callback(Parser<T> parser, BaseNetworkFragment fragment) {
            super(parser);
            mWeakReference = new WeakReference(fragment);
        }

        /**
         * Called when the HTTP response was successfully returned by the remote server
         *
         * @param result if response code is not in [200..300),result is null
         */
        @Override
        protected void onResponse(T result) {
            BaseNetworkFragment fragment = (BaseNetworkFragment) mWeakReference.get();
            if (fragment == null || !fragment.isAdded()) {
                return;
            }
            fragment.isRequesting = false;
            if (result == null) {
                Log.i(GlobalValues.TAG, fragment.mFragmentName + " - 【onResponse】 failure");
                fragment.doOnResponseFailure();
            } else {
                Log.i(GlobalValues.TAG, fragment.mFragmentName + " - 【onResponse】 success");
                fragment.doOnResponseSuccess(result);
            }
        }

        /**
         * Called when the request could not be executed due to cancellation, a connectivity problem or
         * timeout.
         *
         * @param e
         */
        @Override
        protected void onFailure(IOException e) {
            BaseNetworkFragment fragment = (BaseNetworkFragment) mWeakReference.get();
            if (fragment == null || !fragment.isAdded()) {
                return;
            }
            Log.i(GlobalValues.TAG, fragment.mFragmentName + " - 【onFailure】 request 失败");
            fragment.isRequesting = false;
            fragment.doOnFailure();
        }
    }

    //在一次网络请求之前的操作
    abstract void doBeforeOneRequest();

    //从服务器上获取数据成功后的操作：response code is in [200..300)
    abstract void doOnResponseSuccess(T result);

    //从服务器上获取数据失败后的操作：response code is not in [200..300)
    abstract void doOnResponseFailure();

    //请求失败的操作：request could not be executed due to cancellation, a connectivity problem or timeout
    abstract void doOnFailure();

    private OkHttpClient mClient;
    private boolean isRequesting; //是否正在执行网络请求

    public BaseNetworkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS).build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mClient.dispatcher().cancelAll(); //cancel 网络请求
    }

    /**
     * 发起一次请求
     * <p>1.之前的request还未执行，本次request不执行</p>
     * <p>2.本次request执行前，网络已经断开</p>
     * <p>3.本次request执行时，网络被断开/网络出现问题/请求被取消等</p>
     * <p>4.本次request执行成功</p>
     *
     * @param url
     * @return 是否发起网络请求
     */
    protected boolean doRequest(String url, Parser<T> parser) {

        //1.之前的request还未执行，本次request不执行
        if (isRequesting) {
            return false;
        }

        //2.本次request执行前，网络已经断开
        if (!NetUtils.isNetworkConnected(getContext())) {
            ToastUtil.showToast("网络断开", Toast.LENGTH_SHORT);
            return false;
        }

        isRequesting = true;

        //从服务器上获取数据之前的操作
        doBeforeOneRequest();

        //开始一次网络请求
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(new Callback<T>(parser, this));

        return true;
    }


    /**
     * 发起一次请求
     * <p>1.之前的request还未执行，本次request不执行</p>
     * <p>2.本次request执行前，网络已经断开</p>
     * <p>3.本次request执行时，网络被断开/网络出现问题/请求被取消等</p>
     * <p>4.本次request执行成功</p>
     *
     * @param url
     * @return 是否发起网络请求
     */

    protected boolean doRequest2(String url) {

        //1.之前的request还未执行，本次request不执行
        if (isRequesting) {
            return false;
        }

        //2.本次request执行前，网络已经断开
        if (!NetUtils.isNetworkConnected(getContext())) {
            return false;
        }

        isRequesting = true;

        //从服务器上获取数据之前的操作

        //开始一次网络请求
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(new okhttp3.Callback() {

            //3.本次request执行时，网络被断开/网络出现问题/请求被取消等
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(GlobalValues.TAG, mFragmentName + " - 【onFailure】 request 失败");
            }

            //4.本次request执行成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //response code is not in [200..300)
                if (!response.isSuccessful()) {
                    Log.i(GlobalValues.TAG, mFragmentName + " - 【onResponse】 获取数据失败");
                } else {
                    Log.i(GlobalValues.TAG, mFragmentName + " - 【onResponse】 获取数据成功");
                }
            }
        });

        return true;
    }

    private Gson mGson = new Gson();

    private <T> ResultBean<T> fromJsonObject(Reader reader, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(ResultBean.class, new Class[]{clazz});
        return mGson.fromJson(reader, type);
    }

    private <T> ResultBean<List<T>> fromJsonArray(Reader reader, Class<T> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        // 根据List<T>生成完整的Result<List<T>>
        Type type = new ParameterizedTypeImpl(ResultBean.class, new Type[]{listType});
        return mGson.fromJson(reader, type);
    }

}
