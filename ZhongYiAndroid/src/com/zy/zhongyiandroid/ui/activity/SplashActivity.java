package com.zy.zhongyiandroid.ui.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpStatus;

import com.zy.zhongyiandroid.R;

import android.app.DownloadManager.Request;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

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
