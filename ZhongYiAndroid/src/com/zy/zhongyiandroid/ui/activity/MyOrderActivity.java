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
/*	Button btnOrder;

	XListView mxListView;

	Boolean isNew = true;

	Dialog listDialog;

	ImageView imgShopFliter;
	TextView tvEvent;
	TextView tvShop;
	ImageView imgEventFliter;
	EditText etName;
	EditText etPhone;
	EditText etEmail;
	Button btnDate;
	Button btnTime;
	Button btnPostive;
	Button btnCancle;
	String result;

	Order order;

	Context context = this;

	List<Order> mOrderlist = new ArrayList<Order>();
	OrderListAdapter mOrderListAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_myorder);
		initUI();
		initHeader();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		btnOrder = (Button) this.findViewById(R.id.btnOrder);
		mxListView = (XListView) this.findViewById(R.id.listView);
		mOrderListAdapter = new OrderListAdapter(this);
		mOrderListAdapter.setDatas(mOrderlist);

		mxListView.setAdapter(mOrderListAdapter);
		btnOrder.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final OrderDialog mOrderDialog = new OrderDialog(
						MyOrderActivity.this, R.style.MyDialog, null);
				mOrderDialog
						.setOnOrderDialogClickListener(new OnOrderDialogClickListener() {

							@Override
							public void onClick(View v, Order order) {
								// TODO Auto-generated method stub
								mOrderlist.add(order);
								mOrderListAdapter.setDatas(mOrderlist);
								mOrderListAdapter.notifyDataSetChanged();
								mOrderDialog.dismiss();
							}
						});
				mOrderDialog.showDialog();
			}
		});
		mxListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final OrderDialog mOrderDialog = new OrderDialog(
						MyOrderActivity.this, R.style.MyDialog, null);
				mOrderDialog.showDialog();
			}
		});
	}

	public void initHeader() {
		Header mHeader = (Header) this.findViewById(R.id.header);
		if (mHeader != null) {
			mHeader.setTitle(R.string.personal_order);
			mHeader.setBackBtn(getIntent().getStringExtra("name"),
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							finish();
						}
					});
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



	public static void startActivity(Context c, String value) {
		Intent i = new Intent(c, MyOrderActivity.class);
		i.putExtra("name", value);
		c.startActivity(i);
	}*/
}
