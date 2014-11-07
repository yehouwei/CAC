package com.zy.zhongyiandroid.data.bean;

import java.io.Serializable;

import android.R.integer;

public class User implements Serializable{
	private int userId;
	private String userName;
	private String userShop;
	private String userPassword;
	private int userRole;
	private String userCreatDatetime;
	private String lastEditUser;
	private String lastEditDatetime;
	private int score;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserShop() {
		return userShop;
	}
	public void setUserShop(String userShop) {
		this.userShop = userShop;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public int getUserRole() {
		return userRole;
	}
	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
	public String getUserCreatDatetime() {
		return userCreatDatetime;
	}
	public void setUserCreatDatetime(String userCreatDatetime) {
		this.userCreatDatetime = userCreatDatetime;
	}
	public String getLastEditUser() {
		return lastEditUser;
	}
	public void setLastEditUser(String lastEditUser) {
		this.lastEditUser = lastEditUser;
	}
	public String getLastEditDatetime() {
		return lastEditDatetime;
	}
	public void setLastEditDatetime(String lastEditDatetime) {
		this.lastEditDatetime = lastEditDatetime;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
