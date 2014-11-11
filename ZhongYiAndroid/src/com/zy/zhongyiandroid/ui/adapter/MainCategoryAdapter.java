package com.zy.zhongyiandroid.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.BaseCategory;
import com.zy.zhongyiandroid.data.bean.Message;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainCategoryAdapter extends BaseAdapter {
	List<BaseCategory> mlist;
	ImageLoader mImageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	LayoutInflater layoutInflater;

	public MainCategoryAdapter(Context context) {
		// TODO Auto-generated constructor stub
		layoutInflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public void setDatas(List<BaseCategory> list) {
		this.mlist = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist == null ? 0 : mlist.size();
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
		HolderView mHolderView = null;
		if (contentView == null) {
			contentView = layoutInflater.inflate(R.layout.adapter_sort, null);
			mHolderView = new HolderView(contentView);
			contentView.setTag(mHolderView);
		} else {
			mHolderView = (HolderView) contentView.getTag();
		}
		mHolderView.setData(mlist.get(position));
		return contentView;
	}

	private class HolderView {
		private ImageView mImageView;
		private TextView textview;

		public HolderView(View v) {
			mImageView = (ImageView) v.findViewById(R.id.ItemImage);
			textview = (TextView) v.findViewById(R.id.ItemText);

		}

		public void setData(BaseCategory category) {
			mImageLoader.displayImage(category.getCatImage(), mImageView,
					options);
			// mImageView.setImageResource(msort.getGroupImage());
			textview.setText(category.getCatName());
		}
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

}
