package com.bondex.ysl.pdaapp.receive.standand;

import com.bondex.ysl.pdaapp.base.BaseBack;
import com.bondex.ysl.pdaapp.bean.ReceiveStandardCodeBean;

/**
 * date: 2018/11/8
 * Author: ysl
 * description:
 */
public interface StandardCallback extends BaseBack {

    void searchBack(ReceiveStandardCodeBean bean);
    void searchFaile(String msg);

    void resultProduct(ReceiveStandardCodeBean bean);
    void productFailed(String msg);

    void receiveSuccess(String s);
    void  receiveFailed(String msg);

}
