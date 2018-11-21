package com.bondex.ysl.pdaapp.receive.standand;

import com.bondex.ysl.pdaapp.base.BaseView;
import com.bondex.ysl.pdaapp.bean.ReceiveStandardCodeBean;

/**
 * date: 2018/11/8
 * Author: ysl
 * description:
 */
public interface StandardView extends BaseView {

    void showSearch();

    void codeResult(ReceiveStandardCodeBean bean);
    void searchCodeFaile(String msg);

    void productResult(ReceiveStandardCodeBean bean);
    void productFaile(String msg);

    void receiveSuccess(String msg);
    void receiveFalied(String msg);

}
