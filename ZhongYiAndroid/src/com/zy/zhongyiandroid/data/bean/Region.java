package com.zy.zhongyiandroid.data.bean;

import java.io.Serializable;

import android.R.integer;

public class Region implements Serializable {

 private int id;
 private String text;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}

}
