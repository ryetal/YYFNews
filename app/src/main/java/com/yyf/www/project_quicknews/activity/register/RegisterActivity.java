package com.yyf.www.project_quicknews.activity.register;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.utils.PatternUtil;


public class RegisterActivity extends AppCompatActivity {

    private Context mContext = this;

    private Toolbar tbarRegister;
    private EditText etPhoneNumber;
    private EditText etVerificationCode;
    private Button btnSendVerificationCode;
    private EditText etPassword;
    private EditText etPasswordAgain;
    private Button btnRegister;
    private TextView tvAgreement;


    //倒计时
    private CountDownTimer mCountDownTimer = new CountDownTimer(60000, 1000) {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        getViews();
        initViews();
        setListeners();
        initDatas();
    }

    /**
     * 获取View
     */
    private void getViews() {
        tbarRegister = (Toolbar) this.findViewById(R.id.tbarRegister);
        etPhoneNumber = (EditText) this.findViewById(R.id.etUserName);
        etVerificationCode = (EditText) this.findViewById(R.id.etVerificationCode);
        btnSendVerificationCode = (Button) this.findViewById(R.id.btnSendVerificationCode);
        etPassword = (EditText) this.findViewById(R.id.etPassword);
        etPasswordAgain = (EditText) this.findViewById(R.id.etPasswordAgain);
        btnRegister = (Button) this.findViewById(R.id.btnRegister);
        tvAgreement = (TextView) this.findViewById(R.id.tvAgreement);
    }

    /**
     * 初始化View
     */
    private void initViews() {
        tbarRegister.setNavigationIcon(R.drawable.back);
        tbarRegister.setTitle(getResources().getText(R.string.user_register));

        setAgreementText();
    }

    /**
     * 设置监听
     */
    private void setListeners() {

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
                    Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                    return;
                }


                //等待倒计时
                btnSendVerificationCode.setEnabled(false);
                mCountDownTimer.start();
            }
        });

        //注册
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (localVerify()) {
                    if (register()) {
                        Toast.makeText(mContext, "注册成功", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initDatas() {

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
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_LONG).show();
            return false;
        }



        if (!PatternUtil.isPwdCorrect(password)) {
            Toast.makeText(mContext, "密码格式错误", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!password.equals(passwordAgain)) {
            Toast.makeText(mContext, "两次输入的密码不一样", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    /**
     * 注册：连接服务器
     *
     * @return
     */
    private boolean register() {
        return true;
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(mContext, LoginActivity.class);
//        startActivity(intent);
//        finish();
    }
}