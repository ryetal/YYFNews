package com.yyf.www.project_quicknews.activity.register;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.activity.BaseVerifyActivity;
import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.global.GlobalValues;
import com.yyf.www.project_quicknews.net.IUserService;
import com.yyf.www.project_quicknews.utils.PatternUtil;
import com.yyf.www.project_quicknews.utils.ToastUtil;

import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterActivity extends BaseVerifyActivity {

    private Context mContext = this;

    private Toolbar tbarRegister;
    private EditText etPhoneNumber;
    private EditText etVerificationCode;
    private Button btnSendVerificationCode;
    private EditText etPassword;
    private EditText etPasswordAgain;
    private Button btnRegister;
    private TextView tvAgreement;

    private IUserService mUserService;
    private Call<ResultBean<Object>> mCall;

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
        return R.layout.activity_register;
    }

    @Override
    protected void doWhenSuccessGetVerificationCode() {
        ToastUtil.showToast("获取验证码【成功】", Toast.LENGTH_LONG);
    }

    @Override
    protected void doWhenSuccessVerify() {
        register(); //验证码验证成功后，进行注册
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalValues.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mUserService = retrofit.create(IUserService.class);
    }

    @Override
    protected void getViews() {
        super.getViews();

        tbarRegister = (Toolbar) this.findViewById(R.id.tbarRegister);
        etPhoneNumber = (EditText) this.findViewById(R.id.etTelephone);
        etVerificationCode = (EditText) this.findViewById(R.id.etVerificationCode);
        btnSendVerificationCode = (Button) this.findViewById(R.id.btnSendVerificationCode);
        etPassword = (EditText) this.findViewById(R.id.etPassword);
        etPasswordAgain = (EditText) this.findViewById(R.id.etPasswordAgain);
        btnRegister = (Button) this.findViewById(R.id.btnRegister);
        tvAgreement = (TextView) this.findViewById(R.id.tvAgreement);
    }

    @Override
    protected void initViews() {
        super.initViews();

        setAgreementText();
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        //导航栏回退
        tbarRegister.setNavigationOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_LONG).show();
                    return;
                }

                //等待倒计时
                btnSendVerificationCode.setEnabled(false);
                mCountDownTimer.start();

                //获取验证码。会触发EventHandler中的回调方法。
                SMSSDK.getVerificationCode("86", phoneNumber);
            }
        });

        //注册
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (localVerify()) { //本地格式检验
                    //验证验证码
                    String phoneNumber = etPhoneNumber.getText().toString();
                    String code = etVerificationCode.getText().toString();
                    SMSSDK.submitVerificationCode("86", phoneNumber, code);
                }
            }
        });
    }

    /**
     * 设置“点击注册表示......《用户协议》”
     */
    private void setAgreementText() {
        Resources res = getResources();
        String string = res.getString(R.string.agreement);
        int firstIndex = string.indexOf(res.getString(R.string.user_agreement));
        int lastIndex = firstIndex + res.getString(R.string.user_agreement).length();

        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {

                Intent intent = new Intent(mContext, AgreementActivity.class);
                startActivity(intent);

            }
        }, firstIndex, lastIndex, 0);
        tvAgreement.setText(spannableString);
        tvAgreement.setMovementMethod(new LinkMovementMethod());

    }


    /**
     * 注册时的local验证：数据格式是否正确等
     *
     * @return
     */
    private boolean localVerify() {

        String phoneNumber = etPhoneNumber.getText().toString();
        String verificationCode = etVerificationCode.getText().toString();
        String password = etPassword.getText().toString();
        String passwordAgain = etPasswordAgain.getText().toString();

        if (!PatternUtil.isPhoneNumberCorrect(phoneNumber)) {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!PatternUtil.isPwdCorrect(password)) {
            Toast.makeText(getApplicationContext(), "密码格式错误", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!password.equals(passwordAgain)) {
            Toast.makeText(getApplicationContext(), "两次输入的密码不一样", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    /**
     * 注册
     */
    private boolean register() {

        String telephone = etPhoneNumber.getText().toString();
        String password = etPassword.getText().toString();

        mCall = mUserService.regiseter(telephone,password);
        mCall.enqueue(new Callback<ResultBean<Object>>() {
            @Override
            public void onResponse(Call<ResultBean<Object>> call, Response<ResultBean<Object>> response) {

                ResultBean<Object> result = response.body();

                if (result.code == ResultBean.CODE_SQL_OPERATOR_ERROR) {
                    ToastUtil.showToast(result.msg, Toast.LENGTH_SHORT);
                    return;
                }

                if (result.code == ResultBean.CODE_IS_EXSITED) {
                    Boolean isExisted = (Boolean) result.data;
                    if (isExisted) {
                        ToastUtil.showToast("该手机号码已经被注册了!", Toast.LENGTH_SHORT);
                    }
                    return;
                }

                if (result.code == ResultBean.CODE_INSERT_SUCCESS) {
                    Integer count = (Integer) result.data;
                    if (count > 0) {
                        ToastUtil.showToast("注册成功!", Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showToast("注册失败!", Toast.LENGTH_SHORT);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResultBean<Object>> call, Throwable t) {
                if (call.isCanceled()) {
                    return;
                }
                ToastUtil.showToast("网络请求失败", Toast.LENGTH_SHORT);
            }
        });

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCountDownTimer.cancel();
        mCountDownTimer = null;
    }
}
