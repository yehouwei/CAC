package com.zy.zhongyiandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Item;
import com.zy.zhongyiandroid.ui.fragment.ItemListFragment;
import com.zy.zhongyiandroid.ui.fragment.ProductDetailFragment;
import com.zy.zhongyiandroid.ui.fragment.SubCategoryFragment;
/**
 *
 * @author Seven
 *
 */
public class ProductDetailActivity extends BaseActivity  {
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.fragment_container);
		ProductDetailFragment mInfoSortFragment = new ProductDetailFragment((Item) getIntent().getExtras().getSerializable("Item"),getIntent().getExtras().getString("btnBackString"));
		// 添加fragment
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// ft.setCustomAnimations(R.anim.fragment_fade_in,
		// R.anim.fragment_fade_out);
		ft.add(R.id.container, mInfoSortFragment);
		ft.commit();
		getSupportFragmentManager().executePendingTransactions();
	}
	
	
	public static void startActivity(Context c,Item sort,String btnBackString){
		Intent i = new Intent(c,ProductDetailActivity.class);
		i.putExtra("Item",sort);
		i.putExtra("btnBackString", btnBackString);
		c.startActivity(i);
	}

}
