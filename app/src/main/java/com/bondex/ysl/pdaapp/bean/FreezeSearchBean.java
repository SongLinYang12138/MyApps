package com.bondex.ysl.pdaapp.bean;

/**
 * date: 2019/4/1
 * Author: ysl
 * description:
 */
public class FreezeSearchBean {


    /**
     * lotnum : LT00000119
     * locationid : M1-1
     * traceid : yy130400000097
     * qty : 3.0
     * qtyonhold : 0.0
     * inventoryholdid : null
     * holdby : null
     * holdcode : null
     * holdreason : null
     */

    private String lotnum;
    private String locationid;
    private String traceid;
    private double qty;
    private double qtyonhold;
    private String inventoryholdid;
    private String holdby;
    private String holdcode;
    private String holdreason;

    public String getLotnum() {
        return lotnum == null ? "" : lotnum;
    }

    public void setLotnum(String lotnum) {
        this.lotnum = lotnum == null ? "" : lotnum;
    }

    public String getLocationid() {
        return locationid == null ? "" : locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid == null ? "" : locationid;
    }

    public String getTraceid() {
        return traceid == null ? "" : traceid;
    }

    public void setTraceid(String traceid) {
        this.traceid = traceid == null ? "" : traceid;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getQtyonhold() {
        return qtyonhold;
    }

    public void setQtyonhold(double qtyonhold) {
        this.qtyonhold = qtyonhold;
    }

    public String getInventoryholdid() {
        return inventoryholdid == null ? "" : inventoryholdid;
    }

    public void setInventoryholdid(String inventoryholdid) {
        this.inventoryholdid = inventoryholdid == null ? "" : inventoryholdid;
    }

    public String getHoldby() {
        return holdby == null ? "" : holdby;
    }

    public void setHoldby(String holdby) {
        this.holdby = holdby == null ? "" : holdby;
    }

    public String getHoldcode() {
        return holdcode == null ? "" : holdcode;
    }

    public void setHoldcode(String holdcode) {
        this.holdcode = holdcode == null ? "" : holdcode;
    }

    public String getHoldreason() {
        return holdreason == null ? "" : holdreason;
    }

    public void setHoldreason(String holdreason) {
        this.holdreason = holdreason == null ? "" : holdreason;
    }
}
