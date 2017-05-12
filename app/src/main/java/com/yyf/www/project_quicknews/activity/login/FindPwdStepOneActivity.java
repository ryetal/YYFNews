package com.yyf.www.project_quicknews.activity.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.activity.BaseVerifyActivity;
import com.yyf.www.project_quicknews.utils.PatternUtil;
import com.yyf.www.project_quicknews.utils.ToastUtil;

import cn.smssdk.SMSSDK;

public class FindPwdStepOneActivity extends BaseVerifyActivity {

    private Toolbar tbarFindPwdStepOne;
    private EditText etPhoneNumber;
    private EditText etVerificationCode;
    private Button btnSendVerificationCode;
    private Button btnNextStep;

    //倒计时
    private CountDownTimer mCountDownTimer = new CountDownTimer(30000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            btnSendVerificationCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            btnSendVerificationCode.setEnabled(true);
            btnSendVerificationCode.setText(getResources().getString(R.string.send_verification_code));
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_find_pwd_step_one;
    }

    @Override
    protected void getViews() {
        super.getViews();

        etPhoneNumber = (EditText) this.findViewById(R.id.etTelephone);
        etVerificationCode = (EditText) this.findViewById(R.id.etVerificationCode);
        tbarFindPwdStepOne = (Toolbar) this.findViewById(R.id.tbarFindPwdStepOne);
        btnSendVerificationCode = (Button) this.findViewById(R.id.btnSendVerificationCode);
        btnNextStep = (Button) this.findViewById(R.id.btnNextStep);
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        //导航栏回退
        tbarFindPwdStepOne.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //发送验证码
        btnSendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //验证手机号码是否合法
                String phoneNumber = etPhoneNumber.getText().toString();
                if (!PatternUtil.isPhoneNumberCorrect(phoneNumber)) {
                    ToastUtil.showToast("请输入正确的手机号", Toast.LENGTH_LONG);
                    return;
                }

                //等待倒计时
                btnSendVerificationCode.setEnabled(false);
                mCountDownTimer.start();

                //获取验证码。会触发EventHandler中的回调方法。
                SMSSDK.getVerificationCode("86", phoneNumber);
            }
        });

        //下一步
        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = etPhoneNumber.getText().toString();
                String code = etVerificationCode.getText().toString();
                SMSSDK.submitVerificationCode("86", phoneNumber, code);
            }
        });
    }

    @Override
    protected void doWhenSuccessGetVerificationCode() {
        ToastUtil.showToast("获取验证码【成功】", Toast.LENGTH_LONG);
    }

    @Override
    protected void doWhenSuccessVerify() {
        ToastUtil.showToast("验证【成功】", Toast.LENGTH_LONG);
        mCountDownTimer.cancel();
        Intent intent = new Intent(FindPwdStepOneActivity.this, FindPwdStepTwoActivity.class);
        intent.putExtra("telephone", etPhoneNumber.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void doWhenFailedGetVerificationCode() {
        ToastUtil.showToast("获取验证码【失败】", Toast.LENGTH_LONG);
    }

    @Override
    protected void doWhenFailedVerify() {
        ToastUtil.showToast("验证【失败】", Toast.LENGTH_LONG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCountDownTimer.cancel();
        mCountDownTimer = null;
    }
}
