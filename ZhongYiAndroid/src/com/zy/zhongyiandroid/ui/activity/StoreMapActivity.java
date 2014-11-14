package com.zy.zhongyiandroid.ui.activity;

import java.io.Serializable;
import java.util.List;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.google.android.gms.internal.ie;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.ZhongYi;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.bean.Order;
import com.zy.zhongyiandroid.data.bean.OrderPost;
import com.zy.zhongyiandroid.data.bean.Store;
import com.zy.zhongyiandroid.data.shared.UserData;
import com.zy.zhongyiandroid.ui.dialog.OrderDialog;
import com.zy.zhongyiandroid.ui.dialog.UserLoginDialog;
import com.zy.zhongyiandroid.ui.dialog.OrderDialog.OnOrderDialogClickListener;
import com.zy.zhongyiandroid.ui.dialog.UserLoginDialog.OnLoginDialogClickListener;
import com.zy.zhongyiandroid.ui.widget.Header;

import android.R.integer;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class StoreMapActivity extends BaseActivity {
	// static final LatLng NKUT = new LatLng(23.979548, 120.696745);
	private GoogleMap map;
	LatLng NKUT;
	TextView mtvName;
	TextView mtvTel;
	TextView mtvAddress;
	ImageButton mbtnCall;
	TextView mtvErrorMap;
	Store store;
	OrderPost mOrderPost;
	UserData userData = new UserData();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_storemap);
		store = (Store) getIntent().getExtras().get("store");
		initHeader();
		initUI();

	}

	private void initUI() {
		// TODO Auto-generated method stub
		mtvName = (TextView) this.findViewById(R.id.tvName);
		mtvTel = (TextView) this.findViewById(R.id.tvTel);
		mtvAddress = (TextView) this.findViewById(R.id.tvAddress);
		mbtnCall = (ImageButton) this.findViewById(R.id.btnCall);
		mtvErrorMap=(TextView)findViewById(R.id.tvErrorMap);
		mtvName.setText(store.getName());
		mtvTel.setText(store.getPhone());
		mtvAddress.setText(store.getAddress());
		android.support.v4.app.FragmentManager fragmentManager =getSupportFragmentManager();
		FragmentTransaction ft=fragmentManager.beginTransaction();
		SupportMapFragment mSupportMapFragment=(SupportMapFragment) fragmentManager
				.findFragmentById(R.id.map);

		map = mSupportMapFragment.getMap();
		NKUT = new LatLng(Double.parseDouble(store.getLongitude()),
				Double.parseDouble(store.getGeography()));
		if (map != null) {
			Marker nkut = map.addMarker(new MarkerOptions().position(NKUT)
					.title(store.getName()).snippet(store.getAddress()));

			// Move the camera instantly to NKUT with a zoom of 16.
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(NKUT, 16));
		} else {
			ft.hide(mSupportMapFragment);
			ft.commit();
			mtvErrorMap.setVisibility(View.VISIBLE);
/*			Toast.makeText(this, getResources().getString(R.string.google_service_no_support), Toast.LENGTH_LONG)
					.show();*/
		}
		mbtnCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog alert = new AlertDialog.Builder(
						StoreMapActivity.this).create();
				alert.setTitle(getResources().getString(R.string.confirm_to_call));
				alert.setMessage(store.getPhone());
				alert.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.cancle),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
				alert.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.confirm),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();

								Intent intent = new Intent(Intent.ACTION_CALL,
										Uri.parse("tel:" + store.getPhone()));
								startActivity(intent);
							}
						});
				alert.show();

			}
		});
	}

	public static void startActivity(Context c, Store store) {
		Intent i = new Intent(c, StoreMapActivity.class);
		i.putExtra("store", store);
		// i.putExtra("shoplist", shoplist);
		c.startActivity(i);
	}

	public void initHeader() {
		Header mHeader = (Header) this.findViewById(R.id.header);
		if (mHeader != null) {
			mHeader.setEmptyTittle(store.getName());
			mHeader.setBackBtn(getResources().getString(R.string.tab_location),
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							finish();
						}
					});
			mHeader.setRight("预约", new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!userData.getIsShowLogin()) {
						UserLoginDialog userLoginDialog = new UserLoginDialog(
								StoreMapActivity.this, R.style.MyDialog);
						userLoginDialog
								.setOnLoginDialogClickListener(new OnLoginDialogClickListener() {

									@Override
									public void startActivity() {
										// TODO Auto-generated method stub
										initOrderDialog();
									}
								});
						userLoginDialog.showDialog();

					} else {
						initOrderDialog();
					}
				}
			});
		}
	}

	void initOrderDialog() {
		final OrderDialog mOrderDialog = new OrderDialog(StoreMapActivity.this,
				R.style.MyDialog, null);
		// ZhongYi mZhongYi = ((ZhongYi)getApplicationContext());
		// mZhongYi.setStoreList(storeList)
		// mOrderDialog.setShopData(getIntent().getExtras().getStringArray("store"));
/*		mOrderDialog
				.setOnOrderDialogClickListener(new OnOrderDialogClickListener() {

					@Override
					public void onClick(View v, Order order) {
						// TODO Auto-generated method stub
						post(order);
						mOrderDialog.dismiss();
					}
				});*/
		mOrderDialog.showDialog();
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

/*	private void post(Order order) {
		// TODO Auto-generated method stub
		HttpApi.order(this, order, mOnRequestListener);
	}

	Handler mHandler = new Handler();
	public OnRequestListener mOnRequestListener = new OnRequestListener() {

		@Override
		public void onResponse(String url, final int state,
				final Object result, int type) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if ((state == HttpConnectManager.STATE_SUC)
							&& (result != null)) {
						OrderPost mOrderPost;
						mOrderPost = (OrderPost) result;
						if (mOrderPost != null) {
							Toast.makeText(StoreMapActivity.this,
									mOrderPost.getMessage(), Toast.LENGTH_SHORT)
									.show();
						}
					 else {

							toast(getResources().getString(R.string.request_fail));
						}
					
				} else if (state == HttpConnectManager.STATE_TIME_OUT) { // 请求超时
					toast(getResources().getString(R.string.request_fail));
					}
					Toast.makeText(getActivity(), R.string.time_out, Toast.LENGTH_SHORT).show();
					mListView.setPullLoadEnable(false);
				} else { // 请求失败
					if ((mPageNum == 1) && ((mMessages == null) || (mMessages.size() == 0))) {
						setNotNetVisible(View.VISIBLE, mListView);
					}
					Toast.makeText(getActivity(), R.string.request_fail, Toast.LENGTH_SHORT).show();
					mListView.setPullLoadEnable(false);

				}

				}
			});
		}
	};
	void toast(String message){
	Toast.makeText(this, message, Toast.LENGTH_SHORT).show();	
	}*/
}
