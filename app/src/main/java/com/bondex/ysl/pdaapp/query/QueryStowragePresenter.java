package com.bondex.ysl.pdaapp.query;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BasePresnter;

/**
 * date: 2018/11/21
 * Author: ysl
 * description:
 */
public class QueryStowragePresenter  extends BasePresnter<QueryStowrageView,QueryStowrageModel> {


    public QueryStowragePresenter(QueryStowrageView view, Context context) {
        super(view, context);
    }

    @Override
    public QueryStowrageModel getModal() {
        return null;
    }

    @Override
    public void initData() {

    }
}
