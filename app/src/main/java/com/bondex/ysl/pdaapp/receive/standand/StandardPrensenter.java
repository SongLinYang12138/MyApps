package com.bondex.ysl.pdaapp.receive.standand;

import android.content.Context;

import com.bondex.ysl.pdaapp.base.BasePresnter;
import com.bondex.ysl.pdaapp.bean.ReceiveStandardCodeBean;
import com.bondex.ysl.pdaapp.util.CommonUtil;

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


        if (!isRunning) {
            modal.searchCode(code);
            isRunning = true;
            view.showLoading();
        }

    }

    public void searchProduct(String productId) {


        if (receiveStandardCodeBean == null) return;

        if(!isRunning){

            String asnno = receiveStandardCodeBean.getAsnno();
            modal.searchProduct(asnno, productId);
            view.showLoading();
        }
    }

    public void receiveConfirm(int receiveQty, String receiveLocation, String holdrejectCode, double weight, double fweight, double volume, double price) {

        if(!isRunning){

            view.showLoading();
            modal.receiving(receiveStandardCodeBean.getAsnno(), receiveStandardCodeBean.getAsnlineno(), receiveQty, receiveLocation, holdrejectCode, weight, fweight, volume, price);
        }

    }


    @Override
    public void searchBack(ReceiveStandardCodeBean bean) {
        view.codeResult(bean);

        receiveStandardCodeBean = bean;
        view.stopLoading();
        isRunning = false;
    }

    @Override
    public void searchFaile(String msg) {

        view.searchCodeFaile(msg);
        view.stopLoading();
        isRunning = false;

    }

    @Override
    public void resultProduct(ReceiveStandardCodeBean bean) {

        view.productResult(bean);
        view.stopLoading();
        receiveStandardCodeBean = bean;
        isRunning = false;


        String str = view.getReceiveNm();

        if (CommonUtil.isEmpty(str)) {
            str = "0";
        }

        if (!CommonUtil.isNumber(str)) {
            return;
        }
        modifyTotal(Double.valueOf(str));

    }

    @Override
    public void productFailed(String msg) {
        view.productFaile(msg);
        view.stopLoading();
        isRunning = false;

    }

    @Override
    public void receiveSuccess(String s) {

        view.receiveSuccess(s);
        view.stopLoading();
        isRunning = false;

    }

    @Override
    public void receiveFailed(String msg) {

        view.receiveFalied(msg);
        view.stopLoading();
        isRunning = false;

    }


    /**
     * 设置默认数值
     */
    protected void modifyTotal(double num) {

        if (receiveStandardCodeBean == null) return;

        double totalWeight = num * receiveStandardCodeBean.getNw();

        double totalFweight = num * receiveStandardCodeBean.getGw();

        double volume = num * receiveStandardCodeBean.getCubic();

        double price = num * receiveStandardCodeBean.getPrice();

        view.modifyTotal(totalWeight, totalFweight, volume, price);

    }

}
