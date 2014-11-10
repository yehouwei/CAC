package com.zy.zhongyiandroid.data.bean;

import java.io.Serializable;

public class UserLoginResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1159363359927812776L;
	private Boolean isLogin;
	private String message;
	private Object userInfo;
	public Boolean getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Object userInfo) {
		this.userInfo = userInfo;
	}


	
}
