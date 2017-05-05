package com.yyf.www.project_quicknews.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class FindPwdStepTwoActivity extends AppCompatActivity {

    private Context mContext = this;

    private Toolbar tbarFindPwdStepTwo;
    private EditText etPassword;
    private EditText etPasswordAgain;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_find_pwd_step_two);

        getViews();
        initViews();
        setListeners();
    }

    /**
     * 获取View
     */
    private void getViews() {
        tbarFindPwdStepTwo = (Toolbar) this.findViewById(R.id.tbarFindPwdStepTwo);
        etPassword = (EditText) this.findViewById(R.id.etPassword);
        etPasswordAgain = (EditText) this.findViewById(R.id.etPasswordAgain);
        btnConfirm = (Button) this.findViewById(R.id.btnConfirm);
    }

    /**
     * 初始化View
     */
    private void initViews() {
        tbarFindPwdStepTwo.setNavigationIcon(R.drawable.back);
        tbarFindPwdStepTwo.setTitle(getResources().getText(R.string.reset_password));
    }

    /**
     * 设置监听
     */
    private void setListeners() {

        //导航栏回退
        tbarFindPwdStepTwo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //确认
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (localVerify()) {
                    Toast.makeText(mContext, "密码修改成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
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

        String password = etPassword.getText().toString();
        String passwordAgain = etPasswordAgain.getText().toString();

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


}
