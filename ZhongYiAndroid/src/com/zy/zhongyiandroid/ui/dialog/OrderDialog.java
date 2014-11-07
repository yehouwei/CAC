package com.zy.zhongyiandroid.ui.dialog;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.R.integer;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.ZhongYi;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.Order;
import com.zy.zhongyiandroid.data.bean.OrderPost;
import com.zy.zhongyiandroid.data.bean.Store;
import com.zy.zhongyiandroid.ui.dialog.OrderDateDialog.OnDateDialogClickListener;
import com.zy.zhongyiandroid.ui.dialog.OrderTimeDialog.OnTimeDialogClickListener;
import com.zy.zhongyiandroid.ui.dialog.ShopListDialog.OnDialogClickListener;

public class OrderDialog extends Dialog {
	Context context;
	OnOrderDialogClickListener mOnDialogClickListener;

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

	Calendar mCalendar;

	Order mOrder;
	OrderPost mOrderPost;
	List<Store> stores;

	Boolean isNew = true;

	String mdate;
	String mtime;

	public OrderDialog(Context context, int theme, Order order) {
		super(context, theme);
		this.context = context;
		ZhongYi mZhongYi = ((ZhongYi) context.getApplicationContext());
		stores = mZhongYi.getStoreList();
		if (order == null) {

			mOrder = new Order();

		} else {
			this.isNew = false;
			this.mOrder = order;
		}
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_order);

		mCalendar = Calendar.getInstance();
		initUI();
	}

	/*
	 * public void setCurDate(Time time){ mCalendar.set(Calendar.HOUR,
	 * calendar.get(Calendar.HOUR_OF_DAY)); mCalendar.set(Calendar.MINUTE,
	 * calendar.get(Calendar.MINUTE)); mTimePicker.setCalendar(mCalendar); }
	 */

	public void showDialog() {
		// 设置触摸对话框意外的地方取消对话框
		setCanceledOnTouchOutside(true);
		// mTimePicker.setIs24Hour(false);
		show();
	}
