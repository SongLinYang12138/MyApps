package com.bondex.ysl.pdaapp.receive.standand;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.ReceiveStandardCodeBean;

/**
 * date: 2018/11/8
 * Author: ysl
 * description:
 */
public class StandardPrensenter extends BasePresnter<StandardView, StandardModal> implements StandardCallback {

    private ReceiveStandardCodeBean receiveStandardCodeBean;


    public StandardPrensenter(StandardView view, Context context) {
        super(view, context);

        view.showSearch();
    }

    @Override
    public StandardModal getModal() {

        modal = new StandardModal(context);
        modal.setCallback(this);
        return modal;
    }

    @Override
    public void initData() {

    }


    public void searchCode(String code) {


        modal.searchCode(code);
        view.showLoading();
    }

    public void searchProduct(String productId) {

        if (receiveStandardCodeBean == null) return;
        String asnno = receiveStandardCodeBean.getAsnno();
        modal.searchProduct(asnno,productId);
        view.showLoading();
    }

    public void receiveConfirm(String receiveQty,String receiveLocation,String holdrejectCode){


        modal.receiving(receiveStandardCodeBean.getAsnno(),receiveStandardCodeBean.getAsnlineno()+"",receiveQty,receiveLocation,holdrejectCode);
    }


    @Override
    public void searchBack(ReceiveStandardCodeBean bean) {
        view.codeResult(bean);

        receiveStandardCodeBean = bean;
        view.stopLoading();
    }

    @Override
    public void searchFaile(String msg) {

        view.searchCodeFaile(msg);
        view.stopLoading();
    }

    @Override
    public void resultProduct(ReceiveStandardCodeBean bean) {

        view.productResult(bean);
        view.stopLoading();
        receiveStandardCodeBean = bean;
    }

    @Override
    public void productFailed(String msg) {
        view.productFaile(msg);
        view.stopLoading();
    }

    @Override
    public void receiveSuccess(String s) {

    }

    @Override
    public void receiveFailed(String msg) {

    }
}
