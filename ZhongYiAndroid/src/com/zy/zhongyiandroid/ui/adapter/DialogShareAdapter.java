package com.zy.zhongyiandroid.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.R.mipmap;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
/**
 * 分享
 * @author Seven
 *
 */
public class DialogShareAdapter extends BaseAdapter {

	private Context mContext;
	
	private LayoutInflater mLayoutInflater;
	
	private String[] mTitles = {"微博","QQ空间","微信","朋友圈"};
	
	private int[] mRess = {R.drawable.btn_weibo_selector,R.drawable.btn_qq_selector,R.drawable.btn_weixin_selector,R.drawable.btn_friend_selector};
	
	private OnMyClickListener mClickListener = null;
	
	public void setOnClickListener(OnMyClickListener listener){
		this.mClickListener = listener;
	}

	public DialogShareAdapter(Context context )
	{
		mContext = context;

		mLayoutInflater = LayoutInflater.from(mContext);
	
	}
	

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTitles == null ? 0 : mTitles.length;
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return mTitles == null ? null : mTitles[index];
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}

	@Override
	public View getView(final int position, View converView, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(converView == null){
			converView = mLayoutInflater.inflate(R.layout.dialog_share_item, null);
			viewHolder = new ViewHolder(converView);
			
			converView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) converView.getTag();
		}
//		int padding = (int) mContext.getResources().getDimension(R.dimen.apater_info_sort_padding);
//		if(position==0){
//			converView.setPadding(padding, padding, padding, padding);
//		}else{
//			converView.setPadding(padding, 0, padding, padding);
//		}
		
		viewHolder.setData(position);
		return converView;
	}

	
	public class ViewHolder
	{
		ImageView mImgIcon;
		TextView mTvName;
		View mView;
		
		public ViewHolder(View view)
		{
			mView = view;
			mImgIcon = (ImageView) view.findViewById(R.id.imgIcon);
			mTvName = (TextView) view.findViewById(R.id.tvName);

			
		
		}
		
		public void setData(final int position)
		{
			mTvName.setText(mTitles[position]);
			mImgIcon.setBackgroundResource(mRess[position]);

			mView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(mClickListener!=null)
						mClickListener.onClick(position);
				}
			});

		}

		
		Handler mHandler = new Handler();
	}
	public interface OnMyClickListener{
		public void onClick(int position);
	}
}
