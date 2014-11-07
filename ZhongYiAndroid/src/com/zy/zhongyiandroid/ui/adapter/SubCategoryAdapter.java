package com.zy.zhongyiandroid.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;













import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Message;
import com.zy.zhongyiandroid.data.bean.SubCategory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SubCategoryAdapter extends BaseAdapter {
	List<SubCategory> mlist;
	ImageLoader mImageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	LayoutInflater layoutInflater;
	public SubCategoryAdapter(Context context) {
		// TODO Auto-generated constructor stub
		layoutInflater=LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}

	public void setDatas(List<SubCategory> list){
		this.mlist = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist==null?0:mlist.size();
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
		if(contentView==null){
			contentView = layoutInflater.inflate(R.layout.adapter_subcatory, null);
			mHolderView = new HolderView(contentView);
			contentView.setTag(mHolderView);
		}else{
			mHolderView = (HolderView) contentView.getTag();
		}
		mHolderView.setData(mlist.get(position));
		return contentView;
	}
	private class HolderView{
		private ImageView mimgSubCatory;
		private TextView mtvSubCatoryName;

		
		public HolderView(View v){
			mimgSubCatory = (ImageView) v.findViewById(R.id.imgSubCatory);
			mtvSubCatoryName = (TextView) v.findViewById(R.id.tvSubCatoryName);

		}
		public void setData(SubCategory msort){
			mImageLoader.displayImage(msort.getCatImage(), mimgSubCatory, options);
//			mImageView.setImageResource(msort.getGroupImage());
			mtvSubCatoryName.setText(msort.getCatName());
		}
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
	

}
