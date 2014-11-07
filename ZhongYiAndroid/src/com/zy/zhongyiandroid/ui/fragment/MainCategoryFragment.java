package com.zy.zhongyiandroid.ui.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.NetCache;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.Api.ServerUrl;
import com.zy.zhongyiandroid.data.bean.BaseCategory;
import com.zy.zhongyiandroid.data.bean.Message;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.Store;
import com.zy.zhongyiandroid.ui.activity.MyIntroduceActivity;
import com.zy.zhongyiandroid.ui.activity.SubCategoryActivity;
import com.zy.zhongyiandroid.ui.adapter.MessageAdapter;
import com.zy.zhongyiandroid.ui.adapter.MainCategoryAdapter;
import com.zy.zhongyiandroid.ui.widget.Header;
import com.zy.zhongyiandroid.ui.widget.LoadingInfo.OnRefreshClickListener;
import com.zy.zhongyiandroid.ui.widget.PullToRefreshView;
import com.zy.zhongyiandroid.ui.widget.PullToRefreshView.OnFooterRefreshListener;
import com.zy.zhongyiandroid.ui.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.zy.zhongyiandroid.ui.widget.list.XListView;
import com.zy.zhongyiandroid.ui.widget.list.XListView.IXListViewListener;

/**
 * 消息
 * 
 * @author Seven
 * 
 */
public class MainCategoryFragment extends BaseFragment {
	// 第一页
	public final int FRIST_PAGE_NUMBER = 1;

	public final static int mPageSize = 12;

	private GridView mGridView;

	PullToRefreshGridView mPullToRefreshGridView;

	private List<BaseCategory> mBaseCategories = new ArrayList<BaseCategory>();

	private MainCategoryAdapter mBaseListAdapter;

	// 请求是否已经结束
	private boolean isRequesEnd = true;

	public final static String TAG = "MainCategoryFragment";

	boolean mIsFirstLoad = true;
	
	//Sort mSort = new Sort();
	
