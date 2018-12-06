package com.bondex.ysl.pdaapp.pick;

import com.bondex.ysl.pdaapp.bean.PickBean;

/**
 * date: 2018/12/5
 * Author: ysl
 * description:
 */
public interface AdapterInteractive {

    void last(int position);
    void next(int position);
    void pick(PickBean bean);



}
