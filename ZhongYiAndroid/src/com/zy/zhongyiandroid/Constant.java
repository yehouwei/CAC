package com.zy.zhongyiandroid;

import android.os.Environment;


public class Constant {
	//状态栏通知testttkkk
	public static class NotificationId{
		public static final int PLAYER = 1;
		public static final int UPDATE = 2;
		public static final int DOWNLOAD = 3;
	}
	/** 文件夹名称 */
	public static final String FOLDER_NAME = "folder_name"; 
	/** 文件夹路径 */
	public static final String FOLDER_PATH = "folder_path"; 
	/** 歌单ID */
	public static final String SONGLIST_ID = "songlsit_id"; 
	/** 歌单名称 */
	public static final String SONGLIST_NAME = "songlsit_name"; 
	/** 7天时间 */
	public static final long RECENT_ADD_TIME = 7 * 24 * 60 * 60;  
	/** 设置广播 */
	public static final String SETTING_CHANGE_BROADCAST = "setting_change_broadcast";  
	/** “最近添加”对应的歌单名 */
	public static final String RECENT_NAME = "最近添加";
	/**
	 * 代理主机http请求头部key
	 */
	public static final String PROXY_ONLINE_HOST = "x-online-host";

	public static class SdcardPath{
		public static final String SDCARD_ROOT = Environment
				.getExternalStorageDirectory().getAbsolutePath();
		/** 根目录 */
		public static final String SAVE_ROOTPATH = Environment
				.getExternalStorageDirectory() + "/ZhongYi";

		/** 图片缓存目录 */
		public static final String IMAGE_SAVEPATH = SAVE_ROOTPATH + "/images";

		/** 缓存目录 */
		public static final String CACHE_SAVEPATH = SAVE_ROOTPATH + "/cache";


		/** 应用更新目录 */
		public static final String UPDATE_APK_SAVEPATH = SAVE_ROOTPATH + "/update";

		/** 文件缓存目录 */
		public static final String DOWNLOAD_TMP_SAVEPATH = SAVE_ROOTPATH + "/tmp";
		
		/** 日志 */
		public static final String LOG_SAVEPATH = SAVE_ROOTPATH + "/log";

	}
	
}
