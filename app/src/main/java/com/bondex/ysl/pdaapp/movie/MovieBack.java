package com.bondex.ysl.pdaapp.movie;

import com.bondex.ysl.pdaapp.base.BaseBack;
import com.bondex.ysl.pdaapp.bean.ResultBean;


/**
 * date: 2018/11/5
 * Author: ysl
 * description:
 */
public interface MovieBack extends BaseBack {


    void traceIdExists(ResultBean bean);
    void onFailed(String msg);

    void removeStowrage(ResultBean bean);
}
