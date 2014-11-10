package com.zy.zhongyiandroid.data.Api;

import com.encore.libs.http.IDataParser;
import com.encore.libs.json.JacksonUtils;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.MyApiResultTmp;
import com.zy.zhongyiandroid.data.bean.UserInfo;
import com.zy.zhongyiandroid.data.bean.UserLoginResult;
import com.zy.zhongyiandroid.data.bean.UserLoginTmp;

public class UserJsonParser implements IDataParser {
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
	public UserJsonParser(Class parseClass, boolean isList) {
		this.parserClass = parseClass;
		this.isList = isList;
	}

	public UserJsonParser(Class parseClass) {
		this(parseClass, false);
	}

	@Override
	public Object parseData(String data) {
		UserLoginTmp userLoginTmp = JacksonUtils.shareJacksonUtils().parseJson2Obj(data, UserLoginTmp.class);
		Object sonObject = null;

		if (userLoginTmp == null) {
			return null;
		}

		if (userLoginTmp.getUserInfo() != null) {
			String tmpResult = (String)userLoginTmp.getUserInfo();
			if (isList) {
				sonObject = JacksonUtils.shareJacksonUtils().parseJson2List(tmpResult, parserClass);
			} else {
				sonObject = JacksonUtils.shareJacksonUtils().parseJson2Obj(tmpResult, parserClass);
			}
		}
		UserLoginResult myApiResult = new UserLoginResult();
		myApiResult.setIsLogin(userLoginTmp.getIsLogin());
		myApiResult.setMessage(userLoginTmp.getMessage());

		
		if (sonObject != null) {
			myApiResult.setUserInfo(sonObject);
		} else {
			myApiResult.setUserInfo(null);
			
			
		}

		return myApiResult;
	}
}
