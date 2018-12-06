package com.bondex.ysl.pdaapp.base;

import android.content.Context;

import com.orhanobut.logger.Logger;

public abstract class BasePresnter<V extends BaseView, M extends BaseModel> {

    protected V view;
    protected M modal;
    protected Context context;
    protected  boolean isRunning;

    public BasePresnter(V view, Context context) {


        this.view = (V) view;
        this.context = context;

        modal = getModal();

        initData();
        if (this.view != null) view.initView();
        else Logger.i("base view == null");

    }

    public abstract M getModal();

    public abstract void initData();


}
