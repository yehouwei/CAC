package com.zy.zhongyiandroid.data.Api;

import com.encore.libs.http.IDataParser;
import com.encore.libs.json.JacksonUtils;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.MyApiResultTmp;

public class MyJsonParser implements IDataParser {
	private Class parserClass;
	/**
	 * 是否是列表
	 */
	private boolean isList;

	/**
	 * 
	 * @param parseClass 解析类
	 * @param isList 是否解析为列表
	 */
	public MyJsonParser(Class parseClass, boolean isList) {
		this.parserClass = parseClass;
		this.isList = isList;
	}

	public MyJsonParser(Class parseClass) {
		this(parseClass, false);
	}

	@Override
	public Object parseData(String data) {
		MyApiResultTmp myApiResultTmp = JacksonUtils.shareJacksonUtils().parseJson2Obj(data, MyApiResultTmp.class);
		Object sonObject = null;

		if (myApiResultTmp == null) {
			return null;
		}

		if (myApiResultTmp.getRows() != null) {
			String tmpResult = (String)myApiResultTmp.getRows();
			if (isList) {
				sonObject = JacksonUtils.shareJacksonUtils().parseJson2List(tmpResult, parserClass);
			} else {
				sonObject = JacksonUtils.shareJacksonUtils().parseJson2Obj(tmpResult, parserClass);
			}
		}
		MyApiResult myApiResult = new MyApiResult();
		myApiResult.setCode(myApiResultTmp.getCode());
		myApiResult.setErrMsg(myApiResultTmp.getErrMsg());
		myApiResult.setVer(myApiResultTmp.getVer());
		
		if (sonObject != null) {
			myApiResult.setRows(sonObject);
		} else {
			myApiResult.setRows(null);
			
			
		}

		return myApiResult;
	}
}
