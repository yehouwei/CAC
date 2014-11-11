package com.zy.zhongyiandroid.ui.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Order;
import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter {
	ImageLoader mImageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private List<Order> mList = new ArrayList<Order>();
	LayoutInflater mLayoutInflater;
	Context context;

	public OrderListAdapter(Context context) {
		this.context=context;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HolderView mHolderView;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.adapter_orderlist,
					null);
			mHolderView = new HolderView(convertView);
			convertView.setTag(mHolderView);
		} else {
			mHolderView = (HolderView) convertView.getTag();
		}
		mHolderView.setData(mList.get(position));
		return convertView;
	}

	public class HolderView {
		private ImageView mImgShop;
		private ImageView mimgOrderPassed;
		private TextView mtvShopName;
		private TextView mtvArrangeTime;
		private TextView mtvEvent;
		

		public HolderView(View view) {
			// TODO Auto-generated constructor stub
			mImgShop = (ImageView) view.findViewById(R.id.imgShop);
			mimgOrderPassed=(ImageView)view.findViewById(R.id.imgOrderPassed);
			mtvShopName = (TextView) view.findViewById(R.id.tvShopName);
			mtvArrangeTime = (TextView) view.findViewById(R.id.tvArrangeTiime);
			mtvEvent = (TextView) view.findViewById(R.id.tvArrangeEvent);
		}

		public void setData(Order order) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateArrange;
			try {
				dateArrange = sdf.parse(order.getArrageDateTime());
				if(dateArrange.before(new Date())){
					mtvArrangeTime.setTextColor(context.getResources().getColor(R.color.bonus_point));
					mimgOrderPassed.setVisibility(View.VISIBLE);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// mImageView.setBackgroundResource(info.getInfoImageView());
			mtvShopName.setText(order.getShopName());
			mtvArrangeTime.setText(order.getArrageDateTime());

			mtvEvent.setText(order.getRemarks());
			mImageLoader.displayImage(order.getShopImges(), mImgShop, options);
		}
	}

}
