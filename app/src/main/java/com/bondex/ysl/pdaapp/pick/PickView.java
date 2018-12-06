package com.bondex.ysl.pdaapp.pick;

import com.bondex.ysl.pdaapp.base.BaseView;
import com.bondex.ysl.pdaapp.bean.PickBean;

/**
 * date: 2018/12/4
 * Author: ysl
 * description:
 */
public interface PickView extends BaseView {

    void setAdapter(PickAdapter adapter);

    void setTotalSize(int size,int position);

    void last(int position);
    void next(int position);

    void showAlert(String msg, PickBean bean);
}
