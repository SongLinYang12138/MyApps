package com.bondex.ysl.pdaapp.bean.loginebean;

public class WarehousesBean {

    /**
     * no : 1
     * SUBSYSTEM_ID : WMS01
     * SUBSYSTEM_NAME : 成都邦达吉通仓库
     */

    private int no;
    private String SUBSYSTEM_ID;
    private String SUBSYSTEM_NAME;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getSUBSYSTEM_ID() {
        return SUBSYSTEM_ID;
    }

    public void setSUBSYSTEM_ID(String SUBSYSTEM_ID) {
        this.SUBSYSTEM_ID = SUBSYSTEM_ID;
    }

    public String getSUBSYSTEM_NAME() {
        return SUBSYSTEM_NAME;
    }

    public void setSUBSYSTEM_NAME(String SUBSYSTEM_NAME) {
        this.SUBSYSTEM_NAME = SUBSYSTEM_NAME;
    }
}
