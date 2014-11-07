package com.zy.zhongyiandroid.ui.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Message;
import com.zy.zhongyiandroid.data.bean.Region;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RegionDialogAdapter extends BaseAdapter {
	
	ImageLoader mImageLoader = ImageLoader.getInstance();
	//DisplayImageOptions options;
	private List<Region> mList;
	LayoutInflater mLayoutInflater;
	public RegionDialogAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mLayoutInflater=LayoutInflater.from(context);
/*		options = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();*/
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList==null?0:mList.size();
	}

	public List<Region> getList() {
		return mList;
	}

	public void setDatas(List<Region> list) {
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
		if(contentView==null){
			contentView = mLayoutInflater.inflate(R.layout.adapter_dialog_simplelist, null);
			mHolderView = new HolderView(contentView);
			contentView.setTag(mHolderView);
		}else{
			mHolderView = (HolderView) contentView.getTag();
		}
		mHolderView.setData(mList.get(position));
		return contentView;
	}
	public class HolderView{
/*		private ImageView mImageView;
		private TextView mTittleTextView;*/
		private TextView mtvlist;
		public  HolderView(View view) {
			// TODO Auto-generated constructor stub
			mtvlist=(TextView)view.findViewById(R.id.tvList);
/*			mTittleTextView=(TextView)view.findViewById(R.id.InfoTittle);
			mContentTextView=(TextView)view.findViewById(R.id.InfoContent);*/
		}
		public void setData(Region region){

//			mImageView.setBackgroundResource(info.getInfoImageView());
			mtvlist.setText(region.getText());
			//mContentTextView.setText(message.getContent());
			
			//mImageLoader.displayImage(message.getImg(), mImageView, options);
		}
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

}
