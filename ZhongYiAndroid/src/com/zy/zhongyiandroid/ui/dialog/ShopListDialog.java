package com.zy.zhongyiandroid.ui.dialog;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.ZhongYi;
import com.zy.zhongyiandroid.data.bean.Store;
import com.zy.zhongyiandroid.ui.activity.MyOrderActivity;
import com.zy.zhongyiandroid.ui.adapter.ShoplistDialogAdapter;

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

public class ShopListDialog extends Dialog {

	ListView mListView;
	OnDialogClickListener mOnDialogClickListener;

	TextView mTvCancel;
	TextView mTvOK;
	Context context;
	List<Store> stores;

	public ShopListDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		ZhongYi mZhongYi = ((ZhongYi) context.getApplicationContext());
		stores = mZhongYi.getStoreList();
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_listview);
		initUI();
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
		ShoplistDialogAdapter mShoplistDialogAdapter = new ShoplistDialogAdapter(
				context);
		mShoplistDialogAdapter.setDatas(stores);
		mListView.setAdapter(mShoplistDialogAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				dismiss();
				mOnDialogClickListener.onClick(view, stores.get(position));

			}

		});

	}

	public void setOnDialogClickListener(OnDialogClickListener listener) {
		mOnDialogClickListener = listener;

	}

	public interface OnDialogClickListener {
		void onClick(View v, Store store);
	}
}