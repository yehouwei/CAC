package com.zy.zhongyiandroid.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zy.zhongyiandroid.Constant;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;


public class IntentShareUtil {
	public final static String PACKAGE_WEIBO_SINA = "com.sina.weibo";
	public final static String PACKAGE_WEIBO_QQ = "com.tencent.WBlog";
	public final static String PACKAGE_QZONE  = "com.qzone";
	
	/**
	 * 判断是否安装腾讯、新浪等指定的分享应用
	 * @param packageName 应用的包名
	 */
	public static boolean checkInstallation(Context context,String packageName){
		PackageManager packageManager = context.getPackageManager();  
	    try {  
	        PackageInfo pInfo = packageManager.getPackageInfo(packageName,  
	                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT); 
	        //判断是否获取到了对应的包名信息 
	        if(pInfo!=null){  
	            return true;
	        }  
	    } catch (NameNotFoundException e) {  
	        e.printStackTrace();  
	    }  
	    return false;
	}
	public static void shareIntent(Context context,String packageName,String content,String title){
		Intent intent=new Intent(Intent.ACTION_SEND);   
        intent.setType("text/plain");  
        intent.setPackage(packageName);
        
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");   
        intent.putExtra(Intent.EXTRA_TEXT, content);  
        intent.putExtra(Intent.EXTRA_TITLE, title);  
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
        context.startActivity(Intent.createChooser(intent, title));   
	}
	public static void shareToFriend(Context context,Bitmap bm,String content,String title) { 
		if(!isAvilible(context, "com.tencent.mm"))
			return;
        Intent intent = new Intent();  
        ComponentName comp = new ComponentName("com.tencent.mm",  
                        "com.tencent.mm.ui.tools.ShareImgUI");  
        intent.setComponent(comp);  
        intent.setAction("android.intent.action.SEND");  
        intent.setType("image/*");  
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        intent.putExtra(Intent.EXTRA_TEXT, content);  
        intent.putExtra(Intent.EXTRA_TITLE, title);  
        //intent.setFlags(0x3000001);  
        if(bm!=null){
        	intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(saveFile(bm)));  
        }
        context.startActivity(intent);  
	}  
	public static void shareToTimeLine(Context context,Bitmap bm,String content,String title) {  
		if(!isAvilible(context, "com.tencent.mm"))
			return;
        Intent intent = new Intent();  
        ComponentName comp = new ComponentName("com.tencent.mm",  
                        "com.tencent.mm.ui.tools.ShareToTimeLineUI");  
        intent.setComponent(comp);  
        intent.setAction("android.intent.action.SEND");  
        intent.setType("image/*");  
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        intent.putExtra(Intent.EXTRA_TEXT, content);  
        intent.putExtra(Intent.EXTRA_TITLE, title);  
        //intent.setFlags(0x3000001);  
        if(bm!=null){
        	intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(saveFile(bm)));  
        }
        	
        context.startActivity(intent);  
	} 
	public static File saveFile(Bitmap bm) { 
	    String path = Constant.SdcardPath.CACHE_SAVEPATH + "/" +"a.jpg";     
   
        File myCaptureFile = new File(path);  
       
        try {
        	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
			bos.flush();
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
        return myCaptureFile;
    }  
	private static boolean isAvilible(Context context, String packageName){ 
        final PackageManager packageManager = context.getPackageManager();//获取packagemanager 
        List< PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息 
        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名 
       //从pinfo中将包名字逐一取出，压入pName list中 
            if(pinfo != null){ 
            for(int i = 0; i < pinfo.size(); i++){ 
                String pn = pinfo.get(i).packageName; 
                pName.add(pn); 
            } 
        } 
        return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE 
  } 
	
}
