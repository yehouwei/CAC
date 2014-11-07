package com.zy.zhongyiandroid.ui.dialog;

import java.util.Calendar;



import android.R.integer;
import android.app.Dialog;
import android.content.Context;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.encore.libs.widget.picker.TimePicker;

import com.zy.zhongyiandroid.R;



public class OrderTimeDialog extends Dialog {


	OnTimeDialogClickListener mOnDialogClickListener;
	
	TextView mTvCancel;
	TextView mTvOK;
	TimePicker mTimePicker;
	
	Calendar mCalendar;


	public OrderTimeDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_time_order);
		mCalendar = Calendar.getInstance();
		initUI();
	}
	
/*	public void setCurDate(Time time){
		mCalendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR_OF_DAY));
		mCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
		 mTimePicker.setCalendar(mCalendar);
	}*/

	public void showDialog() {
		// 设置触摸对话框意外的地方取消对话框
		setCanceledOnTouchOutside(true);
		//mTimePicker.setIs24Hour(false);
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
		mTimePicker = (TimePicker) findViewById(R.id.Timepicker);
	
		
		mTvOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				
				mCalendar.set(Calendar.HOUR, mTimePicker.getHour());
				mCalendar.set(Calendar.MINUTE, mTimePicker.getMinute());
				mCalendar.set(Calendar.SECOND,30);
				String mtime=mCalendar.get(Calendar.HOUR)+":"+mCalendar.get(Calendar.MINUTE)+":"+mCalendar.get(Calendar.SECOND);
				
				
				mOnDialogClickListener.onClick(v, mCalendar.get(Calendar.HOUR),mCalendar.get(Calendar.MINUTE),mCalendar.get(Calendar.SECOND),mtime);
			}
		});
		
	}
	
	public void setOnTimeDialogClickListener(OnTimeDialogClickListener listener){
		mOnDialogClickListener = listener;
		
	}
	
	public interface OnTimeDialogClickListener{
		void onClick(View v,int hour,int minute,int second,String time);
	}
}