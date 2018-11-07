package com.bondex.ysl.pdaapp.middle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bondex.ysl.pdaapp.base.BaseActivtiy;

/**
 * date: 2018/11/6
 * Author: ysl
 * description:
 */
public class MiddleActivity extends BaseActivtiy<MiddlePresenter> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public MiddlePresenter getPresenter() {
        return null;
    }

    @Override
    public void noDoubleClick(View v) {

    }
}
