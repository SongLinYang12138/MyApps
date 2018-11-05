package com.bondex.ysl.pdaapp.netutil;

public class ApiParam {
	private String app_id;
	private String charset;
	private long timestamp;
	private String method;
	private String business_param;
	private String sign;
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getBusiness_param() {
		return business_param;
	}
	public void setBusiness_param(String business_param) {
		this.business_param = business_param;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
