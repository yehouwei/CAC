package com.zy.zhongyiandroid.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.ui.activity.MainActivity;
import com.zy.zhongyiandroid.ui.widget.Header;
import com.zy.zhongyiandroid.ui.widget.LoadingInfo;
import com.zy.zhongyiandroid.ui.widget.LoadingInfo.OnRefreshClickListener;

/**
 * @author Encore.liang
 */
public class BaseFragment extends Fragment {
	public static final String IMAGE_CACHE_DIR = "images";
	/**
	 * 初始化数据
	 */
	public static final int TAG_INIT_DATA = 0x00;

	// head view
	public Header mHeader = null;
	

	protected Handler mHandler = new Handler();
	//图片加截器
	public ImageLoader imageLoader = ImageLoader.getInstance();
	//加载提示
	protected LoadingInfo mLoadingInfo;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	

	/**
	 * 获取mainActivity实例
	 * 
	 * @return
	 */
	public MainActivity getMainActivity() {
		if (getActivity() != null) {
			return (MainActivity) getActivity();
		}
		return null;
	}

	/**
	 * 获取容器id
	 * 
	 * @return
	 */
	public int getContainerViewID() {
		return R.id.container;
	}

	/**
	 * 
	 * @param fragment
	 *            需要添加的fragment
	 * @param TAG
	 *            标记
	 * @param isEnbaleAnimtion
	 *            是否开打动画
	 */
	public void addFragmentToContainer(BaseFragment fragment, String tag, boolean isEnbaleAnimtion) {
		addFragmentToContainer(fragment, tag, false, isEnbaleAnimtion);
	}

	/**
	 * 
	 * @param fragment
	 *            需要添加的fragment
	 * @param TAG
	 *            标记
	 * @param isAddTobackStack
	 *            是否添加到后退堆栈
	 * @param isEnbaleAnimtion
	 *            是否开打动画
	 */
	public void addFragmentToContainer(BaseFragment fragment, String tag, boolean isAddTobackStack, boolean isEnbaleAnimtion) {
		Fragment f = getChildFragmentManager().findFragmentByTag(tag);
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();

		// 启动动画
		if (isEnbaleAnimtion) {
			// ft.setCustomAnimations(R.anim.fragment_fade_in,
			// R.anim.fragment_fade_out);
			// ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//
			// 设置动画效果
		}

		// 后退堆栈
		if (isAddTobackStack) {
			ft.addToBackStack(null);
		}

		if (f != null) {
			ft.attach(f);
		} else {
			ft.add(getContainerViewID(), fragment, tag); // 替换新的fragment,
		}
		ft.commit();
		getChildFragmentManager().executePendingTransactions();
	}

	public void switchFragment(Fragment fragment, boolean isEnbaleAnimtion) {
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		// 启动动画
		if (isEnbaleAnimtion) {
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);// 设置动画效果
		}

		if (fragment != null) {
			ft.replace(getContainerViewID(), fragment);
		}
		ft.commitAllowingStateLoss();
		
		//ft.commit();
		getChildFragmentManager().executePendingTransactions();
	}

	/**
	 * 删除子ViewFragment
	 * 
	 * @param tag
	 *            初始tag
	 */
	public void detachFragment(String tag) {
		Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
		if (fragment != null) {
			FragmentTransaction ft = getChildFragmentManager().beginTransaction();
			ft.detach(fragment);
			ft.commitAllowingStateLoss();
			getChildFragmentManager().executePendingTransactions();
		}
	}

	/**
	 * 获取是否存在这个tag的fragment
	 * 
	 * @param tag
	 */
	public Fragment getStackFragment(String tag) {
		Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
		return fragment;
	}

	




	/**
	 * 获取容器最顶层的fragment
	 * 
	 * @param tag
	 * @return
	 */
	public BaseFragment findContaninerFragment(String tag) {
		return (BaseFragment) getActivity().getSupportFragmentManager().findFragmentByTag(tag);
	}

	private OnBackStatckListener mOnBackStatckListener = null;

	/**
	 * 设置点击后退到上一个栈的监听,用于做一些改变header样式等操作
	 * 
	 * @param onBackStatckListener
	 */
	public void setOnBackStackListener(OnBackStatckListener onBackStatckListener) {
		mOnBackStatckListener = onBackStatckListener;
	}

	/**
	 * 获取Back监听
	 * 
	 * @return
	 */
	public OnBackStatckListener getOnBackStatckListener() {
		return mOnBackStatckListener;
	}

	public interface OnBackStatckListener {
		public void onBack();
	}

	/** 初始化 **/
	public void initLoadingInfo(View v){
		mLoadingInfo = (LoadingInfo) v.findViewById(R.id.loadingInfo);
	}
	/**
	 * VISIBLE 为 VISIBLE时 显示加载，隐藏v;反之
	 * @param VISIBLE
	 * @param v
	 */
	public void setLoadingViewVisible(int VISIBLE,View v){
		mLoadingInfo.setVisibility(View.VISIBLE);
		mLoadingInfo.setLoadingViewVisible(VISIBLE);
		if(v!=null)
		if(VISIBLE == v.VISIBLE){
			v.setVisibility(v.GONE);
		}else{
			v.setVisibility(v.VISIBLE);
		}
		
	}
	/**
	 * VISIBLE 为 VISIBLE时 显示刷新，隐藏v;反之
	 * @param VISIBLE
	 * @param v
	 */
	public void setNotNetVisible(int VISIBLE,View v){
		mLoadingInfo.setVisibility(View.VISIBLE);
		mLoadingInfo.setNotNetVisible(VISIBLE);
		if(v!=null)
		if(VISIBLE == v.VISIBLE){
			v.setVisibility(v.GONE);
		}else{
			v.setVisibility(v.VISIBLE);
		}
	}
	/**
	 * VISIBLE 为 VISIBLE时 显示无数据，隐藏v;反之
	 * @param VISIBLE
	 * @param v
	 */
	public void setNotDataVisible(int VISIBLE,View v){
		mLoadingInfo.setVisibility(View.VISIBLE);
		mLoadingInfo.setNotDataVisible(VISIBLE);
		if(v!=null)
		if(VISIBLE == v.VISIBLE){
			v.setVisibility(v.GONE);
		}else{
			v.setVisibility(v.VISIBLE);
		}
	}
	/**
	 * 隐藏请求提示控件
	 * @param v
	 */
	public void setLoadInfoGone(View v){
		mLoadingInfo.setVisibility(View.GONE);
		if(v!=null)
		v.setVisibility(View.VISIBLE);
	}
	/**
	 * 设置无网络下按钮点击事件
	 * 
	 * @param mOnRefreshClickListener
	 */
	public void setOnRefreshClickListener(OnRefreshClickListener mOnRefreshClickListener) {
		mLoadingInfo.setOnRefurbishListener(mOnRefreshClickListener);
	}


	public class OnBackClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			getActivity().finish();
		}
	}

	
}
