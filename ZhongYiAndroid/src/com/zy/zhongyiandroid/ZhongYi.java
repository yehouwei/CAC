package com.zy.zhongyiandroid;

import java.io.File;
import java.util.List;


import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zy.zhongyiandroid.data.bean.Store;

import android.app.Application;
import android.content.Context;

public class ZhongYi extends Application {
	private static ZhongYi zhongyiInstance;
	private List<Store> storeList;
	

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

	@Override
	public void onCreate() {
		zhongyiInstance=this;
		init();
		initImageLoader(getApplicationContext());
	}
	
	public static ZhongYi newInstance() {
		if (zhongyiInstance != null)
			return zhongyiInstance;
		return null;
	}
	
	public void init(){
		File rootFile = new File(Constant.SdcardPath.SAVE_ROOTPATH);
		if(!rootFile.exists()){
			rootFile.mkdirs();
		}
		File cacheFile = new File(Constant.SdcardPath.CACHE_SAVEPATH);
		if(!cacheFile.exists()){
			cacheFile.mkdirs();
		}
	}
	//初始化图片加载器
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	

}
