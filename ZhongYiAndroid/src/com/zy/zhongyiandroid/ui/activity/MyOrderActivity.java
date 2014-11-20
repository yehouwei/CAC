package com.zy.zhongyiandroid.ui.activity;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.ui.fragment.OrderListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

public class MyOrderActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.fragment_container);

		OrderListFragment mInfoSortFragment = new OrderListFragment(this,getIntent().getExtras().getString("name"));
		// 添加fragment
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// ft.setCustomAnimations(R.anim.fragment_fade_in,
		// R.anim.fragment_fade_out);
		ft.add(R.id.container, mInfoSortFragment);
		ft.commit();
		getSupportFragmentManager().executePendingTransactions();
	}

	public static void startActivity(Context c,String value) {
		Intent i = new Intent(c, MyOrderActivity.class);
			i.putExtra("name", value);
		c.startActivity(i);
	}

}
