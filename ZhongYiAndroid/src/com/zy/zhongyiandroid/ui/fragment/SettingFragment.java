package com.zy.zhongyiandroid.ui.fragment;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.shared.UserData;
import com.zy.zhongyiandroid.ui.activity.MyBonusActivity;
import com.zy.zhongyiandroid.ui.activity.MyIntroduceActivity;
import com.zy.zhongyiandroid.ui.activity.MyOrderActivity;
import com.zy.zhongyiandroid.ui.dialog.UserLoginDialog;
import com.zy.zhongyiandroid.ui.dialog.UserLoginDialog.OnLoginDialogClickListener;

import com.zy.zhongyiandroid.ui.widget.Header;

/**
 * 宝宝
 * 
 * @author Seven
 * 
 */
public class SettingFragment extends BaseFragment {

	public final static String TAG = "UserFragment";

	// View mReBabyInfo;
	// View mReSetting;
	View mUpdate;
	View mMyBonus;
	View mIntroduce;
	View mMyOrder;
	/*
	 * ImageView mImgBabyIcon; TextView mTvBabyName; TextView mTvBabyBirth;
	 * 
	 * TextView mTvBabySign; TextView mTvBabyZodica; ImageView mImgSex;
	 */

	View mReDownload;

	UserData userData = new UserData();




	DisplayImageOptions options;

	ImageLoader mImageLoader = ImageLoader.getInstance();
	
