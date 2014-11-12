package com.zy.zhongyiandroid.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.ui.fragment.BaseFragment;
import com.zy.zhongyiandroid.ui.fragment.LoacationFragment;
import com.zy.zhongyiandroid.ui.fragment.MessageFragment;
import com.zy.zhongyiandroid.ui.fragment.SettingFragment;
import com.zy.zhongyiandroid.ui.fragment.MainCategoryFragment;
import com.zy.zhongyiandroid.ui.widget.MyRadioGroup;

public class MainActivity extends FragmentActivity implements MyRadioGroup.OnCheckedChangeListener {
	//默认状态 未打开过playActivity
	public static final int STATE_PLAY_ACTIVITY_NOMAL = 0;
	//第一次打开标识
	public static final int STATE_PLAY_ACTIVITY_FRIST = 1;
	//已经显示提示
	public static final int STATE_PLAY_ACTIVITY_SHOW_TIPS = 2;
	
	// tabs
	private MyRadioGroup mRgTabs;
	// 最后选中的fragment
	private BaseFragment mLastFragment;
	// 当前tag
	private String mCurrentTag;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化UI
		initUI();
		// 添加第一个fragment
		attachFristFragment();
	}
	public void onResume() {
		super.onResume();
		
	}
	public void onPause() {
		super.onPause();
		
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	
	}
	/**
	 * 初始化UI
	 */
	public void initUI() {
		mRgTabs = (MyRadioGroup) findViewById(R.id.rgTabs);
		mRgTabs.setOnCheckedChangeListener(this);
	}


	/**
	 * 添加初始显示fragment
	 */
	public void attachFristFragment() {
		try {
			String fristTag = R.id.rboInformation + "";
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			BaseFragment newFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(fristTag);
			if (newFragment == null) {
				newFragment = new MessageFragment();
			}
			// 判断是否和最后的fragment不同,做相应操作
			if (newFragment != mLastFragment) {
//				ft.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out);
				if (mLastFragment != null) {
					ft.detach(mLastFragment);
				}
				ft.add(R.id.container, newFragment, fristTag);
				mLastFragment = newFragment;
			}
			ft.commit();
			getSupportFragmentManager().executePendingTransactions();
		} catch (Exception e) {
			// TODO: handle exception
			finish();
		}
	}

	/**
 	 */
	
	@Override
	public void onCheckedChanged(MyRadioGroup group, int checkedId) {
		// //点击了相同的tab,不做任何操作
		// if(mCurrentTag != null && !mCurrentTag.equals("") &&
		// mCurrentTag.equals(checkedId+"")){
		// return;
		// }

		mCurrentTag = checkedId + "";

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		BaseFragment newFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(checkedId + "");

		
		
		
		boolean isNew = false;
		if (newFragment == null) {
			isNew = true;
			switch (checkedId) {
			case R.id.rboInformation:
				newFragment = new MessageFragment();
				
				break;
			case R.id.rboBrands:

				newFragment = new LoacationFragment();
				break;
			case R.id.rboLoacation:
				newFragment = new MainCategoryFragment();
				break;

			case R.id.rboPersonal:
				newFragment = new SettingFragment();
				break;
			}
		}

		if (newFragment == null) {
			Toast.makeText(this, "No tab known for tag " + checkedId, Toast.LENGTH_SHORT).show();
			return;
		}

		// 判断是否和最后的fragment不同,做相应操作
		if (newFragment != mLastFragment) {
//			ft.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out);
//			 ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//设置动画效果
			if (mLastFragment != null) {
				ft.detach(mLastFragment);
			}
			if (isNew) {
				ft.add(R.id.container, newFragment, mCurrentTag);
			} else {
				ft.attach(newFragment);
			}
			mLastFragment = newFragment;
		}
		ft.commit();
		getSupportFragmentManager().executePendingTransactions();
	}

	@Override
	public void onBackPressed() {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(mLastFragment != null){ //处理localFragment的back事件
//				if(mLastFragment instanceof LocalContainerFragment){
//					LocalContainerFragment lcFragment = (LocalContainerFragment) mLastFragment;
//					BaseFragment baseFragment = (BaseFragment) lcFragment.getStackFragment(LocalContainerFragment.TAG_LOCAL_FRAGMENT);
//					if(baseFragment instanceof LocalFragment){
//						LocalFragment localFragment = (LocalFragment) baseFragment;
//						boolean flag = localFragment.onBackPressed(keyCode);
//						if(!flag){
//							return flag;
//						}
//					}
//				}
			}
			
			// If the fragment exists and has some back-stack entry
			if (mLastFragment != null && mLastFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
				// Get the fragment fragment manager - and pop the backstack
				mLastFragment.getChildFragmentManager().popBackStack();
				//回调onBack监听
				if(mLastFragment.getOnBackStatckListener() != null)
					mLastFragment.getOnBackStatckListener().onBack();
				return false;
			}
			// Else, nothing in the direct fragment back stack
			else {
				// Let super handle the back press
				if (!mIsExitApp) {
					mIsExitApp = true;
					Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
					mHandler.sendEmptyMessageDelayed(0, 2000);
					return false;
				}else{
					finish();
					
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	// 是否退出app
	public boolean mIsExitApp = false;
	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			mIsExitApp = false;
		}
	};

	/**
	 * 隐藏底部tabBar
	 */
	public void hiddenTabBar() {
		mRgTabs.setVisibility(View.GONE);
	}

	/**
	 * 显示tabBar
	 */
	public void showTabBar() {
		mRgTabs.setVisibility(View.VISIBLE);
	}

//	public void download() {
//		
//
//		if (CommonUtils.isHasNetwork(this)) {
//			String id = "1"; // id
//			String downLoadUrl = "http://pnp.565656.com//2014/02/08/63527450608767578103890955.m4a"; // 下载url
//			String song = "test"; // 歌曲
//			String singer = "test"; // 歌手
//			String gid = "1"; // gid
//
//
//			boolean result = hasSongInLocal(id);; // 检查是否存在歌曲
//			if (result) {
//				// 本地已存在
//				Toast.makeText(this, "该歌曲已下载", Toast.LENGTH_SHORT).show();
//			} else {
//				String fileName = CommonUtils.getFileNameByUrl(downLoadUrl);
//				DownloadFile file = new DownloadFile(id, gid, downLoadUrl, fileName, song, singer);
//
//				file.setCreateTime(System.currentTimeMillis()); // 设置创建文件时间，用于文件的排序
//				file.setFileType(DownloadType.DOWNLOAD_TYPE_MUSIC);
//				file.setDownloadListener(Easou.newInstance());
//
//				if (DownloadService.newInstance().getBinder() != null) {
//					DownloadService.newInstance().getBinder().startDownloadTask(file, true);
//				}
//
//				if (DownloadEngine.getDownloadingFiles() != null) {
//					boolean ishaveall = DownloadEngine.getDownloadingFiles().contains(file);
//
//					if (!ishaveall) {
//						Easou.setNewDownLoadCount(Easou.getNewDownLoadCount() + 1);
////						finish();
//					} else {
//
//						Toast.makeText(this, "您要下载的资源已存在或正在下载", Toast.LENGTH_SHORT).show();
//					}
//				}
//			}
//		} else {
////			Toast.makeText(this, R.string.not_network, Toast.LENGTH_SHORT).show();
//		}
//	}
	
	
	
	public static void startActivity(Context context){
		Intent i = new Intent(context,MainActivity.class);
		context.startActivity(i);
	}
	


	BroadcastReceiver mFinishBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
		
	};
	

}
