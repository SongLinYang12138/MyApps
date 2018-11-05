package com.bondex.ysl.pdaapp.login;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.base.BaseView;


public class LoginPernster extends BasePresnter<LoginView, LoginModal> implements LoginBack {

    public LoginPernster(LoginView view, Context context) {
        super(view,context);


      modal.doLocal();

    }

    @Override
    public LoginModal getModal() {

        if (modal == null) {
            modal = new LoginModal(context);
            modal.setCallback(this);
        }

        return modal;
    }

    @Override
    public void initData() {

    }


    public void login(final String name, final String passord) {

        view.showLoading();

        modal.doNet(name, passord);

    }

    @Override
    public void loginSuccess(String data) {
        view.stopLoading();
        view.onSuccess(data);
    }

    @Override
    public void loginFailed(String msg) {
        view.stopLoading();
        view.faile(msg);
    }

    @Override
    public void localData(String name, String password,boolean isLogined) {

        view.setLoginData(name,password,isLogined);
    }
}