	BaseCategory mBaseCategory;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sort, null);

		// 初始化ui\
		initUI(view);
		initHeader(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(null);
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
	public void initUI(View view) {

		mPullToRefreshGridView = (PullToRefreshGridView) view
				.findViewById(R.id.pull_refresh_grid);
		mGridView = mPullToRefreshGridView.getRefreshableView();
		mBaseListAdapter = new MainCategoryAdapter(getActivity());
		mBaseListAdapter.setDatas(mBaseCategories);
		mGridView.setAdapter(mBaseListAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				BaseFragment newFragment = new SubCategoryFragment(mSorts
//						.get(position));
//				switchFragment(newFragment, true);
				SubCategoryActivity.startActivity(getActivity(), mBaseCategories.get(position));

			}
		});
		mPullToRefreshGridView
				.setOnRefreshListener(new OnRefreshListener2<GridView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						// TODO Auto-generated method stub
						mHandler.postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								
							}
						}, 200);
						request();
						mPullToRefreshGridView.onRefreshComplete();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						// TODO Auto-generated method stub
						mHandler.postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								
							}
						}, 200);
						mPullToRefreshGridView.onRefreshComplete();
					}
				});

		mBaseListAdapter.notifyDataSetChanged();
		initLoadingInfo(view);
		// 异常情况下点击刷新按钮处理
		setOnRefreshClickListener(mOnRefreshClickListener);
	}

	public void initHeader(View v) {
		mHeader = (Header) v.findViewById(R.id.header);
		mHeader.setTitle(R.string.tab_selected_brand);
		mHeader.setIntroduceBtn("中艺", new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyIntroduceActivity.startActivity(getActivity(),getActivity().getString(R.string.tab_selected_brand));
			}
		});

	}

	public void request() {
		if (!isRequesEnd) {
			return;
		}

		isRequesEnd = false; // 改变正在请求的标识
		if ((mBaseCategories == null) || (mBaseCategories.size() == 0)) {
			setLoadingViewVisible(View.VISIBLE, mGridView);
		}
		HttpApi.getMainCategory(getActivity(),1,10, mOnRequestListener);
	}

	public OnRequestListener mOnRequestListener = new OnRequestListener() {

		@Override
		public void onResponse(String url, final int state,
				final Object result, int type) {
			mIsFirstLoad = false;
			if (!isAdded()) // fragment 已退出,返回
			{
				return;
			}
			isRequesEnd = true;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if ((state == HttpConnectManager.STATE_SUC)
							&& (result != null)) {

							List<BaseCategory> categories =(List<BaseCategory>) result;
						
						if (categories != null && categories.size() != 0) {
							initData(categories);
						}

					}
				}
			});

		}
	};
	/*
	 * public OnRequestListener mOnRequestListener = new OnRequestListener() {
	 * 
	 * @Override public void onResponse(String url, final int state, final
	 * Object result, int type) { mIsFirstLoad = false; if (!isAdded()) //
	 * fragment 已退出,返回 { return; } isRequesEnd = true; mHandler.post(new
	 * Runnable() {
	 * 
	 * @Override public void run() { if ((state == HttpConnectManager.STATE_SUC)
	 * && (result != null)) { List<Sort> sorts = (List<Sort>) result;
	 * 
	 * if ((sorts != null) && (sorts.size() > 0)) { initData(sorts);
	 * setLoadInfoGone(mPullToRefreshGridView); if (sorts.size() < mPageSize) {
	 * mPullToRefreshGridView.setPullToRefreshEnabled(false); } else {
	 * mPullToRefreshGridView.setPullToRefreshEnabled(true); }
	 * 
	 * } else { mPullToRefreshGridView.setPullToRefreshEnabled(false); if (
	 * (mSorts == null) || (mSorts.size() == 0)) {
	 * setNotDataVisible(View.VISIBLE, mPullToRefreshGridView); } } } else if
	 * (state == HttpConnectManager.STATE_TIME_OUT) { // 请求超时 if ((mSorts ==
	 * null) || (mSorts.size() == 0)) { setNotNetVisible(View.VISIBLE,
	 * mPullToRefreshGridView); } Toast.makeText(getActivity(),
	 * R.string.time_out, Toast.LENGTH_SHORT).show();
	 * mPullToRefreshGridView.setPullToRefreshEnabled(false); } else { // 请求失败
	 * if ( (mSorts == null) || (mSorts.size() == 0)) {
	 * setNotNetVisible(View.VISIBLE, mGridView); }
	 * Toast.makeText(getActivity(), R.string.request_fail,
	 * Toast.LENGTH_SHORT).show();
	 * mPullToRefreshGridView.setPullToRefreshEnabled(false);
	 * 
	 * } } });
	 * 
	 * } };
	 */
	Handler mHandler = new Handler();


	public void initData(List<BaseCategory> categories) {
		if (mGridView.getAdapter() == null) {
			mBaseListAdapter = new MainCategoryAdapter(getActivity());
			mGridView.setAdapter(mBaseListAdapter);
		}

		if (categories == null) {// 第一次进入先读取缓存
			// 获取缓存数据

			mBaseCategories = NetCache.readCache(ServerUrl.URL_MAIN_CATEGORY);

			if (mIsFirstLoad || mBaseCategories == null || mBaseCategories.size() == 0) {
				request();
			}
			mBaseListAdapter.setDatas(mBaseCategories);
			mBaseListAdapter.notifyDataSetChanged();
		} else {
			if (categories.size() != 0) {
				if (categories == null) {
					categories = new ArrayList<BaseCategory>();
				}

				mBaseCategories = categories;
				// 保存第一页的缓存
				try {
					NetCache.saveCache(categories, ServerUrl.URL_MAIN_CATEGORY);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mBaseListAdapter.setDatas(mBaseCategories);
				mBaseListAdapter.notifyDataSetChanged();
			} else {
				// 判断是否有网络的情况
				if ((mBaseCategories == null) && (mBaseCategories.size() == 0)) {
					setNotNetVisible(View.VISIBLE, mPullToRefreshGridView);
				}
			}
		}

	}

	/**
	 * 刷新按钮
	 */
	private OnRefreshClickListener mOnRefreshClickListener = new OnRefreshClickListener() {

		@Override
		public void onClick(View v) {
			initData(null); // 请求数据

		}
	};

	/*
	 * public XGridViewListener mIXListViewListener = new XGridViewListener() {
	 * 
	 * @Override public void onRefresh() { if (isRequesEnd) {
	 * mHandler.postDelayed(new Runnable() {
	 * 
	 * @Override public void run() { onStopLoad(); } }, 2000);
	 * 
	 * request();
	 * 
	 * } }
	 * 
	 * @Override public void onLoadMore() { if (isRequesEnd) { mPageNum++;
	 * request(); } } };
	 */

	/*
	 * @Override public void onFooterRefresh(PullToRefreshView view) {
	 * 
	 * // TODO Auto-generated method stub if (isRequesEnd) {
	 * mHandler.postDelayed(new Runnable() {
	 * 
	 * @Override public void run() { onStopLoad(); } }, 2000);
	 * 
	 * 
	 * } view.postDelayed(new Runnable() {
	 * 
	 * @Override public void run() { onStopLoad(); for(int i=0;i<2;i++){ //
	 * mSort.setGroupImage(R.drawable.info01); mSort.setGroupLabel("zhongyi");
	 * mList.add(mSort); } mSortAdapter.notifyDataSetChanged();
	 * mPullToRefreshView.onFooterRefreshComplete(0);
	 * 
	 * } }, 200); }
	 */

	/*
	 * @Override public void onHeaderRefresh(PullToRefreshView view) { // TODO
	 * Auto-generated method stub view.postDelayed(new Runnable() {
	 * 
	 * @Override public void run() { onStopLoad(); Toast.makeText(getActivity(),
	 * "runing", Toast.LENGTH_SHORT).show(); for(int i=0;i<2;i++){ //
	 * mSort.setGroupImage(R.drawable.info01); mSort.setGroupLabel("zhongyi");
	 * mList.add(mSort); } mSortAdapter.notifyDataSetChanged();
	 * mPullToRefreshView.onHeaderRefreshComplete(); } }, 200);
	 * 
	 * 
	 * }
	 */

}
