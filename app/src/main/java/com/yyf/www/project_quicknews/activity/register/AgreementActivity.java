package com.yyf.www.project_quicknews.activity.register;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.activity.BaseActivity;

public class AgreementActivity extends BaseActivity {

    private Toolbar tbarAgreement;
    private TextView tvAgreement;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register_agreement;
    }

    @Override
    protected void getViews() {
        super.getViews();

        tbarAgreement = (Toolbar) this.findViewById(R.id.tbarAgreement);
        tvAgreement = (TextView) this.findViewById(R.id.tvAgreement);
    }

    @Override
    protected void setListeners() {
        //导航栏回退
        tbarAgreement.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
