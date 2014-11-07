package com.zy.zhongyiandroid.ui.widget;

import java.util.List;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.bean.Item;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.User;
import com.zy.zhongyiandroid.ui.activity.MyBonusActivity;

import android.R.integer;
import android.accounts.Account;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserLoginDialog extends Dialog {
	Context context;
	Button btnPositivie;
	EditText etAccount;
	Button btnCancle;
	EditText etPassword;

	private Boolean userEnable;
	private int Account;
	private String Password;
	private int score = 0;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Boolean getUserEnable() {
		return userEnable;
	}

	public void setUserEnable(Boolean userEnable) {
		this.userEnable = userEnable;
	}

	public int getAccount() {
		return Account;
	}

	public void setAccount(int account) {
		Account = account;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public UserLoginDialog(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public UserLoginDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_user_login);
		btnPositivie = (Button) this.findViewById(R.id.btnPositive);
		btnCancle = (Button) this.findViewById(R.id.btnCancle);
		etAccount = (EditText) this.findViewById(R.id.etUsers);
		etPassword = (EditText) this.findViewById(R.id.EtPassWord);
		btnPositivie.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (etAccount.getText().toString().equals(null)
						&& etAccount.getText().toString().length() == 0) {
					Toast.makeText(context, "请输入正确的账号/密码", Toast.LENGTH_SHORT)
							.show();
				} else if (etPassword.getText().toString().equals(null)
						&& etPassword.getText().toString().length() == 0) {
					Toast.makeText(context, "请输入正确的账号/密码", Toast.LENGTH_SHORT)
							.show();
				} else {
					request();
				}
			}
		});
		btnCancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setUserEnable(false);
				/* ((Activity)context).finish(); */
				cancel();

			}
		});

	}

	public void request() {
		/*
		 * if (!isRequesEnd) { return; }
		 */

		/*
		 * isRequesEnd = false; // 改变正在请求的标识 if ((mPageNum == 1) && ((mSort ==
		 * null) || (mSort.size() == 0))) { setLoadingViewVisible(View.VISIBLE,
		 * mGridView); }
		 */
		HttpApi.getUser(context, getAccount(), mOnRequestListener);
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
						MyApiResult myApiResult = (MyApiResult) result;
						List<User> users = null;
						if (myApiResult.getRows() != null) {
							users = (List<User>) myApiResult.getRows();
						} else {
							Toast.makeText(context, "账号不存在", Toast.LENGTH_SHORT)
									.show();
						}

						if (users != null && users.size() != 0) {
							if (users.get(0).getUserPassword()
									.equals(getPassword())) {
								dismiss();
								setScore(users.get(0).getScore());
							} else {
								Toast.makeText(context, "密码不正确",
										Toast.LENGTH_SHORT).show();
							}

						}

					}
				}
			});

		}
	};

	private void initdata() {
		// TODO Auto-generated method stub

	}

}
