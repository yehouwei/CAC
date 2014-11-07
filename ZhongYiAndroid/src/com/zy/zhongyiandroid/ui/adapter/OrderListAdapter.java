package com.zy.zhongyiandroid.ui.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Message;
import com.zy.zhongyiandroid.data.bean.Order;
import com.zy.zhongyiandroid.data.bean.Store;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter {

	ImageLoader mImageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private List<Order> mList;
	LayoutInflater mLayoutInflater;

	public OrderListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mLayoutInflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0 : mList.size();
	}

	public List<Order> getList() {
		return mList;
	}

	public void setDatas(List<Order> list) {
		this.mList = list;
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
	public View getView(int position, View contentView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HolderView mHolderView;
		if (contentView == null) {
			contentView = mLayoutInflater.inflate(R.layout.adapter_location,
					null);
			mHolderView = new HolderView(contentView);
			contentView.setTag(mHolderView);
		} else {
			mHolderView = (HolderView) contentView.getTag();
		}
		mHolderView.setData(mList.get(position));
		return contentView;
	}

	public class HolderView {
		private ImageView mImageView;
		private TextView mTittleTextView;
		private TextView mStoreTelTextView;
		private TextView mStoreGeographTextView;
		private TextView mStoreAddTextView;

		public HolderView(View view) {
			// TODO Auto-generated constructor stub
			mImageView = (ImageView) view.findViewById(R.id.imageView);
			mTittleTextView = (TextView) view.findViewById(R.id.InfoTittle);
			mStoreTelTextView = (TextView) view.findViewById(R.id.storeTel);
			mStoreAddTextView = (TextView) view.findViewById(R.id.storeAdd);
		}

		public void setData(Order order) {

			mImageView.setBackgroundResource(R.drawable.tab_personal);
			mTittleTextView.setText(order.getShopName());
			mStoreTelTextView.setText("到店时间: " + order.getArrageDateTime());
			mStoreAddTextView.setText(order.getAppellation());
			

//			mImageLoader.displayImage(store.getImage(), mImageView, options);
		}
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

}