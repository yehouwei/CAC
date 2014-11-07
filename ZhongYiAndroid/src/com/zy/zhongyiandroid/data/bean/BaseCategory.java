package com.zy.zhongyiandroid.data.bean;

import java.io.Serializable;

public class BaseCategory implements Serializable{
	private String catImage;
	private String catName;
	private String catCode;
	public String getCatImage() {
		return catImage;
	}
	public void setCatImage(String catImage) {
		this.catImage = catImage;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getCatCode() {
		return catCode;
	}
	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}
	
}
