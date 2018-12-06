package com.bondex.ysl.pdaapp.stowrage;

import android.widget.ArrayAdapter;

import com.bondex.ysl.pdaapp.base.BaseView;

public interface StowrageView extends BaseView {

    void setBottomText(String msg);
    void setAdapter(ArrayAdapter<String> adapter);

}
