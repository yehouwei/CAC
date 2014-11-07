package com.zy.zhongyiandroid.data.bean;

import java.io.Serializable;
import java.sql.Date;

import android.R.integer;

public class Item implements Serializable{
	private int itemId;
	private String itemCacId;
	private String mainCategory;
	private String subCategory;
	private String itemName;
	private  String itemSize;
	private String itemShop;
	private int itemQty;
	private String itemCurrency;
	private float itemPrice;
	private String promotion;
	private String iconImage;
	private String hdImage;
	private String createDateTime;
	private String cacSource;
	private String enabled;
	private String itemDescription;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemCacId() {
		return itemCacId;
	}
	public void setItemCacId(String itemCacId) {
		this.itemCacId = itemCacId;
	}
	public String getMainCategory() {
		return mainCategory;
	}
	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemSize() {
		return itemSize;
	}
	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}
	public String getItemShop() {
		return itemShop;
	}
	public void setItemShop(String itemShop) {
		this.itemShop = itemShop;
	}
	public int getItemQty() {
		return itemQty;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	public String getItemCurrency() {
		return itemCurrency;
	}
	public void setItemCurrency(String itemCurrency) {
		this.itemCurrency = itemCurrency;
	}
	public float getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	public String getIconImage() {
		return iconImage;
	}
	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
	}
	public String getHdImage() {
		return hdImage;
	}
	public void setHdImage(String hdImage) {
		this.hdImage = hdImage;
	}
	public String getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getCacSource() {
		return cacSource;
	}
	public void setCacSource(String cacSource) {
		this.cacSource = cacSource;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

}