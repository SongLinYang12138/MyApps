package com.bondex.ysl.pdaapp.receive.confuse;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BasePresnter;

/**
 * date: 2018/11/8
 * Author: ysl
 * description:
 */
public class ConfusePrensenter extends BasePresnter<ConfuseView,ConfuseModal> {

    public ConfusePrensenter(ConfuseView view, Context context) {
        super(view, context);
    }

    @Override
    public ConfuseModal getModal() {
        return null;
    }

    @Override
    public void initData() {

    }
}
