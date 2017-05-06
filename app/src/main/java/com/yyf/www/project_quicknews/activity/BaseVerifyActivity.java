package com.yyf.www.project_quicknews.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public abstract class BaseVerifyActivity extends BaseActivity {

    private static final int MSG_SUCCESS_GET_VERIFICATION_CODE = 1;
    private static final int MSG_SUCCESS_VERIFY = 2;
    private static final int MSG_FAILED_GET_VERIFICATION_CODE = 3;
    private static final int MSG_FAILED_VERIFY = 4;

    protected abstract void doWhenSuccessGetVerificationCode();

    protected abstract void doWhenSuccessVerify();

    protected abstract void doWhenFailedGetVerificationCode();

    protected abstract void doWhenFailedVerify();

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_SUCCESS_GET_VERIFICATION_CODE:
                    doWhenSuccessGetVerificationCode();
                    break;
                case MSG_SUCCESS_VERIFY:
                    doWhenSuccessVerify();
                    break;
                case MSG_FAILED_GET_VERIFICATION_CODE:
                    doWhenFailedGetVerificationCode();
                    break;
                case MSG_FAILED_VERIFY:
                    doWhenFailedVerify();
                    break;
            }
        }
    };

    //SMS的回调
    private EventHandler mEventHandler = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {  //操作成功

                switch (event) {
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE: //获取验证码
                        Log.i("verify", "获取验证码【成功】：" + data);
                        mHandler.sendEmptyMessage(MSG_SUCCESS_GET_VERIFICATION_CODE);
                        break;
                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE: //验证
                        Log.i("verify", "验证【成功】");
                        mHandler.sendEmptyMessage(MSG_SUCCESS_VERIFY);
                        break;
                    case SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES: //获取支持的国家
                        break;
                }

            } else { //操作失败

                switch (event) {
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE: //获取验证码
                        Log.i("verify", "获取验证码【失败】：" + data);
                        mHandler.sendEmptyMessage(MSG_FAILED_GET_VERIFICATION_CODE);
                        break;
                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE: //验证
                        Log.i("verify", "验证【失败】");
                        mHandler.sendEmptyMessage(MSG_FAILED_VERIFY);
                        break;
                    case SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES: //获取支持的国家
                        break;
                }

                Log.i("verify", "操作失败：" + ((Throwable) data).getMessage());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SMSSDK.initSDK(this, "1c33a256c4ea3", "cf1ad42be5cb5fe2f67ea40081664633"); //需去官网注册
        SMSSDK.registerEventHandler(mEventHandler); //注册短信回调
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEventHandler); //反注册短信回调
    }
}
