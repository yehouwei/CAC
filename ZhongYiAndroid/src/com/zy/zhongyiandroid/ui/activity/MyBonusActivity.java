package com.zy.zhongyiandroid.ui.activity;

import java.util.List;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.UserInfo;
import com.zy.zhongyiandroid.data.shared.UserData;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyBonusActivity extends BaseActivity {
	CircleBonusBar mCircleBonusBar;
	private int i=0;
	Context context;

	TextView tvBonus;

	Dialog dialog;

	EditText etUser;

	EditText etPassword;

	Button btnPositive;

	Button btnCancle;
	
	UserInfo user;
	
	UserData userData=new UserData();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_mybonus);
		initHeader();
		initUI();

	}

	private void intitdialog() {
		// TODO Auto-generated method stub
		dialog=new Dialog(MyBonusActivity.this,R.style.MyDialog);
		dialog.setContentView(R.layout.dialog_user_login);
		dialog.setCanceledOnTouchOutside(false);
		btnPositive = (Button) dialog.findViewById(R.id.btnPositive);
		btnCancle = (Button) dialog.findViewById(R.id.btnCancle);
		etUser = (EditText) dialog.findViewById(R.id.etUsers);
		etPassword = (EditText) dialog.findViewById(R.id.EtPassWord);
		dialog.show();
		
		btnPositive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(etUser.getText().toString().equals("")||etPassword.getText().toString().equals("")){
					toast("用户名或密码不能为空");
				}else {
				/*	request();*/
				}
			}
		});
		btnCancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				finish();
			}
		});
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		mCircleBonusBar = (CircleBonusBar) this
				.findViewById(R.id.circleBonusBar);
		tvBonus = (TextView) this.findViewById(R.id.bonus);
			mCircleBonusBar.setMax(100); 
			tvBonus.setText(userData.getUserScore());

	}

	public static void startActivity(Context c,String value) {
		Intent i = new Intent(c, MyBonusActivity.class);
		i.putExtra("name", value);
		c.startActivity(i);
	}

	public void initHeader() {
		Header mHeader = (Header) this.findViewById(R.id.header);
		if (mHeader != null) {
			mHeader.setTitle(R.string.personal_bonus);
			mHeader.setBackBtn(getIntent().getStringExtra("name"), new View.OnClickListener() {
				
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

	void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

}
