package com.bondex.ysl.pdaapp.exwarehouse;

import com.bondex.ysl.pdaapp.base.BaseBack;
import com.bondex.ysl.pdaapp.bean.ResultBean;

/**
 * date: 2018/11/6
 * Author: ysl
 * description:
 */
public interface ConsigeMentBack extends BaseBack {

    void faile(String msg);
    void success(ResultBean bean);
}
