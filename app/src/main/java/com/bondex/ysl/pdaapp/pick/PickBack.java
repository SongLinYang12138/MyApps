package com.bondex.ysl.pdaapp.pick;

import com.bondex.ysl.pdaapp.base.BaseBack;
import com.bondex.ysl.pdaapp.bean.PickBean;

import java.util.ArrayList;

/**
 * date: 2018/12/4
 * Author: ysl
 * description:
 */
public interface PickBack extends BaseBack {

    void searchSuccess(ArrayList<PickBean> list);

    void searchFaile(String msg);

    void pickSuccess(String s);
    void pickFaile(String s);

}
