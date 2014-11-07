package com.zy.zhongyiandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;
/**
 * 微信工具类
 * @author YY
 *
 */
public class WeixinUtil {
	/** 微信id **/
	public static final String APP_ID = "wx5efa4b0010b7f628";
	/** 分享微信内容 **/
	public static void shareText(Context context,String title,String description,Bitmap result,boolean sendTimeline){
		IWXAPI api = WXAPIFactory.createWXAPI(context, APP_ID);
//		int supportAPI = api.getWXAppSupportAPI(); 
		/** 注册微信 **/
		api.registerApp(WeixinUtil.APP_ID); 
		/** 判断微信是否安装 **/
		if(!api.isWXAppInstalled()){
			Toast.makeText(context, "未找到您的微信", Toast.LENGTH_SHORT).show();
			return;
		}
		
		WXWebpageObject textObj = new WXWebpageObject();
		textObj.webpageUrl = "http://mapp.easou.com/parenting/apk/Parenting.apk";
		
		

		WXMediaMessage msg = new WXMediaMessage(textObj);
//		msg.mediaObject = textObj;
		msg.title = title;
		msg.description = description;
		
		 if (result != null) {
             Bitmap thumbBmp = Bitmap.createScaledBitmap(result, 50, 50, true);
             msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
         }

		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.message = msg;
		req.transaction = System.currentTimeMillis() + "";
		req.scene = sendTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		
		/** 判断是否发送成功 **/
		boolean sendFlag = api.sendReq(req);
		if(sendFlag){
//			ToastUtil.show(context, R.string.weixin_tips);
			Toast.makeText(context, "打开微信分享中...", Toast.LENGTH_LONG).show();
		}
		
	}
}
