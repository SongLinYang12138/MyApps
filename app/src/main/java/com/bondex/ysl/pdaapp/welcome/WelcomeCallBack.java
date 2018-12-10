package com.bondex.ysl.pdaapp.welcome;

import com.bondex.ysl.pdaapp.base.BaseBack;

/**
 * date: 2018/12/7
 * Author: ysl
 * description:
 */
public interface WelcomeCallBack extends BaseBack {

    void localData(String name,String password,boolean isLogined);

    void failed();
}
