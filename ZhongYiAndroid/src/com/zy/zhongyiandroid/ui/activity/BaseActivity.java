package com.zy.zhongyiandroid.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;


import com.encore.libs.swipeback.SwipeBackActivity;
import com.encore.libs.swipeback.SwipeBackLayout;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.ui.widget.Header;
import com.zy.zhongyiandroid.ui.widget.LoadingInfo;

/**
 * @author Encore.liang
 */
public class BaseActivity  extends SwipeBackActivity {
	public static final String RECEVICE_EXIT_APP = "action.exit.app";
	protected SwipeBackLayout mSwipeBackLayout;
	private static final int VIBRATE_DURATION = 20;

	// head view
	public Header mHeader;
	// loading动画
	private Animation mProgressAnimation;
	
	public LoadingInfo mLoadingInfo; 
	
	public FinishBroadcastReceiver mFinishBroadcastReceiver = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
		
		 mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
	            @Override
	            public void onScrollStateChange(int state, float scrollPercent) {

	            }

	            @Override
	            public void onEdgeTouch(int edgeFlag) {
	                vibrate(VIBRATE_DURATION);
	            }

	            @Override
	            public void onScrollOverThreshold() {
	                vibrate(VIBRATE_DURATION);
	            }
	        });
	}
	

	public Header getHeadView() {
		return mHeader;
	}

	public Animation getProgressAnimation() {
		if (mProgressAnimation == null) {
			mProgressAnimation = AnimationUtils.loadAnimation(this, R.anim.image_progress);
			mProgressAnimation.setInterpolator(new LinearInterpolator());
		}
		return mProgressAnimation;
	}

	/**
	 * 显示没有数据暂时的view
	 * 
	 * @param parentView
	 * @param containerView
	 */
	public void setNotDataVisibility(View parentView, View containerView) {
		if (parentView == null || containerView == null) {
			return;
		}
		mLoadingInfo = (LoadingInfo) parentView.findViewById(R.id.loadingInfo);
		mLoadingInfo.setVisibility(View.VISIBLE);
		mLoadingInfo.setNotDataVisible(View.VISIBLE);
		containerView.setVisibility(View.GONE);
	}


	public void startActivity(Class classz) {
		Intent intent = new Intent(this, classz);
		startActivity(intent);
	}
	public void startActivityForResult(Class classz) {
		Intent intent = new Intent(this, classz);
		startActivityForResult(intent,0);
	}
	public class OnBackClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private void vibrate(long duration) {
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = {
	                0, duration
		};
	        vibrator.vibrate(pattern, -1);
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		overridePendingTransition(R.anim.activity_left_in,R.anim.activity_right_out);
		if(mFinishBroadcastReceiver!=null)
			unregisterReceiver(mFinishBroadcastReceiver);
	}

	public Handler mHandler = new Handler();
	public class FinishBroadcastReceiver extends BroadcastReceiver{
		
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};
}
