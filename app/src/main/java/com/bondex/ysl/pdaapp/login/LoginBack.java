package com.bondex.ysl.pdaapp.login;

import com.bondex.ysl.pdaapp.base.BaseBack;

public interface LoginBack extends BaseBack {

    void loginSuccess(String data);

    void loginFailed(String msg);

    void localData(String name,String password,boolean isLogined);

}
