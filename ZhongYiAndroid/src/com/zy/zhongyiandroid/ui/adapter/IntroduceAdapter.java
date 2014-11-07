package com.zy.zhongyiandroid.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Introduce;
import android.R.integer;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroduceAdapter extends PagerAdapter {
	private List<Introduce> mList;
	LayoutInflater mLayoutInflater;
	HolderView mHolderView;
	List<View> vList;

	public IntroduceAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mLayoutInflater = LayoutInflater.from(context);
		vList=new ArrayList<View>();
	}
	public void setDatas(List<Introduce> list) {
		this.mList = list;
		for(int i=0;i<mList.size();i++){
			View view=mLayoutInflater.inflate(R.layout.adapter_introduce, null);
			mHolderView = new HolderView(view);
			mHolderView.setData(mList.get(i));
			vList.add(view);
		}

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0 : mList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(vList.get(position));
		return vList.get(position);
	}
	public class HolderView{
		private ImageView mImageView;
		private TextView mTittleTextView;
		private TextView mContentTextView;
		public  HolderView(View view) {
			// TODO Auto-generated constructor stub
			mImageView=(ImageView)view.findViewById(R.id.imgintroduce);
			mTittleTextView=(TextView)view.findViewById(R.id.tvTitleIntroduce);
			mContentTextView=(TextView)view.findViewById(R.id.tvContentIntroduce);
		}
		public void setData(Introduce introduce){

			mImageView.setBackgroundResource(introduce.getImageview());
			mTittleTextView.setText(introduce.getTitle());
			mContentTextView.setText(introduce.getContent());
		}
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(vList.get(position));
	}



}
