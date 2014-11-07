package com.zy.zhongyiandroid.ui.widget;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.ui.adapter.DialogShareAdapter;
import com.zy.zhongyiandroid.ui.adapter.DialogShareAdapter.OnMyClickListener;
import com.zy.zhongyiandroid.utils.IntentShareUtil;
import com.zy.zhongyiandroid.utils.PopWindowUtil;
import com.zy.zhongyiandroid.utils.WeixinUtil;

import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;


public class Header extends RelativeLayout {
	View mViewBack;
	ImageView mImgBack;
	TextView mTvBack;
	TextView mTvTitle;
	ImageButton mBtnRight1;
	ImageButton mBtnRight2;
	TextView mTvRight;
	View mViewRight;
	
	PopupWindow mPopupWindow;

	public Header(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialise(context);
	}

	public Header(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialise(context);
	}

	public Header(Context context) {
		super(context);
		initialise(context);
	}

	private void initialise(final Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.widget_header, null);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		view.setLayoutParams(layoutParams);

		mImgBack = (ImageView) view.findViewById(R.id.imgBack);
		mTvBack =  (TextView) view.findViewById(R.id.tvBack);
		mViewBack = view.findViewById(R.id.linBack);
		mTvTitle = (TextView) view.findViewById(R.id.tvTitle);
		mBtnRight1 = (ImageButton) view.findViewById(R.id.btnRight1);
		mBtnRight2 = (ImageButton) view.findViewById(R.id.btnRight2);
		mTvRight = (TextView) view.findViewById(R.id.tvRight);
		mViewRight = view.findViewById(R.id.liRight);
		mBtnRight1.requestFocusFromTouch();
		mBtnRight2.requestFocusFromTouch();
		this.addView(view);

	}
	/**
	 * 设置左边按钮
	 * 
	 * @param draw
	 * @param onClickListener
	 */
	public void setIntroduceBtn(String str,View.OnClickListener onClickListener) {
		// TODO Auto-generated method stub
		mViewBack.setVisibility(View.VISIBLE);
		mViewBack.setPadding(50, 0, 0, 0);
		mImgBack.setVisibility(View.GONE);
		mTvBack.setText(str);
		mTvBack.setOnClickListener(onClickListener);
	}
	public void setEmptyTittle(String str){
		mTvTitle.setVisibility(View.VISIBLE);
		mTvTitle.setText(str);
	}
	public void setBackBtn(String str, View.OnClickListener onClickListener) {
		mViewBack.setVisibility(View.VISIBLE);
		if(str!=null){
			mTvBack.setText(str);
			mTvBack.setVisibility(View.VISIBLE);
		}else{
			mTvBack.setVisibility(View.GONE);
		}
		mViewBack.setOnClickListener(onClickListener);
	}
	public void setBackBtn(int res, View.OnClickListener onClickListener) {
		mViewBack.setPadding(0, 0, 0, 0);
		mViewBack.setVisibility(View.VISIBLE);
		mViewBack.setBackgroundResource(android.R.color.transparent);
		mImgBack.setImageResource(res);
		mTvBack.setText("");
		mImgBack.setOnClickListener(onClickListener);
	}
	
	public void setBtnRight1(int res,View.OnClickListener onClickListener){
		mBtnRight1.setVisibility(View.VISIBLE);
		mBtnRight1.setBackgroundResource(res);
		mBtnRight1.setOnClickListener(onClickListener);
	}
	public void setBtnRight2(int res,View.OnClickListener onClickListener){
		mBtnRight2.setVisibility(View.VISIBLE);
		mBtnRight2.setBackgroundResource(res);
		mBtnRight2.setOnClickListener(onClickListener);
	}
	public void setTitle(int str){
		mTvTitle.setVisibility(View.VISIBLE);
		mTvTitle.setBackgroundResource(R.drawable.bg_header_tittle);
		mTvTitle.setText(str);
	}
	public void setTitle(String str){
		mTvTitle.setVisibility(View.VISIBLE);
		mTvTitle.setBackgroundResource(R.drawable.bg_header_tittle);
		mTvTitle.setText(str);
	}
	public String getTitle(){
		return mTvTitle.getText().toString();
	}
	public void setRight(String str,View.OnClickListener onClickListener){
		mTvRight.setVisibility(View.VISIBLE);
		mViewRight.setVisibility(View.VISIBLE);
		if(str!=null)
			mTvRight.setText(str);
		if(onClickListener!=null)
			mViewRight.setOnClickListener(onClickListener);
	}
	
	public interface ShareListener{
		/**
		 * 新浪分享
		 */
		public void onSinaClick();
		/**
		 * QQ空间分享
		 */
		public void onOzoneClick();
		/**
		 * 朋友分享
		 */
		public void onFirendClick();
		/**
		 * 朋友圈
		 */
		public void onSendLineClick();
	}
	
	/**
	 * 显示分享
	 */
	public void showShare(final ImageView mImgShaw,final Activity activity,View mLiContent,final String title,final String content,final ShareListener shareListener){
		if(mPopupWindow==null){
			if(mImgShaw!=null)
				mImgShaw.setVisibility(View.VISIBLE);
			PopWindowUtil mBottomPopWindowUtil = new PopWindowUtil();
			View mBottomView = mBottomPopWindowUtil.initView(activity, R.layout.dialog_share);
			final int yPos = activity.getWindow().getDecorView().getHeight();
			GridView gridView = (GridView) mBottomView.findViewById(R.id.gridview);
			TextView mTvCancel = (TextView) mBottomView.findViewById(R.id.tvCancel);
			DialogShareAdapter dialogShareAdapter = new DialogShareAdapter(activity);
			gridView.setAdapter(dialogShareAdapter);
			final String appName = getContext().getResources().getString(R.string.app_name);
			final String shareStr = "我刚刚在"+appName+"客户端分享了【"+title+"】，" + 
					appName +"是为怀孕妈咪及0-6岁宝宝量身定做的专业育儿应用，快来看看吧。" +
					"下载地址：http://mapp.easou.com/parenting/apk/Parenting.apk";
			
			dialogShareAdapter.setOnClickListener(new OnMyClickListener() {
				
				@Override
				public void onClick(int position) {
					mPopupWindow.dismiss();
					switch (position) {
					case 0:
						if(shareListener!=null)
							shareListener.onSinaClick();
						else
							IntentShareUtil.shareIntent(activity, IntentShareUtil.PACKAGE_WEIBO_SINA, shareStr, appName);
						break;
					case 1:
						if(shareListener!=null)
							shareListener.onOzoneClick();
						else
							IntentShareUtil.shareIntent(activity, IntentShareUtil.PACKAGE_QZONE, shareStr, appName);
						break;
					case 2:
						if(shareListener!=null)
							shareListener.onFirendClick();
						else
							WeixinUtil.shareText(activity,appName,shareStr, null, false);
						break;
					case 3:
						if(shareListener!=null)
							shareListener.onSendLineClick();
						else
							WeixinUtil.shareText(activity, appName, shareStr, null, true);
						break;

					default:
						break;
					}
				}
			});
			
			mTvCancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mPopupWindow.dismiss();
				}
			});
			mPopupWindow = mBottomPopWindowUtil.showMenuPopupWindow(mBottomView, null, mLiContent, R.style.PopupAnimationBottom, yPos);
			if(mPopupWindow==null)
				return;
			mPopupWindow.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
					mPopupWindow = null;
					if(mImgShaw!=null)
						mImgShaw.setVisibility(View.GONE);
				}
			});
		}else{
			mPopupWindow.dismiss();
		}	
	}
}