/*
	public void setShopData(String[] stores) {
		this.stores = stores;
	}*/

	private void initUI() {

		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		dialogWindow.setAttributes(lp);
		dialogWindow.setGravity(Gravity.CENTER);
		setCanceledOnTouchOutside(true);

		RelativeLayout rlShop = (RelativeLayout) findViewById(R.id.rlShop);
		RelativeLayout rlEvent = (RelativeLayout) findViewById(R.id.rlEvent);
		tvShop = (TextView) findViewById(R.id.tvshop);
		tvEvent = (TextView) findViewById(R.id.tvEvent);
		tvShop.setText(stores.get(0).getName());
		tvEvent.setText(context.getResources().getStringArray(R.array.event)[0]);
		imgShopFliter = (ImageView) findViewById(R.id.imgShopFliter);
		imgEventFliter = (ImageView) findViewById(R.id.imgEventFliter);
		etName = (EditText) findViewById(R.id.etName);
		etPhone = (EditText) findViewById(R.id.etPhone);
		etEmail = (EditText) findViewById(R.id.etEmail);
		btnDate = (Button) findViewById(R.id.btnDate);
		btnTime = (Button) findViewById(R.id.btnTime);
		btnPostive = (Button) findViewById(R.id.btnPositive);
		btnCancle = (Button) findViewById(R.id.btnCancle);
		btnCancle.setOnClickListener(clickListener);

		if (isNew) {
			rlShop.setOnTouchListener(onTouchListener);
			rlEvent.setOnTouchListener(onTouchListener);
			btnDate.setOnClickListener(clickListener);
			btnTime.setOnClickListener(clickListener);
			btnPostive.setOnClickListener(clickListener);
		} else {
			btnPostive.setText("删除");
			btnPostive.setOnClickListener(clickListener);
			tvShop.setText(mOrder.getShopName());
			tvEvent.setText(mOrder.getAppellation());
			etName.setText(mOrder.getUserName());
			etPhone.setText(mOrder.getPhone());
			etEmail.setText(mOrder.getEmail());
		}

		/*
		 * btnPostive.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { dismiss();
		 * 
		 * mCalendar.set(Calendar.HOUR, mTimePicker.getHour());
		 * mCalendar.set(Calendar.MONTH, mTimePicker.getMinute());
		 * 
		 * mOnDialogClickListener.onClick(v,
		 * mCalendar.get(Calendar.HOUR),mCalendar
		 * .get(Calendar.MINUTE),mTimePicker.getTime()); } });
		 */

	}

	OnTouchListener onTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.rlShop:
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					final ShopListDialog mlistDialog = new ShopListDialog(context,
							R.style.MyDialog);
					mlistDialog.
							setOnDialogClickListener(new OnDialogClickListener() {

								@Override
								public void onClick(View v, Store store) {
									// TODO Auto-generated method stub
									tvShop.setText(store.getName());
									mOrder.setShopName(store.getName());
									mOrder.setShopId(store.getId());
									mlistDialog.dismiss();
								}
							});
					mlistDialog.showDialog();
				}

				break;

			case R.id.rlEvent:
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					final ListDialog mlistDialog = new ListDialog(context,
							R.style.MyDialog, context.getResources()
									.getStringArray(R.array.event));
					mlistDialog
							.setOnDialogClickListener(new ListDialog.OnDialogClickListener() {

								@Override
								public void onClick(View v, String item) {
									// TODO Auto-generated method stub
									tvEvent.setText(item);
									mOrder.setAppellation(item);
									mlistDialog.dismiss();

								}
							});
					mlistDialog.showDialog();
				}
				break;
			}
			return false;
		}
	};

	View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnCancle:
				dismiss();
				break;

			case R.id.btnDate:
				final OrderDateDialog mdateDialog = new OrderDateDialog(
						context, R.style.MyDialog);
				mdateDialog
						.setOnDateDialogClickListener(new OnDateDialogClickListener() {

							@Override
							public void onClick(View v, Date date) {
								// TODO Auto-generated method stub
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd");
								btnDate.setText(sdf.format(date));
								mdate = sdf.format(date);
								mdateDialog.dismiss();
							}
						});
				mdateDialog.showDialog();
				break;
			case R.id.btnTime:
				final OrderTimeDialog mTimeDialog = new OrderTimeDialog(
						context, R.style.MyDialog);
				mTimeDialog
						.setOnTimeDialogClickListener(new OnTimeDialogClickListener() {

							@Override
							public void onClick(View v, int hour, int minute,int second,
									String time) {
								// TODO Auto-generated method stub
								btnTime.setText(time);
								mtime = time;
								mTimeDialog.dismiss();
							}
						});
				mTimeDialog.showDialog();
				break;

			case R.id.btnPositive:
				if (isNew) {
					if (etEmail.getText().toString().equals("")
							|| mtime == null || mdate == null
							|| etName.getText().toString().equals("")
							|| etPhone.getText().toString().equals("")) {
						toast("请输入完整的信息");
					} else {
						mOrder.setArrageDateTime(mdate +" "+ mtime);
						mOrder.setAppellation(etName.getText().toString());
						mOrder.setPhone(etPhone.getText().toString());
						mOrder.setEmail(etEmail.getText().toString());
						mOrder.setShopName(tvShop.getText().toString());
						mOrder.setRemarks(tvEvent.getText().toString());
						mOrder.setStatus("0");
						mOrder.setUserId(1);
						mOnDialogClickListener.onClick(v, mOrder);
						
					}
				} else {

				}
				break;
			}
		}


	};

	public void setOnOrderDialogClickListener(
			OnOrderDialogClickListener listener) {
		mOnDialogClickListener = listener;

	}

	public interface OnOrderDialogClickListener {
		void onClick(View v, Order order);

	}

	public void toast(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

	}
	private void post() {
		// TODO Auto-generated method stub
		HttpApi.order(context, mOrder, 
				mOnRequestListener);
	}
	Handler mHandler=new Handler();
	public OnRequestListener mOnRequestListener = new OnRequestListener() {

		@Override
		public void onResponse(String url, final int state,
				final Object result, int type) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if ((state == HttpConnectManager.STATE_SUC)
							&& (result != null)) {
							mOrderPost=(OrderPost)result;
							
							toast(mOrderPost.getMessage());
						}


					}
				});
			}
	};
		
	
}