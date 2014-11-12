package com.zy.zhongyiandroid.ui.activity;

import java.util.List;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.Order;
import com.zy.zhongyiandroid.data.bean.UserInfo;
import com.zy.zhongyiandroid.data.shared.UserData;
import com.zy.zhongyiandroid.ui.fragment.BonusFragment;
import com.zy.zhongyiandroid.ui.fragment.OrderListFragment;
import com.zy.zhongyiandroid.ui.widget.CircleBonusBar;
import com.zy.zhongyiandroid.ui.widget.Header;
import android.R.integer;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyBonusActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.fragment_container);

		BonusFragment mInfoSortFragment = new BonusFragment(this,getIntent().getExtras().getString("name"));
		// 添加fragment
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// ft.setCustomAnimations(R.anim.fragment_fade_in,
		// R.anim.fragment_fade_out);
		ft.add(R.id.container, mInfoSortFragment);
		ft.commit();
		getSupportFragmentManager().executePendingTransactions();
	}

	public static void startActivity(Context c,String value) {
		Intent i = new Intent(c, MyBonusActivity.class);
			i.putExtra("name", value);
		c.startActivity(i);
	}
}
