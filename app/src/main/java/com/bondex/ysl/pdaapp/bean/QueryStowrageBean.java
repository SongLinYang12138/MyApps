package com.bondex.ysl.pdaapp.bean;

/**
 * date: 2018/11/22
 * Author: ysl
 * description:
 */
public class QueryStowrageBean {


    /**
     * customerid : GEER
     * customername : GEER
     * sku : 0001
     * skuname : 晶体管
     * locationid : A1-1
     * traceid : yy130500000032
     * qty : 13.0
     * qtyallocated : 12.0
     * qtyonhold : 0.0
     * qtyused : 1.0
     */

    private String customerid;
    private String customername;
    private String sku;
    private String skuname;
    private String locationid;
    private String traceid;
    private double qty;
    private double qtyallocated;
    private double qtyonhold;
    private double qtyused;

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

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getTraceid() {
        return traceid;
    }

    public void setTraceid(String traceid) {
        this.traceid = traceid;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getQtyallocated() {
        return qtyallocated;
    }

    public void setQtyallocated(double qtyallocated) {
        this.qtyallocated = qtyallocated;
    }

    public double getQtyonhold() {
        return qtyonhold;
    }

    public void setQtyonhold(double qtyonhold) {
        this.qtyonhold = qtyonhold;
    }

    public double getQtyused() {
        return qtyused;
    }

    public void setQtyused(double qtyused) {
        this.qtyused = qtyused;
    }
}
