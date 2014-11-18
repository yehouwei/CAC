package com.zy.zhongyiandroid.ui.fragment;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.ZhongYi;
import com.zy.zhongyiandroid.data.NetCache;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.Api.ServerUrl;
import com.zy.zhongyiandroid.data.bean.Message;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.Store;
import com.zy.zhongyiandroid.ui.activity.StoreMapActivity;
import com.zy.zhongyiandroid.ui.adapter.LocationAdapater;
import com.zy.zhongyiandroid.ui.adapter.MessageAdapter;
import com.zy.zhongyiandroid.ui.widget.LoadingInfo.OnRefreshClickListener;
import com.zy.zhongyiandroid.ui.widget.list.XListView;
import com.zy.zhongyiandroid.ui.widget.list.XListView.IXListViewListener;

/**
 * 消息
 * 
 * @author Seven
 * 
 */
public class LocationListFragment extends BaseFragment {
	private XListView mListView;
	private LocationAdapater mLocationAdapater;
	private List<Store> mStoreList = new ArrayList<Store>();
	Context context;

	boolean mIsFirstLoad = true;

	private boolean isRequesEnd = true;

	public final int FRIST_PAGE_NUMBER = 1;

	private List<Store> mStores = new ArrayList<Store>();

	private Store mStore;

	private int mPageNum = 1;

	private int mPageSize = 5;

	private int mRegionId = 0;

	private String mKeyWord = null;

	public LocationListFragment(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_locationlist, null);

		// 初始化ui\
		initUI(view);
		initLoadingInfo(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(null);
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
	}
	

	public int getRegionId() {
		return mRegionId;
	}

	public void setRegionId(int regionId) {
		this.mRegionId = regionId;
		mStores.clear();
		initData(null);
		//request(regionId, mKeyWord);
	}

	public String getKeyWord() {
		return mKeyWord;
	}

	public void setKeyWord(String keyWord) {
		this.mKeyWord = keyWord;
		mStores.clear();
		request(mRegionId, mKeyWord);
	}

