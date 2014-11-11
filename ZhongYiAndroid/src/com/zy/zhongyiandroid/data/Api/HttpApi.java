package com.zy.zhongyiandroid.data.Api;

import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.encore.libs.http.Request;
import com.encore.libs.json.JsonParser;
import com.zy.zhongyiandroid.data.bean.BaseCategory;
import com.zy.zhongyiandroid.data.bean.Item;
import com.zy.zhongyiandroid.data.bean.Message;
import com.zy.zhongyiandroid.data.bean.MessageDetail;
import com.zy.zhongyiandroid.data.bean.Order;
import com.zy.zhongyiandroid.data.bean.OrderDelete;
import com.zy.zhongyiandroid.data.bean.OrderPost;
import com.zy.zhongyiandroid.data.bean.Region;
import com.zy.zhongyiandroid.data.bean.Store;
import com.zy.zhongyiandroid.data.bean.SubCategory;
import com.zy.zhongyiandroid.data.bean.UserInfo;

public class HttpApi {
	/**
	 * 得到消息列表
	 * 
	 * @param context
	 * @param onRequestListener
	 */
	public static void getMessages(Context context, int pageNum, int pageSize,
			OnRequestListener onRequestListener) {
		Request request = new Request(
				ServerUrl.getMessageUrl(pageNum, pageSize));
		request.setParser(new MyJsonParser(Message.class, true));
		request.setOnRequestListener(onRequestListener);
		HttpConnectManager.getInstance(context).doGet(request);
	}

	public static void getMessageDetail(Context context, int messageId,
			OnRequestListener onRequestListener) {
		Request request = new Request(ServerUrl.getMessageDetailUrl(messageId));
		request.setParser(new JsonParser(MessageDetail.class, false));
		request.setOnRequestListener(onRequestListener);
		HttpConnectManager.getInstance(context).doGet(request);
	}

	public static void getMainCategory(Context context, int pageNum,
			int pageSize, OnRequestListener onRequestListener) {
		Request request = new Request(ServerUrl.getMainCategoryUrl(pageNum,
				pageSize));
		request.setParser(new JsonParser(BaseCategory.class, true));
		request.setOnRequestListener(onRequestListener);
		HttpConnectManager.getInstance(context).doGet(request);
	}

	public static void getSubCategory(Context context, String groupId,
			int pageNum, int pageSize, OnRequestListener onRequestListener) {
		Request request = new Request(ServerUrl.getSubCategoryUrl(groupId,
				pageNum, pageSize));
		request.setParser(new JsonParser(SubCategory.class, true));
		request.setOnRequestListener(onRequestListener);
		HttpConnectManager.getInstance(context).doGet(request);
	}

	public static void getItemList(Context context, int pageNum, int pageSize,
			String subCategory, OnRequestListener onRequestListener) {
		Request request = new Request(ServerUrl.getItemListUrl(pageNum,
				pageSize, subCategory));
		request.setParser(new MyJsonParser(Item.class, true));
		request.setOnRequestListener(onRequestListener);
		HttpConnectManager.getInstance(context).doGet(request);
	}

	public static void getRegion(Context context,
			OnRequestListener onRequestListener) {
		Request request = new Request(ServerUrl.getResionUrl());
		request.setParser(new JsonParser(Region.class, true));
		request.setOnRequestListener(onRequestListener);
		HttpConnectManager.getInstance(context).doGet(request);
	}

	public static void getStores(Context context, int pageNum, int pageSize,
			int regionId, String keyWord, OnRequestListener onRequestListener) {
		Request request = new Request(ServerUrl.getStoreUrl(pageNum, pageSize,
				regionId, keyWord));
		request.setParser(new MyJsonParser(Store.class, true));
		request.setOnRequestListener(onRequestListener);
		HttpConnectManager.getInstance(context).doGet(request);
	}

	public static void getLogin(Context context, String loginName,String password,
			OnRequestListener onRequestListener) {
		Request request = new Request(ServerUrl.getLoginUrl(loginName, password));
		request.setParser(new UserJsonParser(UserInfo.class, false));
		request.setOnRequestListener(onRequestListener);
		HttpConnectManager.getInstance(context).doGet(request);
	}

	// 预约
	public static void getOrder(Context context, int userId, int pageNum,
			int pageSize, OnRequestListener onRequestListener) {
		Request request = new Request(ServerUrl.getOrderUrl(userId, pageNum,
				pageSize));
		request.setParser(new MyJsonParser(Order.class, true));
		request.setOnRequestListener(onRequestListener);
		HttpConnectManager.getInstance(context).doGet(request);
	}
	public static void OrderDelete(Context context, int id, OnRequestListener onRequestListener) {
		Request request = new Request(ServerUrl.deleteOrderUrl(id));	
		request.setParser(new JsonParser(OrderDelete.class, false));
		request.setOnRequestListener(onRequestListener);
		HttpConnectManager.getInstance(context).doGet(request);
	}
	
	public static void order(Context context, Order order,OnRequestListener onRequestListener) {
		Request request = new Request(ServerUrl.URL_ORDER_INSERT);
		request.setParser(new JsonParser(OrderPost.class, false));
		request.setOnRequestListener(onRequestListener);
		Map<String, String> postParam = new HashMap<String, String>();
		postParam.put("userId",order.getUserId()+"");
		postParam.put("shopId",order.getShopId()+"");
		postParam.put("arrageDateTime",order.getArrageDateTime());
		postParam.put("appellation",order.getAppellation());
		postParam.put("Email", order.getEmail());
		postParam.put("phone", order.getPhone());
		postParam.put("status", order.getStatus());
		postParam.put("Remarks", order.getRemarks());

		HttpConnectManager.getInstance(context).doPost(request,postParam);
	}
}
