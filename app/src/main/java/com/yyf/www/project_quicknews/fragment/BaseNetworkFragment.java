package com.yyf.www.project_quicknews.fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.type.ParameterizedTypeImpl;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseNetworkFragment<T> extends BaseFragment {

    protected int index; //测试用

    //在一次网络请求之前的操作
    abstract void doBeforeOneRequest();

    //从服务器上获取数据成功后的操作
    abstract void doWhenGetDatasFromServerSuccess();

    //从服务器上获取数据失败后的操作：response code is in [200..300)
    abstract void doWhenGetDatasFromServerFailed();

    //请求失败后的操作：request could not be executed due to cancellation, a connectivity problem or timeout
    abstract void doWhenRequestFailed();

    //Handler Message
    private static final int MSG_GET_DATA_SUCCESS = 1;
    private static final int MSG_GET_DATA_FAILED = 2;
    private static final int MSG_REQUEST_FAILED = 3;
    private static final int MSG_BEFORE_REQUEST = 4;

    //OkHttp and Gson
    private final OkHttpClient mClient = new OkHttpClient();
    private final Gson mGson = new Gson();

    protected Class<T> mClazz; //泛型具体类型
    protected boolean mIsDataset = true; //请求返回的是否是数据集，还是单个数据

    protected ResultBean mResult; //请求返回的结果

    private boolean isRequesting; //是否正在执行网络请求

    //网络处理 handler
    private Handler mNetworkHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            isRequesting = false;

            switch (msg.what) {
                case MSG_GET_DATA_SUCCESS: //获取数据成功
                    doWhenGetDatasFromServerSuccess();
                    break;
                case MSG_GET_DATA_FAILED: //获取数据失败
                    doWhenGetDatasFromServerFailed();
                    break;
                case MSG_REQUEST_FAILED: //request 失败
                    doWhenRequestFailed();
                    break;
                case MSG_BEFORE_REQUEST: //reqeust 之前
                    doBeforeOneRequest();
                    break;
            }
        }
    };

    public BaseNetworkFragment() {
        // Required empty public constructor
    }

    //生命周期：begin///////////////////////////////////////////////////////////////////////////////

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //cancel 网络请求
        mClient.dispatcher().cancelAll();
    }

    //生命周期：end/////////////////////////////////////////////////////////////////////////////////

    /**
     * 从服务器上获取数据
     */
    protected void doRequest(String url) {

        if (isRequesting) {
            return;
        }

        isRequesting = true;

        //从服务器上获取数据之前的操作
        doBeforeOneRequest();

        //开始一次网络请求
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                //request could not be executed due to cancellation, a connectivity problem or timeout.
                Log.i("BaseNetworkFragment", index + "【request 失败】" + e.getMessage());
                mNetworkHandler.sendEmptyMessage(MSG_REQUEST_FAILED);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //response code is not in [200..300)
                if (!response.isSuccessful()) {
                    Log.i("BaseNetworkFragment", index + "【获取数据失败】");
                    mNetworkHandler.sendEmptyMessage(MSG_GET_DATA_FAILED);
                    return;
                }

                try {
                    if (mIsDataset) {
                        mResult = fromJsonArray(response.body().charStream(), mClazz);
                    } else {
                        mResult = fromJsonObject(response.body().charStream(), mClazz);
                    }
                    if (mResult == null) {
                        mNetworkHandler.sendEmptyMessage(MSG_GET_DATA_FAILED);
                        return;
                    }
                    Log.i("BaseNetworkFragment", index + "【获取数据成功】");
                    mNetworkHandler.sendEmptyMessage(MSG_GET_DATA_SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

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