	/**
	 * 初始化UI
	 */
	public void initUI(View view) {

		mListView = (XListView) view.findViewById(R.id.lvStore);
		
		  mListView.setHeaderDividersEnabled(false);
		  mListView.setDividerHeight(0); 
		 mListView.setPullLoadEnable(true);
		  mListView.setPullRefreshEnable(true);
		 
		 mListView.setXListViewListener(mIXListViewListener);
		mLocationAdapater = new LocationAdapater(getActivity());

		mListView.setAdapter(mLocationAdapater);
		//mLocationAdapater.setDatas(mStoreList);
		mLocationAdapater.notifyDataSetChanged();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				/*
				 * String[] storeName=new String[mStoreList.size()]; for(int
				 * i=0;i<mStoreList.size();i++){
				 * storeName[i]=mStoreList.get(i).getName(); }
				 */
				StoreMapActivity.startActivity(getActivity(),
						mStores.get(position - 1));
				ZhongYi mZhongYi = ((ZhongYi) getActivity()
						.getApplicationContext());
				mZhongYi.setStoreList(mStores);
			}
		});
		 initLoadingInfo(view);
		 //异常情况下点击刷新按钮处理
		 setOnRefreshClickListener(mOnRefreshClickListener);
	}

	public void setDatas(List<Store> storelist) {
		this.mStoreList = storelist;
		// TODO Auto-generated method stub
		if (mLocationAdapater == null) {
			mLocationAdapater = new LocationAdapater(getActivity());
		}
		mLocationAdapater.setDatas(mStoreList);
		mLocationAdapater.notifyDataSetChanged();
	}

	public void request(int regionId, String keyWord) {
		mIsFirstLoad = false;
		if (!isRequesEnd) {
			return;
		}

		isRequesEnd = false; // 改变正在请求的标识

		if ((mStores == null) || (mStores.size() == 0)) {

			setLoadingViewVisible(View.VISIBLE, mListView);
		}

		HttpApi.getStores(getActivity(), mPageNum, mPageSize, regionId,
				keyWord, mOnRequestListener);
	}

	public OnRequestListener mOnRequestListener = new OnRequestListener() {

		@Override
		public void onResponse(String url, final int state,
				final Object result, int type) {
			mIsFirstLoad = false;
			if (!isAdded()) // fragment 已退出,返回
			{
				return;
			}
			isRequesEnd = true;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if ((state == HttpConnectManager.STATE_SUC)
							&& (result != null)) {
						MyApiResult myApiResult = (MyApiResult) result;
						List<Store> stores = null;
						if (myApiResult.getRows() != null) {
							stores = (List<Store>) myApiResult.getRows();

						}
						if ((stores != null) && (stores.size() > 0)) {
							initData(stores);
							onStopLoad();
							setLoadInfoGone(mListView);
							if (stores.size() < mPageSize) {
								mListView.setPullLoadEnable(false);
							} else {
								mListView.setPullLoadEnable(true);
							}

						} else {
							mListView.setPullLoadEnable(false);
							if ((mPageNum == 1)
									&& ((mStores == null) || (mStores.size() == 0))) {
								setNotDataVisible(View.VISIBLE, mListView);
							}
						}
					} else if (state == HttpConnectManager.STATE_TIME_OUT) { // 请求超时
						if ((mPageNum == 1)
								&& ((mStores == null) || (mStores.size() == 0))) {
							setNotNetVisible(View.VISIBLE, mListView);
						}
						Toast.makeText(getActivity(), R.string.time_out,
								Toast.LENGTH_SHORT).show();
						mListView.setPullLoadEnable(false);
					} else { // 请求失败
						if ((mPageNum == 1)
								&& ((mStores == null) || (mStores.size() == 0))) {
							setNotNetVisible(View.VISIBLE, mListView);
						}
						Toast.makeText(getActivity(), R.string.request_fail,
								Toast.LENGTH_SHORT).show();
						mListView.setPullLoadEnable(false);

					}
				}
			});

		}
	};

	Handler mHandler = new Handler();

	public void initData(List<Store> stores) {
		if (mListView.getAdapter() == null) {
			mLocationAdapater = new LocationAdapater(getActivity());
			mListView.setAdapter(mLocationAdapater);
		}

		if (stores == null) {// 第一次进入先读取缓存
			// 获取缓存数据
			if (mPageNum == 1 && mRegionId == 0 && mKeyWord == null) {
				mStores = NetCache.readCache(ServerUrl.URL_STORES);
			}
			if (mIsFirstLoad || mStores == null || mStores.size() == 0) {
				request(mRegionId, mKeyWord);
			}
			mLocationAdapater.setDatas(mStores);
			mLocationAdapater.notifyDataSetChanged();
		} else {
			if (stores.size() != 0) {
				if (stores == null) {
					stores = new ArrayList<Store>();
				}

				if (mPageNum == FRIST_PAGE_NUMBER) {

					if (mRegionId == 0 && mKeyWord == null) {
						mStores = stores;
						// 保存第一页的缓存
						try {
							NetCache.saveCache(stores, ServerUrl.URL_STORES);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					mStores = stores;
				} else {
					mStores.addAll(stores);
				}
					mLocationAdapater.setDatas(mStores);
					mLocationAdapater.notifyDataSetChanged();
				
			} else {
				// 判断是否有网络的情况
				if ((mStores == null) && (mStores.size() == 0)) {
					setNotNetVisible(View.VISIBLE, mListView);
				}
			}
		}

	}

	/**
	 * 刷新按钮
	 */
	private OnRefreshClickListener mOnRefreshClickListener = new OnRefreshClickListener() {

		@Override
		public void onClick(View v) {
			initData(null); // 请求数据

		}
	};

	public IXListViewListener mIXListViewListener = new IXListViewListener() {

		@Override
		public void onRefresh() {
			if (isRequesEnd) {
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						onStopLoad();
					}
				}, 2000);

				request(mRegionId, mKeyWord);

			}
		}

		@Override
		public void onLoadMore() {
			if (isRequesEnd) {
				mPageNum++;
				request(mRegionId, mKeyWord);
			}
		}
	};

	/** 关闭Load的显示状态 **/
	private void onStopLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

}