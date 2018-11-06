package com.bondex.ysl.pdaapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.bondex.ysl.pdaapp.R;
import com.bondex.ysl.pdaapp.base.BaseActivtiy;
import com.bondex.ysl.pdaapp.main.MainActivity;
import com.bondex.ysl.pdaapp.stowrage.StowrageActivity;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivtiy<LoginPernster> implements LoginView {


    @BindView(R.id.login_et_phone)
    EditText loginEtPhone;
    @BindView(R.id.login_et_password)
    EditText loginEtPassword;
    @BindView(R.id.login_bt_confirm)
    ButtonRectangle loginBtConfirm;
    @BindView(R.id.av_loading)
    AVLoadingIndicatorView avLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        showLeft(false, 0, null);
        showRight(false, 0, null);
        showTitle(true, "仓库系统");
    }

    @Override
    public LoginPernster getPresenter() {

        if (presenter == null) {
            presenter = new LoginPernster(this, this);
        }
        return presenter;
    }

    @Override
    public void noDoubleClick(View v) {


        switch (v.getId()) {

            case R.id.login_bt_confirm:

                String name = loginEtPhone.getText().toString();
                String password = loginEtPassword.getText().toString();

                if (CommonUtil.isEmpty(name)) {
                    showShort("请输入用户名");
                    return;
                }

                if (CommonUtil.isEmpty(password)) {
                    showShort("请输入密码");
                    return;
                }

                name = name.trim();
                password = password.trim();

                presenter.login(name, password);

                break;


        }

    }


    @Override
    public void initView() {

        loginBtConfirm.setOnClickListener(clickListener);

    }

    @Override
    public void showLoading() {

        avLoading.setVisibility(View.VISIBLE);

    }

    @Override
    public void stopLoading() {

        avLoading.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(String data) {

        showShort(data);
        jumpStowrage();

    }

    @Override
    public void faile(String msg) {

        showLong(msg);
    }

    @Override
    public void setLoginData(String name, String passoword, boolean islogined) {

        loginEtPhone.setText(name);
        loginEtPassword.setText(passoword);

        if (islogined) jumpToMain();
    }

    private void jumpToMain() {

        boolean isLoginOut = getIntent().getBooleanExtra(Constant.LOGIN_OUT, false);
        if (isLoginOut) return;

        Intent intent = new Intent(this, MainActivity.class);
        startBaseActivity(intent);
        finish();
    }

    private void jumpStowrage() {

        Intent intent = new Intent(this, StowrageActivity.class);
        startBaseActivity(intent);
        finish();
    }
}
