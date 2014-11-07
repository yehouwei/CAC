package com.zy.zhongyiandroid.data.shared;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class UserData {

//临时数据---开始
//以下数据，生命周期与应用相同,退出应用后，即丢失
	/** 网络连接代理主机名 */
	private static String mProxyHost;

	/** 网络连接代理端口 */
	private static int mProxyPort;
//临时数据---结束
	
	public static String SHARED_PREFERENCES_NAME = "ZHONGYI_PREFERENCES";
	private static UserData mInstence = new UserData();
	private SharedPreferencesManager mSharedPreferencesManager = new SharedPreferencesManager();
	
	
	/**
	 * 客户端是否已经展示过引导页面
	 * @param isShowedNewUserGuide
	 */
	public void setUserAccount(String UserAccount){
		mSharedPreferencesManager.writeStringPreferences(SharedPreferencesManager.USER_ACCOUNT,UserAccount);
	}
	
	/**
	 * 客户端是否已经展示过引导页面
	 */
	public String getUserAccount(){
		return mSharedPreferencesManager.readStringPreferences(SharedPreferencesManager.USER_ACCOUNT);
	}	
	

	

	
	/**
	 * 是否已扫描本地媒体
	 * @param isSavedLocalMsg
	 */
	public void setisShowLogin(boolean isShowLogin){
		mSharedPreferencesManager.writeBooleanPreferences(SharedPreferencesManager.Is_SHOW_LOGIN,isShowLogin);
	}
	
	public Boolean getIsShowLogin(){
		return mSharedPreferencesManager.readBooleanPreferences(SharedPreferencesManager.Is_SHOW_LOGIN,false);
	}
	


}
