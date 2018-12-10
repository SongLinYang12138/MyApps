package com.bondex.ysl.pdaapp.welcome;

import com.bondex.ysl.pdaapp.base.BaseView;

/**
 * date: 2018/12/7
 * Author: ysl
 * description:
 */
public interface WelcomeView   extends BaseView  {

    void startAnimation();
    void setLoginData(String name, String passoword,boolean islogined);

}
