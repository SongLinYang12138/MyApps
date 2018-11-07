package com.bondex.ysl.pdaapp.exwarehouse;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BasePresnter;

/**
 * date: 2018/11/6
 * Author: ysl
 * description:
 */
public class ConsigementPresenter extends BasePresnter<ConsigementView,ConsigeMentModel> {

    public ConsigementPresenter(ConsigementView view, Context context) {
        super(view, context);
    }

    @Override
    public ConsigeMentModel getModal() {
        return null;
    }

    @Override
    public void initData() {

    }
}
