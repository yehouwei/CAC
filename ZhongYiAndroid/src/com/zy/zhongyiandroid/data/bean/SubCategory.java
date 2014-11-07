package com.zy.zhongyiandroid.data.bean;

import java.io.Serializable;

import android.R.integer;

public class SubCategory implements Serializable{
	private String catImage;
	private String catDescribe;
	private String catName;
	private String catCode;
	private String catEnabled;
	private int catSort;
	private String catPid;
	public String getCatDescribe() {
		return catDescribe;
	}
	public void setCatDescribe(String catDescribe) {
		this.catDescribe = catDescribe;
	}

	public String getCatImage() {
		return catImage;
	}
	public void setCatImage(String catImage) {
		this.catImage = catImage;
	}
	public String getCatCode() {
		return catCode;
	}
	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getCatEnabled() {
		return catEnabled;
	}
	public void setCatEnabled(String catEnabled) {
		this.catEnabled = catEnabled;
	}
	public int getCatSort() {
		return catSort;
	}
	public void setCatSort(int catSort) {
		this.catSort = catSort;
	}
	public String getCatPid() {
		return catPid;
	}
	public void setCatPid(String catPid) {
		this.catPid = catPid;
	}

}
