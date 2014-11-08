package com.zy.zhongyiandroid.data.Api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.R.integer;

public class ServerUrl {
	public static String ROOT = "http://202.82.52.233:8080/ZhongYi/";
	// 最新消息列表
	public static String URL_MESSAGES = ROOT + "notice/getPageList.do";
	public static String URL_MESSAGE_DETAIL = ROOT + "notice/getById.do";
	public static String URL_MAIN_CATEGORY = ROOT + "category/getBaseList.do";
	public static String URL_SUB_CATEGORYT = ROOT + "category/getSubList.do";
	public static String URL_ITEMLIST = ROOT + "item/getPageList.do";
	public static String URL_REIGION = ROOT + "region/getList.do";
	public static String URL_STORES = ROOT + "shop/getPageList.do";
	public static String URl_USER = ROOT + "user/getById.do";
	public static String URL_ORDER = ROOT + "arrange/getPageListByUserId.do";
	public static String DATA_FORMAT = "ZH_CN";
	
	public static String URL_ORDER_INSERT=ROOT+"arrange/insert.do";

	public static String getMessageUrl(int pageNum, int pageSize) {
		return URL_MESSAGES + "?page=" + pageNum + "&pageSize=" + pageSize
				+ "&dataFormat=" + DATA_FORMAT;
	}

	public static String getMessageDetailUrl(int messageId) {
		return URL_MESSAGE_DETAIL + "?id=" + messageId + "&dataFormat="
				+ DATA_FORMAT;

	}

	public static String getMainCategoryUrl(int pageNum, int pageSize) {
		return URL_MAIN_CATEGORY + "?page=" + pageNum + "&pageSize=" + pageSize
				+ "&dataFormat=" + DATA_FORMAT;
	}

	public static String getSubCategoryUrl(String catCode, int pageNum,
			int pageSize) {
		return URL_SUB_CATEGORYT + "?catCode=" + catCode + "&page=" + pageNum
				+ "&pageSize=" + pageSize + "&dataFormat=" + DATA_FORMAT;
	}

	public static String getItemListUrl(int pageNum, int pageSize,
			String subCategory) {
		return URL_ITEMLIST + "?page=" + pageNum + "&pageSize=" + pageSize
				+ "&subCategory=" + subCategory + "&dataFormat=" + DATA_FORMAT;
	}

	public static String getResionUrl() {
		return URL_REIGION + "?dataFormat=" + DATA_FORMAT;
	}

	public static String getStoreUrl(int pageNum, int pageSize,int regionId,String keyWord) {

		if((regionId==0)&(keyWord==null)){
			return URL_STORES + "?page=" + pageNum + "&pageSize=" + pageSize
					+ "&dataFormat=" + DATA_FORMAT;
		}else if((regionId!=0)&(keyWord==null)){
			return URL_STORES + "?page=" + pageNum + "&pageSize=" + pageSize+ "&regionId="+regionId+"&dataFormat=" + DATA_FORMAT;
		}else{				
		return URL_STORES + "?page=" + pageNum + "&pageSize=" + pageSize
				+ "&keyWord="+keyWord+"&dataFormat=" + DATA_FORMAT;
		//
		}
	}

	public static String getUserUrl(int userId) {
		return URl_USER + "?userId=" + userId + "&dataFormat=" + DATA_FORMAT;
	}

	public static String getOrderUrl(int userId, int pageNum, int pageSize) {
		return URL_ORDER + "?userId=" + userId + "&page=" + pageNum
				+ "&pageSize=" + pageSize + "&dataFormat=" + DATA_FORMAT;
	}
}
