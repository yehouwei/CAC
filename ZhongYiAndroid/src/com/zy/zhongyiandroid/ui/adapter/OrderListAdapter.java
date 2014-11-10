package com.zy.zhongyiandroid.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Order;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter{
	ImageLoader mImageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private List<Order> mList=new ArrayList<Order>();
	LayoutInflater mLayoutInflater;
	public OrderListAdapter(Context context){
		mLayoutInflater=LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList==null? 0:mList.size();
	}
	public void setDatas(List<Order> list){
		this.mList=list;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HolderView mHolderView;
		if(convertView==null){
			convertView = mLayoutInflater.inflate(R.layout.adapter_information, null);
			mHolderView = new HolderView(convertView);
			convertView.setTag(mHolderView);
		}else{
			mHolderView = (HolderView) convertView.getTag();
		}
		mHolderView.setData(mList.get(position));
		return convertView;
	}
	public class HolderView{
		private ImageView mImageView;
		private TextView mTittleTextView;
		private TextView mContentTextView;
		public  HolderView(View view) {
			// TODO Auto-generated constructor stub
			mImageView=(ImageView)view.findViewById(R.id.imageView);
			mTittleTextView=(TextView)view.findViewById(R.id.InfoTittle);
			mContentTextView=(TextView)view.findViewById(R.id.InfoContent);
		}
		public void setData(Order order){

//			mImageView.setBackgroundResource(info.getInfoImageView());
			mTittleTextView.setText(order.getShopName());
			mContentTextView.setText(order.getArrageDateTime());
			
			mImageLoader.displayImage(order.getShopImages(), mImageView, options);
		}
	}



}
