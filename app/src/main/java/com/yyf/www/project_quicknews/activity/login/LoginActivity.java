package com.yyf.www.project_quicknews.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.activity.BaseActivity;
import com.yyf.www.project_quicknews.activity.register.RegisterActivity;
import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.bean.UserBean;
import com.yyf.www.project_quicknews.bean.event.LoginEvent;
import com.yyf.www.project_quicknews.global.GlobalValues;
import com.yyf.www.project_quicknews.net.IUserService;
import com.yyf.www.project_quicknews.utils.PatternUtil;
import com.yyf.www.project_quicknews.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BaseActivity {

    private Context mContext = this;

    private EditText etUserName;
    private EditText etPassword;
    private TextView tvFindPwd;
    private Button btnLogin;
    private TextView tvRegister;

    private IUserService mUserService;
    private Call<ResultBean<UserBean>> mCall;

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
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void getViews() {
        super.getViews();

        etUserName = (EditText) this.findViewById(R.id.etUserName);
        etPassword = (EditText) this.findViewById(R.id.etPassword);
        tvFindPwd = (TextView) this.findViewById(R.id.tvFindPwd);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        tvRegister = (TextView) this.findViewById(R.id.tvRegister);
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        //找回密码
        tvFindPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FindPwdStepOneActivity.class);
                startActivity(intent);
            }
        });

        //登录
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (localVerify()) {
                    login();
                }
            }
        });

        //注册新用户
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 登录时的local验证：数据格式是否正确等
     *
     * @return
     */
    private boolean localVerify() {

        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if (userName.equals("")) {
            Toast.makeText(getApplicationContext(), "请输入用户名/手机号", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!PatternUtil.isPwdCorrect(password)) {
            Toast.makeText(getApplicationContext(), "密码格式错误", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    /**
     * 登录
     */
    private void login() {

        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        mCall = mUserService.login("login", userName, password);
        mCall.enqueue(new Callback<ResultBean<UserBean>>() {
            @Override
            public void onResponse(Call<ResultBean<UserBean>> call, Response<ResultBean<UserBean>> response) {

                ResultBean<UserBean> result = response.body();

                if (result.code == ResultBean.CODE_ERROR) {
                    Toast.makeText(getApplicationContext(), result.msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (result.code == ResultBean.CODE_SINGLE_NOT_HAVE) {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误，登录失败", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (result.code == ResultBean.CODE_SINGLE_HAVE) {
                    UserBean user = response.body().data;
                    writeSPWhenLogin(user);
                    EventBus.getDefault().post(new LoginEvent(user));
                    finish();
                }

            }

            @Override
            public void onFailure(Call<ResultBean<UserBean>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "网络请求失败!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 登录信息写入login.sp
     *
     * @param user
     */
    private void writeSPWhenLogin(UserBean user) {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext, GlobalValues.SP_LOGIN);

        sharedPreferencesUtil.putBoolean("isLogined", true);
        sharedPreferencesUtil.putInt("id", user.getId());
        sharedPreferencesUtil.putString("userName", user.getUserName());
        sharedPreferencesUtil.putString("password", user.getPassword());
        sharedPreferencesUtil.putString("description", user.getDescription());
        sharedPreferencesUtil.putString("profilePhotoURL", user.getProfilePhotoURL());
        sharedPreferencesUtil.putString("telephone", user.getTelephone());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mCall != null) {
            mCall.cancel();
        }
    }
}
