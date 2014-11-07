package com.zy.zhongyiandroid.data.bean;

import java.io.Serializable;

public class MyApiResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2009105507330048410L;
	private int code;
	private String ver;
	private String errMsg;
	private Object rows;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		this.rows = rows;
	}
	
	
	
}
