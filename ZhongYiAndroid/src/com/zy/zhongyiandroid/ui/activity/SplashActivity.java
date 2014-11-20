package com.zy.zhongyiandroid.ui.activity;

import com.zy.zhongyiandroid.R;

import android.os.Bundle;
import android.os.Handler;

/**
 * 
 * @author Encore.liang
 * 
 */
public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.activity_splash);

		toMainActivity();
	}

	private Handler mHandler = new Handler();

	/**
	 * 跳转到主页面
	 */
	public void toMainActivity() {

		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				MainActivity.startActivity(SplashActivity.this);
				finish();
			}
		}, 2000);

	}

}
