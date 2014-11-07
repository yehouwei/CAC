package com.zy.zhongyiandroid.utils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.zy.zhongyiandroid.Constant;
import com.zy.zhongyiandroid.R;

/**
 * 公共工具类
 * 
 * @author DK
 * 
 */
public class CommonUtils {
	private static final String TAG = "CommonUtils";

	/**
	 * 默认每页请求数
	 */
	public static final int DEFAULT_PAGESIZE = 10;
	
	/**
	 * 判断当前是否有网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isHasNetwork(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
			return false;
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断当前网络环境是否为wifi
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (ni.getState() == NetworkInfo.State.CONNECTED) {
			return true;
		}
		return false;
	}

	

	/**
	 * 生成32位MD5
	 * 
	 * @param content
	 * @return
	 */
	public static String MD5(String content) {
		String result = null;
		if (content != null) {
			try {
				MessageDigest digest = MessageDigest.getInstance("MD5");
				digest.update(content.getBytes());
				byte buffer[] = digest.digest();
				int i = 0;
				StringBuffer buf = new StringBuffer();
				for (int offset = 0; offset < buffer.length; offset++) {
					i = buffer[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				result = buf.toString().toUpperCase();
			} catch (NoSuchAlgorithmException e) {
				// e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 检测SD卡是否可用
	 * 
	 * @return
	 */
	public static boolean isExternalStorageAvailable() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}

	/**
	 * 手机内存的可用空间大小
	 * 
	 * @return
	 */
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * 手机内存的总空间大小
	 * 
	 * @return
	 */
	public static long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	/**
	 * sdcard的可用空间大小
	 * 
	 * @return
	 */
	public static long getAvailableExternalMemorySize() {
		if (isExternalStorageAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		}
		return 0;
	}

	/**
	 * 获取sdcard的总空间大小
	 * 
	 * @return
	 */
	public static long getTotalExternalMemorySize() {
		if (isExternalStorageAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		}
		return 0;
	}

	/**
	 * 根据链接获取文件名
	 * 
	 * @param url
	 * @return 文件名
	 */
	public static String getFileNameByUrl(String url) {
		if (url == null || (url = url.trim()).length() <= 0) {
			return null;
		}
		int pos = url.indexOf("?");
		if (pos > 0) {
			url = url.substring(0, pos);
		}
		if (url.endsWith("/") && url.length() > 2) {
			url = url.substring(0, url.length() - 1);
		}
		pos = url.lastIndexOf("/");
		if (pos >= 0) {
			return url.substring(pos + 1);
		}
		return null;
	}

	/**
	 * 根据文件名获取文件后缀
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件后缀
	 */
	public static String getSuffixByFileName(String fileName) {
		if (fileName == null || (fileName = fileName.trim()).length() <= 0) {
			return null;
		}
		int pos = fileName.lastIndexOf(".");
		if (pos >= 0 && fileName.length() - 1 > pos) {
			return fileName.substring(pos);
		}
		return null;
	}

	/**
	 * 根据链接返回文件名后缀
	 * 
	 * @param url
	 * @return
	 */
	public static String getSuffixByUrl(String url) {
		String fileName = getFileNameByUrl(url);
		return getSuffixByFileName(fileName);
	}

	/**
	 * 判断字符串中，乱码是否占总字符串长度的60%以上。
	 * 
	 * @param strName
	 *            需要判断的字符串
	 * @return boolean 当乱码的长度 大于总长度的40%时，则返回true，否则返回false。
	 * 
	 * @author Perry
	 */
	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		char[] ch = after.trim().toCharArray();
		float chLength = ch.length;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isDigit(c)) {
				if (!isChineseOrLetter(c)) {
					count = count + 1;
				}
			}
		}
		float result = count / chLength;
		// 当乱码的长度 大于总长度的40%时，则返回true，否则返回false。
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}

	}

	
	
