package com.zy.zhongyiandroid.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Order;
import com.zy.zhongyiandroid.ui.adapter.OrderListAdapter;
import com.zy.zhongyiandroid.ui.dialog.OrderDialog;
import com.zy.zhongyiandroid.ui.dialog.OrderDialog.OnOrderDialogClickListener;
import com.zy.zhongyiandroid.ui.widget.Header;
import com.zy.zhongyiandroid.ui.widget.list.XListView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MyOrderActivity extends BaseActivity {
	Button btnOrder;

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

	/*
	 * btnOrder.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub initDialog(isNew); } }); }
	 * 
	 * private void initDialog(Boolean isNew) { final Dialog dialog; dialog =
	 * new Dialog(MyOrderActivity.this, R.style.MyDialog);
	 * dialog.setContentView(R.layout.dialog_order); RelativeLayout
	 * rlShop=(RelativeLayout)dialog.findViewById(R.id.rlShop); RelativeLayout
	 * rlEvent=(RelativeLayout)dialog.findViewById(R.id.rlEvent);
	 * OnTouchListener onTouchListener=new OnTouchListener() {
	 * 
	 * @Override public boolean onTouch(View v, MotionEvent event) { // TODO
	 * Auto-generated method stub switch (v.getId()) { case R.id.rlShop:
	 * if(event.getAction() == MotionEvent.ACTION_DOWN){
	 * initShopDialog(getResources().getStringArray(R.array.loacation)); }
	 * break;
	 * 
	 * case R.id.rlEvent: if(event.getAction() == MotionEvent.ACTION_DOWN){
	 * initEventDialog(getResources().getStringArray(R.array.event)); } break; }
	 * return false; } }; rlShop.setOnTouchListener(onTouchListener);
	 * rlEvent.setOnTouchListener(onTouchListener); tvShop = (TextView)
	 * dialog.findViewById(R.id.tvshop); tvEvent = (TextView)
	 * dialog.findViewById(R.id.tvEvent); imgShopFliter = (ImageView)
	 * dialog.findViewById(R.id.imgShopFliter); imgEventFliter = (ImageView)
	 * dialog.findViewById(R.id.imgEventFliter); etName = (EditText)
	 * dialog.findViewById(R.id.etName); etPhone = (EditText)
	 * dialog.findViewById(R.id.etPhone); etEmail = (EditText)
	 * dialog.findViewById(R.id.etEmail); btnDate = (Button)
	 * dialog.findViewById(R.id.btnDate); btnTime = (Button)
	 * dialog.findViewById(R.id.btnTime); btnPostive = (Button)
	 * dialog.findViewById(R.id.btnPositive); btnCancle = (Button)
	 * dialog.findViewById(R.id.btnCancle); btnPostive.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub dialog.dismiss(); order =new Order(); order.setStatus(1);
	 * order.setShopName(tvShop.getText().toString());
	 * order.setArrageDateTime(btnDate
	 * .getText().toString()+btnTime.getText().toString());
	 * order.setPhone(etPhone.getText().toString());
	 * order.setEmail(etEmail.getText().toString());
	 * order.setAppellation(tvEvent.getText().toString());
	 * order.setShopImages("222"); mOrderlist.add(order);
	 * mOrderListAdapter.setDatas(mOrderlist);
	 * mOrderListAdapter.notifyDataSetChanged();
	 * 
	 * } }); btnCancle.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub dialog.dismiss(); } }); btnDate.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * TextView tvShop;
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub initDateDialog();
	 * 
	 * } });
	 * 
	 * btnTime.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub initTimedDialog();
	 * 
	 * } });
	 * 
	 * dialog.show(); }
	 */
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

	/*
	 * private void initShopDialog(final String[] list) {
	 * 
	 * // TODO Auto-generated method stub listDialog = new Dialog(this,
	 * R.style.MyDialog); listDialog.setContentView(R.layout.dialog_listview);
	 * ListView mListView = (ListView) listDialog.findViewById(R.id.lvDialog);
	 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(
	 * MyOrderActivity.this, R.layout.adapter_dialog_simplelist, list);
	 * mListView.setAdapter(adapter); mListView.setOnItemClickListener(new
	 * OnItemClickListener() {
	 * 
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) { // TODO Auto-generated method stub result =
	 * list[position]; listDialog.dismiss(); tvShop.setText(result); } });
	 * listDialog.show(); } private void initEventDialog(final String[] list) {
	 * 
	 * // TODO Auto-generated method stub listDialog = new Dialog(this,
	 * R.style.MyDialog); listDialog.setContentView(R.layout.dialog_listview);
	 * ListView mListView = (ListView) listDialog.findViewById(R.id.lvDialog);
	 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(
	 * MyOrderActivity.this, R.layout.adapter_dialog_simplelist, list);
	 * mListView.setAdapter(adapter); mListView.setOnItemClickListener(new
	 * OnItemClickListener() {
	 * 
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) { // TODO Auto-generated method stub result =
	 * list[position]; listDialog.dismiss(); tvEvent.setText(result); } });
	 * listDialog.show(); }
	 * 
	 * private void initDateDialog() { final Dialog dateDialog = new
	 * Dialog(this, R.style.MyDialog);
	 * dateDialog.setContentView(R.layout.dialog_date_order); final DatePicker
	 * datePicker = (DatePicker) dateDialog .findViewById(R.id.datepicker);
	 * Button btnDatePositive = (Button) dateDialog
	 * .findViewById(R.id.btnPositive); Button btnDateCancle = (Button)
	 * dateDialog.findViewById(R.id.btnCancle);
	 * btnDatePositive.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub
	 * 
	 * dateDialog.dismiss(); btnDate.setText(datePicker.getYear() + "年" +
	 * (datePicker.getMonth() + 1) + "月" + datePicker.getDayOfMonth() + "日"); }
	 * });
	 * 
	 * btnDateCancle.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub dateDialog.dismiss();
	 * 
	 * } }); dateDialog.show(); final OrderDateDialog mDateDialog=new
	 * OrderDateDialog(this, R.style.MyDialog);
	 * mDateDialog.setOnDialogClickListener(new OnDialogClickListener() {
	 * 
	 * @Override public void onClick(View v, Date date) { // TODO Auto-generated
	 * method stub SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	 * btnDate.setText(sdf.format(date));
	 * 
	 * } }); mDateDialog.showDialog();
	 * 
	 * }
	 * 
	 * private void initTimedDialog() { // TODO Auto-generated method stub final
	 * OrderTimeDialog timeDialog = new OrderTimeDialog(this, R.style.MyDialog);
	 * timeDialog.setOnDialogClickListener(new OnTimeDialogClickListener() {
	 * 
	 * @Override public void onClick(View v, int hour, int minute, String time)
	 * { // TODO Auto-generated method stub timeDialog.dismiss();
	 * btnTime.setText(time); } }); timeDialog.showDialog();
	 * 
	 * 
	 * timeDialog.setContentView(R.layout.dialog_time_order); final TimePicker
	 * timePicker = (TimePicker) timeDialog .findViewById(R.id.Timepicker);
	 * Button btnDatePositive = (Button) timeDialog
	 * .findViewById(R.id.btnPositive); Button btnDateCancle = (Button)
	 * timeDialog.findViewById(R.id.btnCancle);
	 * btnDatePositive.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub
	 * 
	 * timeDialog.dismiss();
	 * btnTime.setText(timePicker.getCurrentHour().toString
	 * ()+":"+timePicker.getCurrentMinute().toString());
	 * 
	 * } });
	 * 
	 * btnDateCancle.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub timeDialog.dismiss();
	 * 
	 * } }); timeDialog.show();
	 * 
	 * }
	 * 
	 * OnClickListener onclicklistener = new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub switch (v.getId()) { case R.id.btnPositive:
	 * 
	 * break;
	 * 
	 * case R.id.btnDate: initDateDialog(); break; case R.id.btnTime:
	 * initTimedDialog(); break; case R.id.btnCancle: break; }
	 * 
	 * }
	 * 
	 * };
	 */

	public static void startActivity(Context c, String value) {
		Intent i = new Intent(c, MyOrderActivity.class);
		i.putExtra("name", value);
		c.startActivity(i);
	}
}
