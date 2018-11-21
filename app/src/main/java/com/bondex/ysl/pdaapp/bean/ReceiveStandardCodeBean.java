package com.bondex.ysl.pdaapp.bean;

/**
 * date: 2018/11/21
 * Author: ysl
 * description:
 */
public class ReceiveStandardCodeBean {





    /**
     * customerid : GT12110174
     * customername : 山东泰山壹伍叁贰物联供应链有限公司
     * asnno : ASNCD18111200001
     * asnlineno : 1
     * sku : 12000001
     * skuname : 泰山（新品）
     * expectedqty : 10.0
     * receivedqty : 2.0
     * receivinglocation : A1-1
     */

    private String customerid;
    private String customername;
    private String asnno;
    private int asnlineno;
    private String sku;
    private String skuname;
    private double expectedqty;
    private double receivedqty;
    private String receivinglocation;




    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getAsnno() {
        return asnno;
    }

    public void setAsnno(String asnno) {
        this.asnno = asnno;
    }

    public int getAsnlineno() {
        return asnlineno;
    }

    public void setAsnlineno(int asnlineno) {
        this.asnlineno = asnlineno;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuname() {
        return skuname;
    }

    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }

    public double getExpectedqty() {
        return expectedqty;
    }

    public void setExpectedqty(double expectedqty) {
        this.expectedqty = expectedqty;
    }

    public double getReceivedqty() {
        return receivedqty;
    }

    public void setReceivedqty(double receivedqty) {
        this.receivedqty = receivedqty;
    }

    public String getReceivinglocation() {
        return receivinglocation;
    }

    public void setReceivinglocation(String receivinglocation) {
        this.receivinglocation = receivinglocation;
    }
}
