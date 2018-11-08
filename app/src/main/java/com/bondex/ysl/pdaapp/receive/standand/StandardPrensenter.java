package com.bondex.ysl.pdaapp.receive.standand;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BasePresnter;

/**
 * date: 2018/11/8
 * Author: ysl
 * description:
 */
public class StandardPrensenter extends BasePresnter<StandardView,StandardModal> {

    public StandardPrensenter(StandardView view, Context context) {
        super(view, context);
    }

    @Override
    public StandardModal getModal() {
        return null;
    }

    @Override
    public void initData() {

    }
}
