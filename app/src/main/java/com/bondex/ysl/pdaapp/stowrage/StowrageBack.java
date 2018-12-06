package com.bondex.ysl.pdaapp.stowrage;

import com.bondex.ysl.pdaapp.base.BaseBack;
import com.bondex.ysl.pdaapp.bean.loginebean.LoginBean;

public interface StowrageBack extends BaseBack {

    void getLoginBean(LoginBean loginBean);
}
