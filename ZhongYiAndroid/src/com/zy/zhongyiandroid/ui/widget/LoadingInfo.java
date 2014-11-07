package com.zy.zhongyiandroid.ui.widget;

import com.zy.zhongyiandroid.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class LoadingInfo extends RelativeLayout {
	private View mLoadingView;
	private View mNotNetView;
	private View mNotDataView;
//	private TextView mTvNotData;
	
	private ImageButton mBtnRefresh;
	
	private ImageView mImgProgress;
	private Animation mProgressAnimation;
	
	private OnRefreshClickListener mListener;
	
	public void setOnRefurbishListener(OnRefreshClickListener listener){
		mListener = listener;
	}

	public LoadingInfo(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialise(context);
	}

	public LoadingInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialise(context);
	}

	public LoadingInfo(Context context) {
		super(context);
		initialise(context);
	}

	private void initialise(final Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.widget_loading_info, null);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(layoutParams);
		mLoadingView = view.findViewById(R.id.loadingView);
		mNotNetView = view.findViewById(R.id.notNetWork);
		mNotDataView = view.findViewById(R.id.notDataView);
		mBtnRefresh = (ImageButton) view.findViewById(R.id.btnRefresh);
		mImgProgress = (ImageView) view.findViewById(R.id.imgProgress);
//		mTvNotData = (TextView) view.findViewById(R.id.tvNotData);
		
		mBtnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener!=null){
					mNotNetView.setVisibility(View.GONE);
					setLoadingViewVisible(View.VISIBLE);
					mListener.onClick(v);
				}
					
			}
		});
		
		this.addView(view);
	}
	public Animation getProgressAnimation() {
		if (mProgressAnimation == null) {
			mProgressAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading_image_progress);
			mProgressAnimation.setInterpolator(new LinearInterpolator());
		}
		return mProgressAnimation;
	}
	
	public void setLoadingViewVisible(int VISIBLE){
		mLoadingView.setVisibility(VISIBLE);
		mNotNetView.setVisibility(View.GONE);
		mNotDataView.setVisibility(View.GONE);
		if(VISIBLE == View.VISIBLE){
			mImgProgress.setAnimation(getProgressAnimation());
		}
	}
//	public void setNotDataText(String text){
//		mTvNotData.setText(text);
//	}
//	public void setNotDataText(int res){
//		mTvNotData.setText(res);
//	}
	public void setNotNetVisible(int VISIBLE){
		mNotNetView.setVisibility(VISIBLE);
		mLoadingView.setVisibility(View.GONE);
		mNotDataView.setVisibility(View.GONE);
	}
	public void setNotDataVisible(int VISIBLE){
		mNotNetView.setVisibility(View.GONE);
		mLoadingView.setVisibility(View.GONE);
		mNotDataView.setVisibility(VISIBLE);
	}
	

	public interface OnRefreshClickListener{
		void onClick(View v);
	}
}
