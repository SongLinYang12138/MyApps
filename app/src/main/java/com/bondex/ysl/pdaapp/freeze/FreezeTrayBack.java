package com.bondex.ysl.pdaapp.freeze;

import com.bondex.ysl.pdaapp.base.BaseBack;
import com.bondex.ysl.pdaapp.bean.FreezeSearchBean;

/**
 * date: 2019/4/1
 * Author: ysl
 * description:
 */
public interface FreezeTrayBack extends BaseBack {

    void searchSuccess(FreezeSearchBean bean);
    void searchFaile(String str);

    void freezeResult(boolean isSuccess,String msg);
    void unFreezeResult(boolean isSuccess,String msg);
}
