package com.zy.zhongyiandroid.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.bean.Message;
import com.zy.zhongyiandroid.data.bean.MessageDetail;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.ui.widget.Header;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MessageDeatailAcitvity extends BaseActivity {
	// Message message;
	DisplayImageOptions options;
	boolean mIsFirstLoad = true;

	private boolean isRequesEnd = true;
	List<MessageDetail> mDetails = new ArrayList<MessageDetail>();
	ImageView mImgMeessage;
	TextView mtvTitle;
	TextView mtvContent;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_message_detail);

		initHeader();
		initUI();

	}

	private void initUI() {
		// TODO Auto-generated method stub
		mImgMeessage = (ImageView) this.findViewById(R.id.imgMessage);
		mtvTitle = (TextView) this.findViewById(R.id.tvMessageTitle);
		mtvContent = (TextView) this.findViewById(R.id.tvContent);
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		request();

	}

	public void request() {
		/*
		 * if (!isRequesEnd) { return; }
		 * 
		 * isRequesEnd = false; // 改变正在请求的标识
		 *//*
			 * if ((mStores == null) || (mStores.size() == 0)) {
			 * setLoadingViewVisible(View.VISIBLE, mListView); }
			 */
		HttpApi.getMessageDetail(this, getIntent().getExtras().getInt("messageId"), mOnRequestListener);
		// 
	}

	Handler mHandler = new Handler();
	public OnRequestListener mOnRequestListener = new OnRequestListener() {

		@Override
		public void onResponse(String url, final int state,
				final Object result, int type) {

			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if ((state == HttpConnectManager.STATE_SUC)
							&& (result != null)) {
						MessageDetail mMessageDetail = null;
						mMessageDetail = (MessageDetail) result;
						mtvTitle.setText(mMessageDetail.getTitle());
						mtvContent.setText(mMessageDetail.getContent());
						ImageLoader mImageLoader = ImageLoader.getInstance();
						mImageLoader.displayImage(mMessageDetail.getImg(),mImgMeessage,options);
						// List<MessageDetail> mDetail
						// =(List<MessageDetail>)result;

						/*
						 * if(mDetail.size()==0){ mtvTitle.setText("1"); }else{
						 */

						/*
						 * if((mDetail != null) && (mDetail.size() > 0)){
						 * ImageLoader mImageLoader = ImageLoader.getInstance();
						 * mImageLoader.displayImage(mDetail.get(0).getImg(),
						 * c; mtvTitle.setText("333"); }
						 */
					}
				}
			});

		}
	};

	private void initHeader() {
		// TODO Auto-generated method stub
		Header mHeader = (Header) this.findViewById(R.id.header);
		if (mHeader != null) {
			mHeader.setTitle(getResources().getString(R.string.message_detail));
			mHeader.setBackBtn(
					getResources().getString(R.string.tab_informantion),
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							finish();
						}
					});
		}
	}

	public static void startActivity(Context c, int messageId) {
		Intent i = new Intent(c, MessageDeatailAcitvity.class);
		i.putExtra("messageId", messageId);

		c.startActivity(i);
	}

}
