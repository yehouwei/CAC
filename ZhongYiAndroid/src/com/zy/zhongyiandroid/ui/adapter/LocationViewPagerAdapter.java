package com.zy.zhongyiandroid.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Introduce;
import com.zy.zhongyiandroid.data.bean.Store;
import com.zy.zhongyiandroid.ui.widget.list.XListView;

import android.R.integer;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationViewPagerAdapter extends PagerAdapter {
	private List<Store> mList;
	XListView xListView;

	LayoutInflater mLayoutInflater;

	List<View> vList;
	Context context;

	public LocationViewPagerAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mLayoutInflater = LayoutInflater.from(context);
		this.context = context;
		vList = new ArrayList<View>();
		vList.add(mLayoutInflater.inflate(
				R.layout.adapter_location_viewpager_xlistview, null));
		vList.add(mLayoutInflater.inflate(
				R.layout.adapter_location_viewpager_map, null));
	}

	public void setDatas(List<Store> list) {
		this.mList = list;
		/*
		 * for(int i=0;i<mList.size();i++){ View
		 * view=mLayoutInflater.inflate(R.layout.adapter_introduce, null);
		 * mHolderView = new HolderView(view);
		 * mHolderView.setData(mList.get(i)); vList.add(view); }
		 */

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return vList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
/*		ListHolderView mHolderView;
		View view;
	
		// TODO Auto-generated method stub
		if(position==0){
			 view=mLayoutInflater.inflate(R.layout.adapter_location_viewpager_xlistview, null);

			mHolderView = new ListHolderView(view);
			mHolderView.setData();
		}else{
			 view=mLayoutInflater.inflate(R.layout.adapter_location_viewpager_map, null);

		
		}
		container.addView(view);
		return container;*/
		container.addView(vList.get(position));
		return vList.get(position);
		
	}
	public class ListHolderView{
		private XListView mListView;
		LocationAdapater mLocationAdapater;
		public  ListHolderView(View view) {
			// TODO Auto-generated constructor stub
			mListView=(XListView)view.findViewById(R.id.listView);
			mLocationAdapater=new LocationAdapater(context);
			setData();
			mListView.setAdapter(mLocationAdapater);
			
		}	 
		
	
		public void setData(){	
	
			mLocationAdapater.setDatas(mList);
			mLocationAdapater.notifyDataSetChanged();
	//		mImageView.setBackgroundResource(introduce.getImageview());
	//		mTittleTextView.setText(introduce.getTitle());
	//		mContentTextView.setText(introduce.getContent());
	}
	}
	public class MapHolderView{
//		private ImageView mImageView;
//		private TextView mTittleTextView;
//		private TextView mContentTextView;
		private GoogleMap map;
		public  MapHolderView(View view) {
			// TODO Auto-generated constructor stub
/*			FragmentManager myFM = get

			final SupportMapFragment myMAPF = (SupportMapFragment) myFM
			                .findFragmentById(R.id.map);
			 mMap = myMAPF.getMap(); 

			 addMapMaker(mStoreList);*/
		}
		public void setData(Store introduce){

	//		mImageView.setBackgroundResource(introduce.getImageview());
	//		mTittleTextView.setText(introduce.getTitle());
	//		mContentTextView.setText(introduce.getContent());
		}
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(vList.get(position));
	}

}
