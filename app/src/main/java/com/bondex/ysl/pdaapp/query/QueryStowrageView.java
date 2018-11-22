package com.bondex.ysl.pdaapp.query;

import com.bondex.ysl.pdaapp.base.BaseView;
import com.bondex.ysl.pdaapp.bean.QueryStowrageBean;

import java.util.ArrayList;

/**
 * date: 2018/11/21
 * Author: ysl
 * description:
 */
public interface QueryStowrageView extends BaseView {

    void showSearDialog();

    void resultSuccess(QueryStowrageAdapter adapter);
    void resultFailed(String msg);
}
