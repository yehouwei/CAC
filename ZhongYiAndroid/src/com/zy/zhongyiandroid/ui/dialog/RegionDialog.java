package com.zy.zhongyiandroid.ui.dialog;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.Region;
import com.zy.zhongyiandroid.data.bean.Store;
import com.zy.zhongyiandroid.ui.activity.MyOrderActivity;
import com.zy.zhongyiandroid.ui.adapter.RegionDialogAdapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
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

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.encore.libs.widget.picker.DatePicker;

public class RegionDialog extends Dialog {

	ListView mListView;
	OnDialogClickListener mOnDialogClickListener;

	RegionDialogAdapter mDialogAdapter;

	List<Region> mRegions;

	Calendar mCalendar;

	Context context;

	private int mPageNum = 1;

	private int mPageSize = 5;

	private boolean isRequesEnd = true;

	public RegionDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_listview);
		initUI();
	}


	public void setCurDate(Date date) {
		mCalendar.setTime(date);
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
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		dialogWindow.setAttributes(lp);
		dialogWindow.setGravity(Gravity.CENTER);
		setCanceledOnTouchOutside(true);

		mListView = (ListView) findViewById(R.id.lvDialog);
		/*
		 * ArrayAdapter<String> adapter = new ArrayAdapter<String>( context,
		 * R.layout.adapter_dialog_simplelist, list);
		 */
		mDialogAdapter = new RegionDialogAdapter(context);
		mListView.setAdapter(mDialogAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				dismiss();
				mOnDialogClickListener.onClick(view, mRegions.get(position));

			}

		});
		request();

	}

	public void request() {
		if (!isRequesEnd) {
			return;
		}

		isRequesEnd = false; // 改变正在请求的标识
		/*
		 * if ((mStores == null) || (mStores.size() == 0)) {
		 * setLoadingViewVisible(View.VISIBLE, mListView); }
		 */
		HttpApi.getRegion(context, mOnRequestListener);
	}

	public OnRequestListener mOnRequestListener = new OnRequestListener() {

		@Override
		public void onResponse(String url, final int state,
				final Object result, int type) {
			// mIsFirstLoad = false;
			isRequesEnd = true;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if ((state == HttpConnectManager.STATE_SUC)
							&& (result != null)) {

						List<Region> regions = (List<Region>) result;

						if (regions != null && regions.size() != 0) {
							mRegions=regions;
							mDialogAdapter.setDatas(mRegions);
							mDialogAdapter.notifyDataSetChanged();

						}
					}
				}
			});

		}
	};

	public void setOnDialogClickListener(OnDialogClickListener listener) {
		mOnDialogClickListener = listener;

	}

	public interface OnDialogClickListener {
		void onClick(View v, Region region);
	}

	Handler mHandler = new Handler();

}