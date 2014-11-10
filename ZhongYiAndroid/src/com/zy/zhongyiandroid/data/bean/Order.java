package com.zy.zhongyiandroid.data.bean;

import java.io.Serializable;

public class Order implements Serializable {
	 private String creatDateTime;
	private String phone;
	private String  status;
	 private String remarks;
	private String shopImages;
	 private int id;
	private String email;
	 private int shopId;
	private String shopName;
	private int userId;
	private String userName;
	private String appellation;
	private String arrageDateTime;
	public String getCreatDateTime() {
		return creatDateTime;
	}
	public void setCreatDateTime(String creatDateTime) {
		this.creatDateTime = creatDateTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getShopImages() {
		return shopImages;
	}
	public void setShopImages(String shopImages) {
		this.shopImages = shopImages;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
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
	public String getAppellation() {
		return appellation;
	}
	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}
	public String getArrageDateTime() {
		return arrageDateTime;
	}
	public void setArrageDateTime(String arrageDateTime) {
		this.arrageDateTime = arrageDateTime;
	}

}
