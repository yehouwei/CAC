package com.zy.zhongyiandroid.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.Region;
import com.zy.zhongyiandroid.data.bean.Store;

/**
 * 消息
 * 
 * @author Seven
 * 
 */
public class LocationMapFragment extends BaseFragment {
	private GoogleMap mMap;

	private LatLng NKUT;;
	private static View view;

	Store mStore;
	List<Store> mStores = new ArrayList<Store>();

	private int mPageNum = 1;

	private int mPageSize = 20;
	boolean mIsFirstLoad = true;

	private boolean isRequesEnd = true;
	private int mRegionId = 0;

	private String mKeyWord = null;

	public int getmRegionId() {
		return mRegionId;
	}

	public void setmRegionId(int mRegionId) {
		this.mRegionId = mRegionId;
		mStores.clear();
		request(mRegionId, null);
	}

	public String getmKeyWord() {
		return mKeyWord;
	}

	public void setmKeyWord(String mKeyWord) {
		this.mKeyWord = mKeyWord;
		mStores.clear();
		request(0, mKeyWord);
	}

	TextView tvError;

	List<Store> mStoreList = new ArrayList<Store>();
	Store store;

	public LocationMapFragment() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.fragment_location_map, container,
					false);
		} catch (InflateException e) {
			/* map is already there, just return view as it is */
		}
		initUI(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		request(0, null);
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
		FragmentManager myFM = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = myFM.beginTransaction();

		final SupportMapFragment myMAPF = (SupportMapFragment) myFM
				.findFragmentById(R.id.map);
		mMap = myMAPF.getMap();
		tvError = (TextView) view.findViewById(R.id.tvErrorMap);
		if (mMap == null) {
			ft.hide(myMAPF);
			ft.commit();
			tvError.setVisibility(View.VISIBLE);
		}

	}

	public void addMapMaker(List<Store> stores) {
		if (mMap != null) {
			mMap.clear();
			for (int i = 0; i < stores.size(); i++) {
				NKUT = new LatLng(Double.parseDouble(stores.get(i)
						.getLongitude()), Double.parseDouble(stores.get(i)
						.getGeography()));
				Marker nkut = mMap.addMarker(new MarkerOptions().position(NKUT)
						.title(stores.get(i).getName())
						.snippet(stores.get(i).getAddress()));

			}
			NKUT = new LatLng(Double.parseDouble(stores.get(0).getLongitude()),
					Double.parseDouble(stores.get(0).getGeography()));
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NKUT, 16));
		}
		/*
		 * Toast.makeText(getActivity(),
		 * getResources().getString(R.string.google_service_no_support),
		 * Toast.LENGTH_LONG) .show();
		 */

	}

	public void request(int regionId, String keyWord) {
		mIsFirstLoad = false;
		if (!isRequesEnd) {
			return;
		}

		isRequesEnd = false; // 改变正在请求的标识

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
							/*
							 * mlocatioLocationViewPagerAdapter.setDatas(mStores)
							 * ;
							 * mlocatioLocationViewPagerAdapter.notifyDataSetChanged
							 * ();
							 */

							addMapMaker(mStores);

						}
					}
				}
			});

		}
	};
}