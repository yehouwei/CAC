package com.zy.zhongyiandroid.ui.dialog;

import java.util.Calendar;
import java.util.List;



import android.R.integer;
import android.app.Dialog;
import android.content.Context;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.encore.libs.widget.picker.TimePicker;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.bean.Message;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.OrderPost;
import com.zy.zhongyiandroid.data.bean.UserInfo;
import com.zy.zhongyiandroid.data.bean.UserLoginResult;
import com.zy.zhongyiandroid.data.shared.UserData;



public class UserLoginDialog extends Dialog {


	OnLoginDialogClickListener mOnDialogClickListener;
	
	EditText etUser;

	EditText etPassword;

	Button btnPositive;

	Button btnCancle;
	
	Context context;
	
	UserInfo userInfo;


	public UserLoginDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context=context;
		setContentView(R.layout.dialog_user_login);
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
		
		
		
		btnPositive = (Button) findViewById(R.id.btnPositive);
		btnCancle = (Button) findViewById(R.id.btnCancle);
		etUser = (EditText) findViewById(R.id.etUsers);
		etPassword = (EditText) findViewById(R.id.EtPassWord);

		btnPositive.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (etUser.getText().toString().equals("")
						|| etPassword.getText().toString().equals("")) {
					Toast.makeText(context, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
				
				} else {
					request();
				}
			}
		});
		btnCancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();

			}
		});
	}
		
	private void request() {
		// TODO Auto-generated method stub
		HttpApi.getLogin(context, etUser.getText().toString(), etPassword.getText().toString(), mOnRequestListener);
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
						UserLoginResult userLoginResult = (UserLoginResult) result;
						if(userLoginResult.getUserInfo()!=null){
							userInfo = (UserInfo)userLoginResult.getUserInfo();
							toast(userLoginResult.getMessage());
							UserData userData = new UserData();
							userData.setisShowLogin(userLoginResult.getIsLogin());
							userData.setUserAccount(etUser.getText().toString());
							userData.setUserScore(userInfo.getScore());
							userData.setUserId(userInfo.getUserId());
							dismiss();
							mOnDialogClickListener.startActivity();
							
						}else{
							toast("登录失败");
						}
						
					}
				}
				});
			}
	};
	
	public void setOnLoginDialogClickListener(OnLoginDialogClickListener listener){
		mOnDialogClickListener = listener;
		
	}
	
	public interface OnLoginDialogClickListener{
		void startActivity();
	}

	void toast(String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
}