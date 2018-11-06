package com.bondex.ysl.pdaapp.inventory;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BaseModel;
import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.base.BaseView;

/**
 * date: 2018/11/5
 * Author: ysl
 * description:
 */
public class InventoryPresenter extends BasePresnter<InvertoryView,BaseModel> {



    public InventoryPresenter(InvertoryView view, Context context) {
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
