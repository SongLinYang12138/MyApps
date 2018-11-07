package com.bondex.ysl.pdaapp.middle;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.base.BaseView;

/**
 * date: 2018/11/6
 * Author: ysl
 * description:
 */
public class MiddlePresenter extends BasePresnter<MiddleView,BaseModel> {


    public MiddlePresenter(MiddleView view, Context context) {
        super(view, context);
    }

    @Override
    public BaseModel getModal() {
        return null;
    }

    @Override
    public void initData() {

    }
}
