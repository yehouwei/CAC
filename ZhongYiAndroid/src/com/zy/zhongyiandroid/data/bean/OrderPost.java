package com.zy.zhongyiandroid.data.bean;

import java.io.Serializable;

public class OrderPost implements Serializable {
	private Boolean flag;
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private String message;
}
