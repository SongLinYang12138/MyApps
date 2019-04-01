package com.bondex.ysl.pdaapp.bean;

/**
 * date: 2018/12/4
 * Author: ysl
 * description:
 */
public class HttpRequestParam {


    /**
     * success : true
     * msg :
     * business_param  :
     * errormsg  :
     * method  :
     */

    private boolean success;
    private String msg;
    private String business_param;
    private String errormsg;
    private String method;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    @Override
    public String toString() {
        return "HttpRequestParam{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", business_param='" + business_param + '\'' +
                ", errormsg='" + errormsg + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
