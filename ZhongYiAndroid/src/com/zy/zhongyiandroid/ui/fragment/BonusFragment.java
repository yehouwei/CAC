package com.zy.zhongyiandroid.ui.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.NetCache;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.Api.ServerUrl;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.Order;
import com.zy.zhongyiandroid.data.bean.UserInfo;
import com.zy.zhongyiandroid.data.shared.UserData;
import com.zy.zhongyiandroid.ui.activity.MyBonusActivity;
import com.zy.zhongyiandroid.ui.activity.MyIntroduceActivity;
import com.zy.zhongyiandroid.ui.adapter.OrderListAdapter;
import com.zy.zhongyiandroid.ui.dialog.OrderDialog;
import com.zy.zhongyiandroid.ui.widget.CircleBonusBar;
import com.zy.zhongyiandroid.ui.widget.Header;
import com.zy.zhongyiandroid.ui.widget.LoadingInfo.OnRefreshClickListener;
import com.zy.zhongyiandroid.ui.widget.list.XListView;
import com.zy.zhongyiandroid.ui.widget.list.XListView.IXListViewListener;

/**
 * 消息
 * 
 * @author Seven
 * 
 */
public class BonusFragment extends BaseFragment {
	CircleBonusBar mCircleBonusBar;
	private int i = 0;
	Context context;

	TextView tvBonus;

	Dialog dialog;

	EditText etUser;

	EditText etPassword;

	Button btnPositive;

	Button btnCancle;

	UserInfo user;

	UserData userData = new UserData();

	private String btnBackString;

	RelativeLayout mRelativeLayout;

	public BonusFragment(Context context, String name) {
		// TODO Auto-generated constructor stub
		this.context = context;
		btnBackString = name;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_mybonus, null);
		initHeader(view);
		// 初始化ui\
		initUI(view);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		request();
	}

	public static void startActivity(Context c) {
		Intent i = new Intent(c, MyIntroduceActivity.class);
		c.startActivity(i);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * 初始化UI
	 */
	private void initUI(View view) {
		// TODO Auto-generated method stub
		mRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.progressBonus);
		mCircleBonusBar = (CircleBonusBar) view
				.findViewById(R.id.circleBonusBar);
		tvBonus = (TextView) view.findViewById(R.id.bonus);

		initLoadingInfo(view);
		setOnRefreshClickListener(mOnRefreshClickListener);
	}

	private void initHeader(View v) {
		Header mHeader = (Header) v.findViewById(R.id.header);
		if (mHeader != null) {
			mHeader.setTitle(R.string.personal_bonus);
			mHeader.setBackBtn(btnBackString, new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getActivity().finish();
				}
			});
		}

	}

	private void initData(UserInfo userInfo) {
		mCircleBonusBar.setProgress(Integer.parseInt(userInfo.getScore()));
		mCircleBonusBar.setMax(1000);
		tvBonus.setText(userInfo.getScore());
		mCircleBonusBar.invalidate();
	}

	void toast(String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	private void request() {
		if (user == null) {
			setNotDataVisible(View.VISIBLE, mRelativeLayout);
		}
		HttpApi.getBonus(context, userData.getUserId(), mOnRequestListener);
	}

	private OnRequestListener mOnRequestListener = new OnRequestListener() {

		@Override
		public void onResponse(String url, final int state,
				final Object result, int type) {

			if (!isAdded()) // fragment 已退出,返回
			{
				return;
			}
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if ((state == HttpConnectManager.STATE_SUC)
							&& (result != null)) {

						UserInfo userInfo;
						userInfo = (UserInfo) result;

						if (userInfo != null) {
							user = userInfo;
							initData(user);
							setLoadInfoGone(mRelativeLayout);

						} else {

							setNotDataVisible(View.VISIBLE, mRelativeLayout);
						}

					} else if (state == HttpConnectManager.STATE_TIME_OUT) { // 请求超时

						setNotNetVisible(View.VISIBLE, mRelativeLayout);

						Toast.makeText(getActivity(), R.string.time_out,
								Toast.LENGTH_SHORT).show();

					} else { // 请求失败

						setNotNetVisible(View.VISIBLE, mRelativeLayout);

						Toast.makeText(getActivity(), R.string.request_fail,
								Toast.LENGTH_SHORT).show();

					}
				}
			});

		}
	};
	Handler mHandler = new Handler();

	private OnRefreshClickListener mOnRefreshClickListener = new OnRefreshClickListener() {

		@Override
		public void onClick(View v) {
			request();

		}
	};
}
