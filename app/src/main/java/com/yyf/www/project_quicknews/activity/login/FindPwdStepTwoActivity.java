package com.yyf.www.project_quicknews.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.activity.BaseActivity;
import com.yyf.www.project_quicknews.bean.ResultBean;
import com.yyf.www.project_quicknews.global.GlobalValues;
import com.yyf.www.project_quicknews.net.IUserService;
import com.yyf.www.project_quicknews.utils.PatternUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindPwdStepTwoActivity extends BaseActivity {

    private Context mContext = this;
    private String mTelephone;

    private Toolbar tbarFindPwdStepTwo;
    private EditText etPassword;
    private EditText etPasswordAgain;
    private Button btnReset;

    private IUserService mUserService;
    private Call<ResultBean<Integer>> mCall;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_find_pwd_step_two;
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

        tbarFindPwdStepTwo = (Toolbar) this.findViewById(R.id.tbarFindPwdStepTwo);
        etPassword = (EditText) this.findViewById(R.id.etPassword);
        etPasswordAgain = (EditText) this.findViewById(R.id.etPasswordAgain);
        btnReset = (Button) this.findViewById(R.id.btnReset);
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        //导航栏回退
        tbarFindPwdStepTwo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //确认
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (localVerify()) {
                    doRequest();
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        super.initDatas();

        mTelephone = getIntent().getExtras().getString("telephone");
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
            Toast.makeText(getApplicationContext(), "密码格式错误", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!password.equals(passwordAgain)) {
            Toast.makeText(getApplicationContext(), "两次输入的密码不一样", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void doRequest() {

        String newPassword = etPassword.getText().toString();

        mCall = mUserService.resetPassword(mTelephone, newPassword);
        mCall.enqueue(new Callback<ResultBean<Integer>>() {
                          @Override
                          public void onResponse(Call<ResultBean<Integer>> call, Response<ResultBean<Integer>> response) {

                              ResultBean<Integer> result = response.body();

                              if (result.code == ResultBean.CODE_SQL_OPERATOR_ERROR) {
                                  Toast.makeText(getApplicationContext(), result.msg, Toast.LENGTH_SHORT).show();
                                  return;
                              }

                              if (result.code == ResultBean.CODE_UPDATE_SUCCESS) {

                                  Integer count = result.data;
                                  if (count > 0) {
                                      Toast.makeText(getApplicationContext(), "密码修改成功", Toast.LENGTH_SHORT).show();
                                      etPassword.postDelayed(new Runnable() {
                                          @Override
                                          public void run() {
                                              Intent intent = new Intent(mContext, LoginActivity.class);
                                              startActivity(intent);
                                          }
                                      }, 1000);
                                  } else {
                                      Toast.makeText(getApplicationContext(), "密码修改失败", Toast.LENGTH_SHORT).show();
                                  }
                              }

                          }

                          @Override
                          public void onFailure(Call<ResultBean<Integer>> call, Throwable t) {
                              if (call.isCanceled()) {  //主动取消的时候
                                  return;
                              }
                              Toast.makeText(getApplicationContext(), "网络请求失败!", Toast.LENGTH_SHORT).show();
                          }
                      }

        );
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mCall != null) {
            mCall.cancel();
        }
    }

}
