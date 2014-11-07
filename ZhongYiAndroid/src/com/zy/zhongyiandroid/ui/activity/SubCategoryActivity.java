package com.zy.zhongyiandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.BaseCategory;
import com.zy.zhongyiandroid.ui.fragment.SubCategoryFragment;
/**
 * 早教资源图片列表
 * @author Seven
 *
 */
public class SubCategoryActivity extends BaseActivity  {
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.fragment_container);

		
		SubCategoryFragment mInfoSortFragment = new SubCategoryFragment((BaseCategory) getIntent().getExtras().getSerializable("category"));
		// 添加fragment
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// ft.setCustomAnimations(R.anim.fragment_fade_in,
		// R.anim.fragment_fade_out);
		ft.add(R.id.container, mInfoSortFragment);
		ft.commit();
		getSupportFragmentManager().executePendingTransactions();
	}
	
	
	public static void startActivity(Context c,BaseCategory category){
		Intent i = new Intent(c,SubCategoryActivity.class);
		i.putExtra("category",category);
		c.startActivity(i);
	}

}
