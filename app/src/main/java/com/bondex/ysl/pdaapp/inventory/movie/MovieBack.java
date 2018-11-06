package com.bondex.ysl.pdaapp.inventory.movie;

import com.bondex.ysl.pdaapp.base.BaseBack;
import com.bondex.ysl.pdaapp.bean.InventoryMovieOneTraceIdBean;


/**
 * date: 2018/11/5
 * Author: ysl
 * description:
 */
public interface MovieBack extends BaseBack {


    void traceIdExists(InventoryMovieOneTraceIdBean bean);
    void onFailed(String msg);

    void removeStowrage(InventoryMovieOneTraceIdBean bean);
}
