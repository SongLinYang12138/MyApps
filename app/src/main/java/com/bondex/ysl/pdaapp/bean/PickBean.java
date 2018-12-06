package com.bondex.ysl.pdaapp.bean;

/**
 * date: 2018/12/4
 * Author: ysl
 * description:
 */
public class PickBean {


    /**
     * customername : 义嵩渔具
     * orderno : SOCD181100003
     * allocationdetailsid : 0000000153
     * location : 2A-3
     * traceid : yy130300000017
     * sku : YSLU00001
     * skuname : 路亚竿2
     * qty : 5.0
     */

    private String customername;
    private String orderno;
    private String allocationdetailsid;
    private String location;
    private String traceid;
    private String sku;
    private String skuname;
    private double qty;

//    扫描得到的值
    private String scanLocation;
    private String scanTraceId;
    private String scanSku;

    public String getScanLocation() {
        return scanLocation == null ? "" : scanLocation;
    }

    public void setScanLocation(String scanLocation) {
        this.scanLocation = scanLocation == null ? "" : scanLocation;
    }

    public String getScanTraceId() {
        return scanTraceId == null ? "" : scanTraceId;
    }

    public void setScanTraceId(String scanTraceId) {
        this.scanTraceId = scanTraceId == null ? "" : scanTraceId;
    }

    public String getScanSku() {
        return scanSku == null ? "" : scanSku;
    }

    public void setScanSku(String scanSku) {
        this.scanSku = scanSku == null ? "" : scanSku;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getAllocationdetailsid() {
        return allocationdetailsid;
    }

    public void setAllocationdetailsid(String allocationdetailsid) {
        this.allocationdetailsid = allocationdetailsid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTraceid() {
        return traceid;
    }

    public void setTraceid(String traceid) {
        this.traceid = traceid;
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

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }
}
