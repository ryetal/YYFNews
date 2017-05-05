package com.yyf.www.project_quicknews.activity.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yyf.www.project_quicknews.R;


public class AgreementActivity extends AppCompatActivity {

    private Toolbar tbarAgreement;
    private TextView tvAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_agreement);

        getViews();
        initViews();
        setListeners();
    }

    /**
     * 获取View
     */
    private void getViews() {
        tbarAgreement = (Toolbar) this.findViewById(R.id.tbarAgreement);
        tvAgreement = (TextView) this.findViewById(R.id.tvAgreement);

    }

    /**
     * 初始化View
     */
    private void initViews() {
        tbarAgreement.setNavigationIcon(R.drawable.back);
        tbarAgreement.setTitle(getResources().getText(R.string.user_agreement));
        //tvAgreement.setMovementMethod(ScrollingMovementMethod.getInstance());
    }


    /**
     * 设置监听
     */
    private void setListeners() {
        //导航栏回退
        tbarAgreement.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
