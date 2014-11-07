package com.zy.zhongyiandroid.ui.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.ZhongYi;
import com.zy.zhongyiandroid.data.bean.Store;
import com.zy.zhongyiandroid.ui.activity.StoreMapActivity;
import com.zy.zhongyiandroid.ui.adapter.LocationAdapater;
import com.zy.zhongyiandroid.ui.widget.list.XListView;

/**
 * 消息
 * 
 * @author Seven
 * 
 */
public class LocationListFragment extends BaseFragment {
	XListView mListView;
	LocationAdapater mLocationAdapater;
	List<Store> mStoreList = new ArrayList<Store>();
	Context context;


	public LocationListFragment(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;

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

		mListView = (XListView) view.findViewById(R.id.listView);
		/*
		 * mListView.setHeaderDividersEnabled(false);
		 * mListView.setDividerHeight(0); mListView.setPullLoadEnable(true);
		 * mListView.setPullRefreshEnable(true);
		 */
		// mListView.setXListViewListener(mIXListViewListener);
		mLocationAdapater = new LocationAdapater(getActivity());

		mListView.setAdapter(mLocationAdapater);
		mLocationAdapater.setDatas(mStoreList);
		mLocationAdapater.notifyDataSetChanged();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
/*				String[] storeName=new String[mStoreList.size()];
				for(int i=0;i<mStoreList.size();i++){
					storeName[i]=mStoreList.get(i).getName();
				}*/
				StoreMapActivity.startActivity(context,mStoreList.get(position-1));
			    ZhongYi mZhongYi = ((ZhongYi)getActivity().getApplicationContext());  
			    mZhongYi.setStoreList(mStoreList);
			}
		});
	}
	public void setDatas(List<Store> storelist) {
		this.mStoreList=storelist;
		// TODO Auto-generated method stub
		mLocationAdapater.setDatas(storelist);
		mLocationAdapater.notifyDataSetChanged();
	}
}