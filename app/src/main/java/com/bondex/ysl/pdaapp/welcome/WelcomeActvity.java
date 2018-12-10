package com.bondex.ysl.pdaapp.welcome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.login.LoginActivity;
import com.bondex.ysl.pdaapp.main.MainActivity;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
import com.bondex.ysl.pdaapp.util.SharedPreferecneUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date: 2018/12/7
 * Author: ysl
 * description:
 */
public class WelcomeActvity extends BaseActivtiy<WelcomePresenter> implements WelcomeView {

    @BindView(R.id.welcome_tv)
    TextView welcomeTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        showLeft(false, 0, null);
        showTitle(false, null);
        ButterKnife.bind(this);

    }


    @Override
    public WelcomePresenter getPresenter() {

        if (presenter == null) {

            presenter = new WelcomePresenter(this, this);
        }
        return presenter;
    }

    @Override
    public void noDoubleClick(View v) {

    }

    @Override
    public void startAnimation() {

        ObjectAnimator animator = ObjectAnimator.ofFloat(welcomeTv, "ScaleX", 0, 1f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(welcomeTv, "ScaleY", 0, 1f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(welcomeTv, "rotation", 0, 360);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(welcomeTv, "alpha", 0, 1);

        AnimatorSet set = new AnimatorSet();

        set.play(animator).with(animator1).with(animator2).with(animator3);
        set.setDuration(2000);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);


            }
        });

        boolean handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                presenter.start();
            }
        }, 2000);

    }

    @Override
    public void setLoginData(String name, String passoword, boolean islogined) {

        String stowrage = SharedPreferecneUtils.getValue(this, Constant.STORWAGEPAGE, Constant.SUBSYSTEM_NAME);
        if (islogined && CommonUtil.isNotEmpty(stowrage)) jumpToMain();
        else jumpToLogin();
    }

    private void jumpToMain() {

        Intent intent = new Intent(WelcomeActvity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void jumpToLogin() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void initView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void faile(String msg) {
        jumpToLogin();
    }
}
