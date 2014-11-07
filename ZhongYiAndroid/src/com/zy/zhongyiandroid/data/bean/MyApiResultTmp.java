package com.zy.zhongyiandroid.data.bean;

import java.io.Serializable;

public class MyApiResultTmp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1159363359927812776L;
	private int code;
	private String ver;
	private String errMsg;
	private String rows;
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
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	
	
	
}
