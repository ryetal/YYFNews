package com.yyf.www.project_quicknews.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.utils.PatternUtil;

public class FindPwdStepOneActivity extends AppCompatActivity {

    private Context mContext = this;

    private Toolbar tbarFindPwdStepOne;
    private EditText etPhoneNumber;
    private EditText etVerificationCode;
    private Button btnSendVerificationCode;
    private Button btnNextStep;


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
        setContentView(R.layout.activity_find_pwd_step_one);

        getViews();
        initViews();
        setListeners();

    }


    /**
     * 获取View
     */
    private void getViews() {
        etPhoneNumber = (EditText) this.findViewById(R.id.etUserName);
        etVerificationCode = (EditText) this.findViewById(R.id.etVerificationCode);
        tbarFindPwdStepOne = (Toolbar) this.findViewById(R.id.tbarFindPwdStepOne);
        btnSendVerificationCode = (Button) this.findViewById(R.id.btnSendVerificationCode);
        btnNextStep = (Button) this.findViewById(R.id.btnNextStep);
    }

    /**
     * 初始化View
     */
    private void initViews() {
        tbarFindPwdStepOne.setNavigationIcon(R.drawable.back);
        tbarFindPwdStepOne.setTitle(getResources().getText(R.string.find_password));
    }

    /**
     * 设置监听
     */
    private void setListeners() {

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
                    Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                    return;
                }



                //等待倒计时
                btnSendVerificationCode.setEnabled(false);
                mCountDownTimer.start();
            }
        });

        //下一步
        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (localVerify()) {
                    mCountDownTimer.cancel();
                    Intent intent = new Intent(FindPwdStepOneActivity.this, FindPwdStepTwoActivity.class);
                    startActivity(intent);
                }
            }
        });
    }



    /**
     * local验证：数据格式是否正确等
     *
     * @return
     */
    private boolean localVerify() {

        String phoneNumber = etPhoneNumber.getText().toString();
        String verificationCode = etVerificationCode.getText().toString();

        if (!PatternUtil.isPhoneNumberCorrect(phoneNumber)) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }


}