	/**
	 * 判断是否为中文字或英文字母。
	 * 
	 * @param c
	 * 
	 * @return 是则返回true 不是则返回false
	 * 
	 * @author Perry
	 */
	public static boolean isChineseOrLetter(char c) {
		int charCode = c;

		if (c == '！' || c == '￥' || c == '…' && c == '（' || c == '）' || c == '—' || c == '、' || c == '；' || c == '：' || c == '？' || c == '。' || c == '》' || c == '《' || c == '，') {
			return false;
		}
		// ａ~ｚ 双字节字母
		if (charCode >= 65345 && charCode <= 65370) {
			return false;
		} else {
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				return true;
			} else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				return true;
			}
		}
		return false;
	}

	// 时间格式化
	public static String timeFormate(int seconds) {
		if (seconds <= 0) {
			return "00:00";
		}
		seconds = seconds / 1000;
		int hour = seconds / 3600;
		seconds = seconds % 3600;
		int minute = seconds / 60;
		seconds = seconds % 60;
		if (hour <= 0) {
			return intToStrFormate(minute) + ":" + intToStrFormate(seconds);
		} else {
			return intToStrFormate(hour) + ":" + intToStrFormate(minute) + ":" + intToStrFormate(seconds);
		}
	}

	/**
	 * 时间字符串格式化为毫秒
	 * 
	 * @author Erica
	 * */
	public static long timeStringTosecond(String _time) {
		if (_time.indexOf(":") == -1) {
			return 0;
		}
		String fen = _time.substring(0, _time.indexOf(":"));
		int fen_s = Integer.parseInt(fen);
		String sec = _time.substring(_time.indexOf(":") + 1, _time.length());
		int sec_s = Integer.parseInt(sec);
		long millisecond = (fen_s * 60 + sec_s) * 1000;

		return millisecond;

	}

	// 对时间进行处理.返回都 以SS形式.位数不够用0填充

	private static String intToStrFormate(int time) {
		if (time >= 0 && time <= 9) {
			return "0" + time;
		} else {
			return time + "";

		}
	}

	/**
	 * 获取屏幕分辨率
	 * 
	 * @author Erica
	 * */
	public static int[] readWindowMetrics(WindowManager _windowManager) {
		WindowManager manager = _windowManager;
		int hight = manager.getDefaultDisplay().getHeight();
		// 屏幕的高度
		int width = manager.getDefaultDisplay().getWidth();
		int screen_wh[] = new int[2];
		screen_wh[0] = hight;
		screen_wh[1] = width;
		return screen_wh;
	}

	public static int [] getSreenWidth_Height(Context context){
		int [] wh = new int[2];
		if(context!=null){
			WindowManager _windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
			wh = readWindowMetrics(_windowManager);
		}
		return wh;
	}
	
	/**
	 * dip2px
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 判断一个字符串的首字母是否是中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinaLetter(String str) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		char c = str.charAt(0);
		str = new String(new char[] { c });
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}



	// 获取imei串码信息
	public static String getIMEI(Context context) {
		String imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		return imei;
	}



	/**
	 * 动态设置listView高度
	 * 
	 * @author Erica
	 * */
	public static void setListViewHeightBasedOnChildren(ListView listView, boolean isShowFooter, boolean isShowHeader) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int size = 0;
		if (!isShowFooter) {
			size = listView.getCount() - 1;
		} else {
			if (isShowHeader)
				size = listView.getCount() + 2;
			else
				size = listView.getCount();
		}
		for (int i = 0; i < size; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	

	

	
	

	/**
	 * 移动方法
	 * 
	 * @param v
	 *            需要移动的View
	 * @param startX
	 *            起始x坐标
	 * @param toX
	 *            终止x坐标
	 * @param startY
	 *            起始y坐标
	 * @param toY
	 *            终止y坐标
	 */
	public static void moveFrontBg(View v, int startX, int toX, int startY, int toY) {
		TranslateAnimation anim = new TranslateAnimation(startX, toX, startY, toY);
		anim.setDuration(200);
		anim.setFillAfter(true);
		v.startAnimation(anim);
	}

	

	

	

	


	public static String fileSizeFormate(long size) {
		if (size <= 0) {
			return "0kB";
		}
		float kb = (float) size / 1024;
		if (kb < 1024) {
			return floatToString(kb) + "KB";
		} else {
			kb = kb / 1024;
			return floatToString(kb) + "MB";
		}
	}

	/**
	 * 公用的toast
	 */
	private static Toast toast;

	/**
	 * 公用的Toast 方法
	 * 
	 * @param context
	 * @param title
	 * @param time
	 */
	public static void showToast(Context context, int title, int time) {
		if (toast == null) {
			toast = Toast.makeText(context, title, time);
			toast.show();
		} else {
			toast.setText(title);
			toast.setDuration(time);
			toast.show();
		}
	}

	/**
	 * 公用的Toast 方法
	 * 
	 * @param context
	 * @param title
	 * @param time
	 */
	public static void showToast(Context context, String title, int time) {
		if (toast == null) {
			toast = Toast.makeText(context, title, time);
			toast.show();
		} else {
			toast.setText(title);
			toast.setDuration(time);
			toast.show();
		}
	}

	/**
	 * 当前SD卡是否可用
	 * 
	 * @return true 则可用；false 不可以。
	 * @author Perry
	 */
	public static boolean isSDCardAvailable() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	/**
	 * 将float转成字符串，只保留最多两位小数的
	 * 
	 * @param size
	 * @return
	 */
	public static String floatToString(float size) {
		String str = "" + size;
		int index = str.indexOf(".");
		if (index > 0 && index + 3 < str.length()) {
			str = str.substring(0, index + 3);
		}
		return str;
	}

	/**
	 * 获取文件的总长度，断点续传时，总长度包含在Content-Ranges中，如Content-Ranges: bytes 0-499/1234
	 * 
	 * @return
	 */
	public static long getFileTotalSize(String contentRanges, String contentLength) {
		if (contentRanges != null) {
			int pos = contentRanges.lastIndexOf("/");
			if (pos > 0) {
				return Long.parseLong(contentRanges.substring(pos + 1));
			}
		} else if (contentLength != null) {
			return Long.parseLong(contentLength);
		}
		return 0;
	}

	

	/**
	 * 计算字符串长度并适当截取
	 * 
	 * @author Erica
	 * @param _dtr
	 *            String 需要操作的字符串
	 * @param size
	 *            int 需要处理文字的大小
	 * @param lin
	 *            int 最大显示行数
	 * @param width
	 *            int 最大显示宽度
	 * */
	public static String mestrStr(String _dtr, int size, int lin, int width) {
		if (_dtr == null || _dtr.length() == 0)
			return "";
		// System.out.println(_dtr);
		// width = 200;
		Paint temp = new Paint();
		temp.setTextSize(size);
		String now_str;
		float len = temp.measureText(_dtr);
		if (width * lin <= len) {
			float f = len - (width * lin);
			float f_t = f / len;
			now_str = _dtr.substring(0, (int) (_dtr.length() * (1 - f_t)));
			now_str = now_str.substring(0, now_str.length() - 3);
			now_str = now_str + "...";
		} else {
			now_str = _dtr;
		}
		// System.out.println(now_str);
		return now_str;
	}

	

	
	/**
	 * 拼接Sql字符串
	 * 
	 * @param sql
	 *            String 原始字符串
	 * @param args
	 *            String[] 占位符
	 * @return 拼接好的sql
	 * @note 增加 10.8
	 * */
	public static String putArgsIntoSqlString(String sql, String[] args) {
		String sql_c = "";
		sql_c = sql;
		boolean isHaveArgs = (sql_c.indexOf("?") != -1);
		if (!isHaveArgs)
			return "";
		for (int i = 0; i < args.length; i++) {
			int temp = sql_c.indexOf("?");
			if (temp == -1)
				break;
			String begin = sql_c.substring(0, temp);
			String end = sql_c.substring(temp + 1, sql_c.length());
			sql_c = begin + "'" + args[i] + "'" + end;
		}
		return sql_c;
	}

	



	/**
	 * 判断当前客户端是否在前端运行
	 * 
	 * @author DK
	 * @return
	 */
	public static boolean isAppInfront1(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = am.getRunningTasks(1).get(0).topActivity.getPackageName();
		if ("com.easou.music".equals(packageName)) {
			return true;
		}
		return false;
	}

	/**
	 * 显示键盘
	 * 
	 * @param context
	 * @param view
	 */
	public static void showInputMethod(Context context, View view) {
		InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		im.showSoftInput(view, 0);
	}

	/**
	 * 隐藏键盘
	 * 
	 * @param context
	 * @param view
	 */
	public static void hideInputMethod(Context context, View view) {
		if (context == null || view == null) {
			return;
		}
		InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	





//	public static String getLocalCache() {
//		String root = getRootCache();
//		if (root == null) {
//			return null;
//		}
//		File rootFile = new File(root);
//		if (rootFile.isDirectory() || rootFile.mkdirs()) {
//			return root;
//		}
//		return null;
//	}

//	private static String getRootCache() {
//		String rootDir = null;
//		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//			// SD-card available
//			rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + Easou.newInstance().getPackageName();
//		}
//		return rootDir;
//	}

	/**
	 * 获取sdCard容量
	 * 
	 * @return
	 */
	public static long getSDAllSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 获取所有数据块数
		long allBlocks = sf.getBlockCount();
		// 返回SD卡大小
		// return allBlocks * blockSize; //单位Byte
		// return (allBlocks * blockSize)/1024; //单位KB
		return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
	}

	/**
	 * 获取sdcard剩余容量
	 * 
	 * @return
	 */
	public static long getSDFreeSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		// 返回SD卡空闲大小
		// return freeBlocks * blockSize; //单位Byte
		// return (freeBlocks * blockSize)/1024; //单位KB
		return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
	}
	
	public static int px2dip(Context context, float pxValue){ 
         final float scale = context.getResources().getDisplayMetrics().density; 
         return (int)(pxValue / scale + 0.5f); 
	} 
	
	public static float getSize(Context context,float sourceSize){
		float size = 0;
		if(context ==null)
			return size;
		float desinWidth = 760;
		float width = context.getResources().getDisplayMetrics().widthPixels;
		float height = context.getResources().getDisplayMetrics().heightPixels;
		if(width >height){
			width = height;
		}
		size = width*sourceSize/desinWidth;
		return size;
	}
	
	public static int getIntegerSize(Context context,float sourceSize){
		return (int) (getSize(context, sourceSize)+0.5f);
	}
	
	/***
	 * 获取控件在屏幕中的xy
	 * @param view
	 * @return
	 */
	public static int [] getViewLocation(View view){
		int [] positions = null;
		if(view!=null){
			positions = new int[2];
			view.getLocationOnScreen(positions);
		}
		return positions;
	}
	
	
}
