package com.bondex.ysl.pdaapp.receive.standand;

import android.databinding.BaseObservable;
import android.databinding.Bindable;



/**
 * date: 2018/11/20
 * Author: ysl
 * description:
 */
public class StandarBean extends BaseObservable {

    private String asnno;
    private String cutom;
    private String product;
    private int  receiveNum;
    private String receiveLocation;

    @Bindable
    public String getAsnno() {
        return asnno == null ? "" : asnno;
    }

    public void setAsnno(String asnno) {
        this.asnno = asnno == null ? "" : asnno;
    }

    @Bindable
    public String getCutom() {
        return cutom == null ? "" : cutom;
    }

    public void setCutom(String cutom) {

        this.cutom = cutom == null ? "" : cutom;
    }

    public String getProduct() {
        return product == null ? "" : product;
    }

    public void setProduct(String product) {
        this.product = product == null ? "" : product;
    }

    public int getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(int receiveNum) {
        this.receiveNum = receiveNum;
    }

    public String getReceiveLocation() {
        return receiveLocation == null ? "" : receiveLocation;
    }

    public void setReceiveLocation(String receiveLocation) {
        this.receiveLocation = receiveLocation == null ? "" : receiveLocation;
    }
}
