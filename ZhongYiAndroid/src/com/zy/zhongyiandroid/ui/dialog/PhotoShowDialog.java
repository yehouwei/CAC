package com.zy.zhongyiandroid.ui.dialog;

import java.util.Calendar;
import java.util.Date;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.ui.activity.MyOrderActivity;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.encore.libs.widget.picker.DatePicker;


public class PhotoShowDialog extends Dialog {
	
	ListView mListView;
	OnDialogClickListener mOnDialogClickListener;
	
	TextView mTvCancel;
	TextView mTvOK;
	DatePicker mDatePicker;
	
	String[] list;
	
	Calendar mCalendar;
	
	Context context;


	public PhotoShowDialog(Context context, int theme,String[] list) {
		super(context, theme);
		this.context=context;
		this.list=list;
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_listview);
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
		
		mListView = (ListView) findViewById(R.id.lvDialog);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				context, R.layout.adapter_dialog_simplelist, list);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				dismiss();
				mOnDialogClickListener.onClick(view, list[position]);
				
			}

		});

		
	}
	
	public void setOnDialogClickListener(OnDialogClickListener listener){
		mOnDialogClickListener = listener;
		
	}
	
	public interface OnDialogClickListener{
		void onClick(View v,String item);
	}
}