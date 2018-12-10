package com.bondex.ysl.pdaapp.welcome;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BasePresnter;

/**
 * date: 2018/12/7
 * Author: ysl
 * description:
 */
public class WelcomePresenter extends BasePresnter<WelcomeView,WelcomModal> implements WelcomeCallBack{

    public WelcomePresenter(WelcomeView view, Context context) {
        super(view, context);

        view.startAnimation();
    }

    @Override
    public WelcomModal getModal() {

        modal = new WelcomModal(context);
        modal.setCallback(this);


        return modal;
    }

    public void start(){

        modal.doLocal();
    }


    @Override
    public void initData() {

    }

    @Override
    public void localData(String name, String password, boolean isLogined) {
        view.setLoginData(name,password,isLogined);
    }

    @Override
    public void failed() {
        view.faile("");
    }
}