	private  Boolean IsLogin=false;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, null);

		// 初始化ui\
		initUI(view);
		initHeader(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// initData();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// getActivity().unregisterReceiver(mBroadcastReceiver);
	}

	/**
	 * 初始化UI
	 */
	public void initUI(View view) {
		mUpdate = view.findViewById(R.id.Update);
		mIntroduce = view.findViewById(R.id.MyIntroduce);
		mMyBonus = view.findViewById(R.id.myBonus);
		// mReFeedback = view.findViewById(R.id.reFeedback);
		mMyOrder = view.findViewById(R.id.myOrder);
		// mReSetting = view.findViewById(R.id.reSetting);
		// mReBabyInfo.setOnClickListener(mOnClickListener);
		mUpdate.setOnClickListener(mOnClickListener);
		// mReSetting.setOnClickListener(mOnClickListener);
		mIntroduce.setOnClickListener(mOnClickListener);
		mMyBonus.setOnClickListener(mOnClickListener);
		// mReFeedback.setOnClickListener(mOnClickListener);
		mMyOrder.setOnClickListener(mOnClickListener);
	}

	/**
	 * 初始化header
	 */
	public void initHeader(View view) {
		Header mHeader = (Header) view.findViewById(R.id.header);
		if (mHeader != null) {
			mHeader.setTitle(getActivity().getString(R.string.tab_personal));
			mHeader.setIntroduceBtn("中艺", new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					MyIntroduceActivity.startActivity(getActivity(),
							getActivity().getString(R.string.tab_personal));
				}
			});
		}
	}

	/*
	 * public void initData(){ mUser = UserUtil.getInstance().getUser();
	 * if(mUser!=null&&mUser.getId()!=null){
	 * mTvBabyName.setText(mUser.getName());
	 * mImageLoader.displayImage(mUser.getHeadImg(), mImgBabyIcon, options);
	 * if(mUser.getBirthDate()!=null&&mUser.getType()!=User.BIRTH_NO){
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日"); String
	 * nowTime = sdf.format(mUser.getBirthDate());
	 * mTvBabyBirth.setText("出生："+nowTime);
	 * 
	 * 
	 * mTvBabySign.setVisibility(View.VISIBLE);
	 * mTvBabyZodica.setVisibility(View.VISIBLE);
	 * if(mUser.getType()==User.BIRTH_ING){//备孕
	 * mImgSex.setVisibility(View.GONE); LinearLayout.LayoutParams mLayoutParams
	 * = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	 * LayoutParams.WRAP_CONTENT); mLayoutParams.leftMargin = 0;
	 * mTvBabySign.setLayoutParams(mLayoutParams); }else{
	 * mImgSex.setVisibility(View.VISIBLE); LinearLayout.LayoutParams
	 * mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	 * LayoutParams.WRAP_CONTENT); mLayoutParams.leftMargin = 5;
	 * mTvBabySign.setLayoutParams(mLayoutParams); if(mUser.getSex()==
	 * User.SEX_BOY){ mImgSex.setBackgroundResource(R.drawable.icon_boy); }else
	 * if(mUser.getSex()== User.SEX_GIRL){
	 * mImgSex.setBackgroundResource(R.drawable.icon_gird); } }
	 * 
	 * }else{ mTvBabyBirth.setText(mUser.getBabyTitle());
	 * mImgSex.setVisibility(View.GONE); mTvBabySign.setVisibility(View.GONE);
	 * mTvBabyZodica.setVisibility(View.GONE); }
	 * 
	 * 
	 * String strSign = mUser.getSign(); String strZodica = mUser.getZodica();
	 * if(strSign!=null&&strSign.length()>0){
	 * mTvBabySign.setText("生肖:"+strZodica); }
	 * if(strZodica!=null&&strZodica.length()>0){
	 * mTvBabyZodica.setText("星座:"+strSign); }
	 * 
	 * 
	 * } }
	 */

	OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			/*
			 * case R.id.reBabyInfo:
			 * UserInfoActivity.startActivity(getActivity(),false); break;
			 */
			case R.id.myBonus:
				if (!userData.getIsShowLogin()) {
					UserLoginDialog dialog = new UserLoginDialog(getActivity(), R.style.MyDialog);
					dialog.setOnLoginDialogClickListener(new OnLoginDialogClickListener() {
						
						@Override
						public void startActivity() {
							// TODO Auto-generated method stub
							MyOrderActivity.startActivity(getActivity(), getActivity()
									.getString(R.string.tab_personal));
						}
						
					});
					dialog.showDialog();
				} else {
					MyBonusActivity.startActivity(getActivity(), getActivity()
							.getString(R.string.tab_personal));
				}
				break;
			/*
			 * case R.id.reSetting:
			 * SettingActivity.startActivity(getActivity()); break;
			 */
			case R.id.myOrder:
				if (!userData.getIsShowLogin()) {
					UserLoginDialog dialog = new UserLoginDialog(getActivity(), R.style.MyDialog);
					dialog.setOnLoginDialogClickListener(new OnLoginDialogClickListener() {
						
						@Override
						public void startActivity() {
							// TODO Auto-generated method stub
							MyOrderActivity.startActivity(getActivity(), getActivity()
									.getString(R.string.tab_personal));
						}
						
					});
					dialog.showDialog();
				} else {
					MyOrderActivity.startActivity(getActivity(), getActivity()
							.getString(R.string.tab_personal));
				}
				break;
			case R.id.MyIntroduce:
				MyIntroduceActivity.startActivity(getActivity(), getActivity()
						.getString(R.string.tab_personal));
				break;
			/*
			 * case R.id.reFeedback:
			 * FeedbackActivity.startActivity(getActivity()); break;
			 */
			case R.id.Update:
				/* UpdateVersionAcitvity.startActivity(getActivity()); */
				break;
			default:
				break;
			}
		}
	};

	/*
	 * BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
	 * 
	 * @Override public void onReceive(Context context, Intent intent) {
	 * initData(); } };
	 */

/*	private void intitdialog() {
		// TODO Auto-generated method stub

		UserLoginDialog dialog = new UserLoginDialog(getActivity(), R.style.MyDialog);
		dialog.setOnLoginDialogClickListener(new OnLoginDialogClickListener() {
			
			@Override
			public void startActivity() {
				// TODO Auto-generated method stub
				
			}
			
		});
		dialog.showDialog();

	}*/
	void toast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}
}
