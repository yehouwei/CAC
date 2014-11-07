package com.zy.zhongyiandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.zy.zhongyiandroid.R;

import com.zy.zhongyiandroid.data.bean.SubCategory;
import com.zy.zhongyiandroid.ui.fragment.ItemListFragment;
import com.zy.zhongyiandroid.ui.fragment.SubCategoryFragment;

/**
 * 早教资源图片列表
 * 
 * @author Seven
 * 
 */
public class ItemListActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.fragment_container);

		ItemListFragment mInfoSortFragment = new ItemListFragment(
				(SubCategory) getIntent().getExtras().getSerializable(
						"subCatory"), getIntent().getExtras().getString("tittle"));
		// 添加fragment
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// ft.setCustomAnimations(R.anim.fragment_fade_in,
		// R.anim.fragment_fade_out);
		ft.add(R.id.container, mInfoSortFragment);
		ft.commit();
		getSupportFragmentManager().executePendingTransactions();
	}

	public static void startActivity(Context c, SubCategory sort, String tittle) {
		Intent i = new Intent(c, ItemListActivity.class);
		i.putExtra("subCatory", sort);
		i.putExtra("tittle", tittle);
		c.startActivity(i);
	}


}
