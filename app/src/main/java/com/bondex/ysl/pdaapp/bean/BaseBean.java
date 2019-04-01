package com.bondex.ysl.pdaapp.bean;

public class BaseBean {


//    {"success":true,"msg":"{\"userid\":\"maggie.sun\",\"username\":\"孙潞\",\"salt\":\"57134104\",
// \"userlevel\":9,\"warehouses\":[{\"no\":8,\"SUBSYSTEM_ID\":\"WMS08\",\"SUBSYSTEM_NAME\":\"海程邦达仓库\"}]}",
// "business_param":null,"errormsg":null,"method":null,"dateKey":"224471EC-1247-4FFB-A22C-FF16344C44EE"}

    private boolean success;
    private String msg;
    private String business_param;
    private String errormsg;
    private String method;
    private String dateKey;

    public String getDateKey() {
        return dateKey == null ? "" : dateKey;
    }

    public void setDateKey(String dateKey) {
        this.dateKey = dateKey == null ? "" : dateKey;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? "" : msg;
    }

    public String getBusiness_param() {
        return business_param == null ? "" : business_param;
    }

    public void setBusiness_param(String business_param) {
        this.business_param = business_param == null ? "" : business_param;
    }

    public String getErrormsg() {
        return errormsg == null ? "" : errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg == null ? "" : errormsg;
    }

    public String getMethod() {
        return method == null ? "" : method;
    }

    public void setMethod(String method) {
        this.method = method == null ? "" : method;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
