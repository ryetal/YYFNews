package com.yyf.www.project_quicknews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yyf.www.project_quicknews.R;
import com.yyf.www.project_quicknews.activity.login.LoginActivity;
import com.yyf.www.project_quicknews.bean.UserBean;
import com.yyf.www.project_quicknews.bean.event.LoginEvent;
import com.yyf.www.project_quicknews.global.GlobalValues;
import com.yyf.www.project_quicknews.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MineFragment extends BaseFragment {

    private UserBean mUser;

    private Toolbar tbarMine;
    private ImageView ivProfilePhoto;
    private TextView tvUserName;
    private LinearLayout llytCare;
    private LinearLayout llytSet;

    public MineFragment() {
        // Required empty public constructor
        mFragmentName = "mine";
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        mUser = readLoginSP();
    }

    @Override
    protected View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    protected void getViews() {
        super.getViews();

        tbarMine = (Toolbar) mRootView.findViewById(R.id.tbarMine);
        ivProfilePhoto = (ImageView) mRootView.findViewById(R.id.ivProfilePhoto);
        tvUserName = (TextView) mRootView.findViewById(R.id.tvUserName);
        llytCare = (LinearLayout) mRootView.findViewById(R.id.llytCare);
        llytSet = (LinearLayout) mRootView.findViewById(R.id.llytSet);
    }

    @Override
    protected void initViews() {
        super.initViews();

        changeViews(mUser == null ? false : true);
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser == null) {
                    //登录
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent loginEvent) {

        mUser = loginEvent.user;
        changeViews(mUser == null ? false : true);
    }

    /**
     * 根据是否登录，变换View
     *
     * @param isLogined 是否登录
     */
    private void changeViews(boolean isLogined) {

        String path = null;
        if (!isLogined) {
            Picasso.with(getContext()).load(path).placeholder(R.mipmap.ic_launcher).into(ivProfilePhoto);
            tvUserName.setText(getString(R.string.click_to_login));
            tbarMine.setNavigationIcon(null);
        } else {
            path = mUser.getProfilePhotoURL();
            Picasso.with(getContext()).load(path).placeholder(R.mipmap.ic_launcher).into(ivProfilePhoto);
            tvUserName.setText(mUser.getUserName());
            tbarMine.setNavigationIcon(R.drawable.logout);
            tbarMine.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUser = null;
                    writeSPWhenLogout();
                    changeViews(false);
                }
            });
        }
    }

    /**
     * 从login.sp中读取登录信息
     *
     * @return
     */
    private UserBean readLoginSP() {

        UserBean user = null;
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getContext(), GlobalValues.SP_LOGIN);

        boolean isLogin = sharedPreferencesUtil.getBoolean("isLogined", false);
        if (isLogin) {
            user = new UserBean();
            user.setId(sharedPreferencesUtil.getInt("id", -1));
            user.setUserName(sharedPreferencesUtil.getString("userName", ""));
            user.setPassword(sharedPreferencesUtil.getString("password", ""));
            user.setDescription(sharedPreferencesUtil.getString("description", ""));
            user.setProfilePhotoURL(sharedPreferencesUtil.getString("profilePhotoURL", ""));
            user.setTelephone(sharedPreferencesUtil.getString("telephone", ""));
        }

        return user;
    }

    /**
     * 登出后清空login.sp
     */
    private void writeSPWhenLogout() {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getContext(), GlobalValues.SP_LOGIN);
        sharedPreferencesUtil.clearAll();
        sharedPreferencesUtil.putBoolean("isLogined", false);
    }

}
