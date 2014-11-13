package com.zy.zhongyiandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Item;
import com.zy.zhongyiandroid.ui.fragment.ItemListFragment;
import com.zy.zhongyiandroid.ui.fragment.ProductDetailFragment;
import com.zy.zhongyiandroid.ui.fragment.SubCategoryFragment;
import com.zy.zhongyiandroid.ui.widget.ZoomableImageView;
/**
 *
 * @author Seven
 *
 */
public class ImageShowActivity extends BaseActivity  {
	DisplayImageOptions options;
	ZoomableImageView mZoomableImageView;
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_image_show);
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		mZoomableImageView=(ZoomableImageView)findViewById(R.id.imgShow);

		ImageLoader mImageLoader = ImageLoader.getInstance();
		mImageLoader.displayImage(getIntent().getExtras().getString("img"),mZoomableImageView,options);
	}
	
	
	public static void startActivity(Context c,String img){
		Intent i = new Intent(c,ImageShowActivity.class);
		i.putExtra("img",img);
		c.startActivity(i);
	}

}
