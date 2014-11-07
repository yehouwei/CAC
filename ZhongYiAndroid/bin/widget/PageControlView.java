package com.zy.zhongyiandroid.ui.widget;



import java.util.List;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Introduce;

import android.R.integer;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;




public class PageControlView extends LinearLayout {
	private Context context;

	private int count;
	public PageControlView(Context context) {
		super(context);
		this.init(context);
	}
	public PageControlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
		
	}

	private void init(Context context) {
		this.context=context;
	}

	public void bindScrollViewGroup(List<Introduce> introduces, int position) {
		this.count=introduces.size();

	//	generatePageControl(scrollViewGroup.getCurrentItem());
		this.removeAllViews();
/*
		int pageNo = currentIndex+1; //绗嚑椤?
		int pageSum = this.count; //鎬诲叡澶氬皯椤?
*/		for(int i=0;i<count;i++){
			ImageView imageView=new ImageView(context);
			if(i==position){
				imageView.setImageResource(R.drawable.page_indicator_focused);
			}else{
				imageView.setImageResource(R.drawable.page_indicator);
			}
			 this.addView(imageView);
		}
/*		scrollViewGroup.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int currentIndext) {
				// TODO Auto-generated method stub
				generatePageControl(currentIndext);
				
			}
		});*/
	}



	/*private void generatePageControl(int currentIndex) {

		}*/
/*		if(pageSum>1){
			int currentNum = (pageNo % pageNum == 0 ? (pageNo / pageNum) - 1  
	                 : (int) (pageNo / pageNum))   
	                 * pageNum; 
			
			 if (currentNum < 0)   
	             currentNum = 0;  */ 
			 
/*			 if (pageNo > pageNum){
				 ImageView imageView = new ImageView(context);
				 imageView.setImageResource(R.drawable.zuo);
				 this.addView(imageView);
			 }*/
			 
			 
			 
/*			 for (int i = 0; i < pageNum; i++) {   
	             if ((currentNum + i + 1) > pageSum || pageSum < 2)   
	                 break;   
	             
	             ImageView imageView = new ImageView(context);
	             if(currentNum + i + 1 == pageNo){
	            	 imageView.setImageResource(R.drawable.page_indicator_focused);
	             }else{
	            	 imageView.setImageResource(R.drawable.page_indicator);
	             }
	             this.addView(imageView);
	         } */  
			 
/*			 if (pageSum > (currentNum + pageNum)) {
				 ImageView imageView = new ImageView(context);
				 imageView.setImageResource(R.drawable.you);
				 this.addView(imageView);
			 }*/
		
	


}

