package com.bondex.ysl.pdaapp.bean;

public class BaseBean {


    /**
     * success : true
     * msg : {"userid":"wangzhenying","username":"王震鹰","salt":"54863945","userlevel":9,"warehouses":[{"no":1,"SUBSYSTEM_ID":"WMS01","SUBSYSTEM_NAME":"成都邦达吉通仓库"}]}
     * business_param : null
     * errormsg : null
     * method : null
     */

    private boolean success;
    private String msg;
    private String business_param;
    private String errormsg;
    private String method;


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
