package com.zy.zhongyiandroid.data.shared;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zy.zhongyiandroid.ZhongYi;

/**
 * 作用：提供对sharedPreferences的读写操作
 * 使用方法：在此注册 key，然后在UserData中编写相关的 get set方法，通过get,set方法间接调用本类，业务模块不可直接调用本类中的方法
 * 
 * @author looming
 */
public  class SharedPreferencesManager {
	
	private static final Context context =ZhongYi.newInstance();
	/** 用户是否登陆 */

	public static final String Is_SHOW_LOGIN = "isShowLogin"; 
	
	public static final String USER_ACCOUNT="userAccount";
	
	
	/**
	 * 写入boolean
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public void writeBooleanPreferences(String key,
			boolean value) {
		SharedPreferences sp = context.getSharedPreferences(
				UserData.SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	/**
	 * 读取boolean
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public boolean readBooleanPreferences(String key,
			boolean defaultValue) {
		Context context = ZhongYi.newInstance();
		SharedPreferences sp = context.getSharedPreferences(
				UserData.SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, defaultValue);
	}

	/**
	 * 写入String
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public void writeStringPreferences(String key,
			String value) {
		SharedPreferences sp = context.getSharedPreferences(
				UserData.SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}

	/**
	 * 读取String
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public String readStringPreferences(String key) {
		SharedPreferences sp = context.getSharedPreferences(
				UserData.SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		return sp.getString(key, null);
	}

	/**
	 * 写入Long
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public void writeLongPreferences(String key,
			long value) {
		SharedPreferences sp = context.getSharedPreferences(
				UserData.SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putLong(key, value);
		edit.commit();
	}

	/**
	 * 读取Long
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public long readLongPreferences(String key,
			long value) {
		SharedPreferences sp = context.getSharedPreferences(
				UserData.SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		return sp.getLong(key, 0);
	}

	/**
	 * 写入int
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public void writeIntPreferences(String key,
			int value) {
		SharedPreferences sp = context.getSharedPreferences(
				UserData.SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putInt(key, value);
		edit.commit();
	}

	/**
	 * 读取int
	 * 
	 * @param context
	 * @param key
	 */
	public int readIntPreferences(String key) {
		SharedPreferences sp = context.getSharedPreferences(
				UserData.SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);
		return sp.getInt(key, 0);
	}
}

