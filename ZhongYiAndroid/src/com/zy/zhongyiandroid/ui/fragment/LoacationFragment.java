package com.zy.zhongyiandroid.ui.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.google.android.gms.maps.MapFragment;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.Region;
import com.zy.zhongyiandroid.data.bean.Store;
import com.zy.zhongyiandroid.ui.activity.MyIntroduceActivity;
import com.zy.zhongyiandroid.ui.adapter.LocationViewPagerAdapter;
import com.zy.zhongyiandroid.ui.dialog.RegionDialog;
import com.zy.zhongyiandroid.ui.dialog.RegionDialog.OnDialogClickListener;
import com.zy.zhongyiandroid.ui.widget.Header;

/**
 * 消息
 * 
 * @author Seven
 * 
 */
public class LoacationFragment extends BaseFragment {

	public final static String TAG = "LocationFragment";

	boolean mIsFirstLoad = true;

	private boolean isRequesEnd = true;

	TextView spinnerTextView;

	ImageButton searchImageView;

	EditText searchEditText;

	LocationMapFragment mMapFragment;
	LocationListFragment mXlistviewFragment;
	ViewPager mvpLocation;
	LocationViewPagerAdapter mlocatioLocationViewPagerAdapter;

	Store mStore;
	Region region;
	List<Store> mStores = new ArrayList<Store>();
	List<Region> mRegions;

	private int mPageNum = 1;

	private int mPageSize = 20;

	/*
	 * private int regionId=0;
	 * 
	 * private String keyWord=null;
	 */

	private Boolean isOne = true;

	private Boolean isMapFirst = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_location, null);

		// 初始化ui\
		initUI(view);
		initHeader(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// initdata(storeName[0]);
		request(0, null);
		requestRegion();
	}

	public static void startActivity(Context c) {
		Intent i = new Intent(c, MyIntroduceActivity.class);
		c.startActivity(i);
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

	/**
	 * 初始化UI
	 */
	public void initUI(View view) {
		View spinnerView = (View) view.findViewById(R.id.spinnerview);
		spinnerTextView = (TextView) view.findViewById(R.id.tvSpinner);
		spinnerView.setOnTouchListener(mOnTouchListener);
		searchImageView = (ImageButton) view.findViewById(R.id.btnSearch);
		searchImageView.setOnClickListener(onClickListener);
		searchEditText = (EditText) view.findViewById(R.id.etsearch);
/*		  mvpLocation=(ViewPager)view.findViewById(R.id.vpLocation);
		  mlocatioLocationViewPagerAdapter=new
		  LocationViewPagerAdapter(getFragmentManager(),getActivity());
		  mlocatioLocationViewPagerAdapter.setDatas(mStores);
		  mvpLocation.setAdapter(mlocatioLocationViewPagerAdapter);*/
		 
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		mXlistviewFragment = new LocationListFragment(getActivity());
		mMapFragment=new LocationMapFragment();
		ft.add(R.id.vpLocation, mXlistviewFragment);
		ft.add(R.id.vpLocation, mMapFragment);
		ft.hide(mMapFragment).show(mXlistviewFragment);
		ft.commit();
		// getChildFragmentManager().executePendingTransactions();

	}

	public void initHeader(View v) {
		mHeader = (Header) v.findViewById(R.id.header);
		mHeader.setTitle(R.string.tab_location);
		mHeader.setIntroduceBtn(
				getActivity().getResources().getString(R.string.cac),
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MyIntroduceActivity.startActivity(getActivity(),
								getActivity().getString(R.string.tab_location));
					}
				});
		mHeader.setBtnRight1(R.drawable.icon_location_header_switch,
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						FragmentTransaction ft = getActivity()
								.getSupportFragmentManager().beginTransaction();
						if (isOne) {

							ft.hide(mXlistviewFragment).show(mMapFragment);

							isOne = false;

						} else {
							ft.hide(mMapFragment).show(mXlistviewFragment);

							isOne = true;
						}
						ft.commit();
						// getChildFragmentManager().executePendingTransactions();
					}
				});
	}

	public void request(int regionId, String keyWord) {
		mIsFirstLoad = false;
		if (!isRequesEnd) {
			return;
		}

		isRequesEnd = false; // 改变正在请求的标识
		
/*		  if ((mStores == null) || (mStores.size() == 0)) {
			  mXlistviewFragment.setFLoadingViewVisible(View.VISIBLE); 
		  }*/
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

						if (stores != null && stores.size() != 0) {
							// initData(sorts);
							// mStores.clear();
							mStores.clear();
							mStores.addAll(stores);
/*							mlocatioLocationViewPagerAdapter.setDatas(mStores);
							mlocatioLocationViewPagerAdapter.notifyDataSetChanged();*/
							mXlistviewFragment.setDatas(mStores);
							mMapFragment.addMapMaker(mStores);

						}
					}
				}
			});

		}
	};
	OnTouchListener mOnTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View touchedView, MotionEvent event) {

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				switch (touchedView.getId()) {
				case R.id.spinnerview:
					RegionDialog mRegionDialog = new RegionDialog(
							getActivity(), R.style.MyDialog,mRegions);
					mRegionDialog
							.setOnDialogClickListener(new OnDialogClickListener() {

								@Override
								public void onClick(View v, Region region) {
									// TODO Auto-generated method stub

									spinnerTextView.setText(region.getText());
									request(region.getId(), null);
								}
							});
					mRegionDialog.showDialog();
					break;
				}
			}
			return false;
		}
	};
	OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String search = searchEditText.getText().toString();
			if (search == "") {
				Toast.makeText(getActivity(), "请输入要搜索的内容", Toast.LENGTH_SHORT)
						.show();

			} else {
				// initdata(search);
/*				try {
					String strUTF8 = URLEncoder.encode(search, "UTF-8");
					request(0, strUTF8);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				request(0, getUTF8XMLString(searchEditText.getText().toString()));

			}

		}
	};

	public static String getUTF8XMLString(String xml) {
		// A StringBuffer Object
		StringBuffer sb = new StringBuffer();
		sb.append(xml);
		String xmString = "";
		String xmlUTF8 = "";
		try {
			xmString = new String(sb.toString().getBytes("UTF-8"));
			xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
			System.out.println("utf-8 编码：" + xmlUTF8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return to String Formed
		return xmlUTF8;
	} 
	public void requestRegion() {
/*		if (!isRequesEnd) {
			return;
		}

		isRequesEnd = false; // 改变正在请求的标识
*/		/*
		 * if ((mStores == null) || (mStores.size() == 0)) {
		 * setLoadingViewVisible(View.VISIBLE, mListView); }
		 */
		HttpApi.getRegion(getActivity(), mRegionOnRequestListener);
	}

	public OnRequestListener mRegionOnRequestListener = new OnRequestListener() {

		@Override
		public void onResponse(String url, final int state,
				final Object result, int type) {
			// mIsFirstLoad = false;
			isRequesEnd = true;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if ((state == HttpConnectManager.STATE_SUC)
							&& (result != null)) {

						List<Region> regions = (List<Region>) result;

						if (regions != null && regions.size() != 0) {
							mRegions=regions;


						}
					}
				}
			});

		}
	};

}
