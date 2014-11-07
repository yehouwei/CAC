package com.zy.zhongyiandroid.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Store;

/**
 * 消息
 * 
 * @author Seven
 * 
 */
public class LocationMapFragment extends BaseFragment {
	 private GoogleMap mMap;
	 
	 private     LatLng NKUT;;
	 private static View view;
	 
	 List<Store> mStoreList=new ArrayList<Store>();
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	    }
	    try {
	        view = inflater.inflate(R.layout.fragment_location_map, container, false);
	    } catch (InflateException e) {
	        /* map is already there, just return view as it is */
	    }
	    initUI(view);
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
		FragmentManager myFM = getActivity().getSupportFragmentManager();

		final SupportMapFragment myMAPF = (SupportMapFragment) myFM
		                .findFragmentById(R.id.map);
		 mMap = myMAPF.getMap(); 


	}
	public void addMapMaker(List<Store> stores){
		if(mMap!=null){
		mMap.clear();
		for(int i=0;i<stores.size();i++){
	        NKUT=new LatLng( Double.parseDouble(stores.get(i).getLongitude()),Double.parseDouble(stores.get(i).getGeography()));
	        Marker nkut = mMap.addMarker(new MarkerOptions().position(NKUT).title(stores.get(i).getName()).snippet(stores.get(i).getAddress())); 
			
		}
		NKUT=new LatLng( Double.parseDouble(stores.get(0).getLongitude()),Double.parseDouble(stores.get(0).getGeography()));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NKUT, 16));
		}
	}
}