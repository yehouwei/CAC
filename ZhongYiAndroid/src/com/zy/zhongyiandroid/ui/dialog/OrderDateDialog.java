package com.zy.zhongyiandroid.ui.dialog;

import java.util.Calendar;
import java.util.Date;

import com.zy.zhongyiandroid.R;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.encore.libs.widget.picker.DatePicker;


public class OrderDateDialog extends Dialog {
	OnDateDialogClickListener mOnDialogClickListener;
	
	TextView mTvCancel;
	TextView mTvOK;
	DatePicker mDatePicker;
	
	Calendar mCalendar;


	public OrderDateDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_date_order);
		mCalendar = Calendar.getInstance();
		initUI();
	}
	
	public void setCurDate(Date date){
		mCalendar.setTime(date);
		mDatePicker.setCalendar(mCalendar);
	}

	public void showDialog() {
		// 设置触摸对话框意外的地方取消对话框
		setCanceledOnTouchOutside(true);
		show();
	}

	private void initUI() {


		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
		lp.height  = ViewGroup.LayoutParams.WRAP_CONTENT;
		dialogWindow.setAttributes(lp);
		dialogWindow.setGravity(Gravity.CENTER);
		setCanceledOnTouchOutside(true);
		

		mTvOK = (TextView) findViewById(R.id.btnPositive);
		mDatePicker = (DatePicker) findViewById(R.id.datepicker);
		
		mTvOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				mCalendar.set(Calendar.YEAR, mDatePicker.getYear());
				mCalendar.set(Calendar.MONTH, mDatePicker.getMonth());
				mCalendar.set(Calendar.DAY_OF_MONTH, mDatePicker.getDay());
				mOnDialogClickListener.onClick(v, mCalendar.getTime());
			}
		});
		
	}
	
	public void setOnDateDialogClickListener(OnDateDialogClickListener listener){
		mOnDialogClickListener = listener;
		
	}
	
	public interface OnDateDialogClickListener{
		void onClick(View v,Date date);
	}
}