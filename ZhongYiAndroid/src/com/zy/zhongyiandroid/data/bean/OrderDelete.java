package com.zy.zhongyiandroid.data.bean;

import java.io.Serializable;

public class OrderDelete implements Serializable {
	private Boolean flag;
	private String message;

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
}
