package com.zy.zhongyiandroid.ui.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.annotation.SuppressLint;
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
import com.zy.zhongyiandroid.data.bean.Item;
import com.zy.zhongyiandroid.data.bean.Message;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.data.bean.SubCategory;
import com.zy.zhongyiandroid.ui.activity.MyIntroduceActivity;
import com.zy.zhongyiandroid.ui.activity.ProductDetailActivity;
import com.zy.zhongyiandroid.ui.adapter.ItemsAdapter;
import com.zy.zhongyiandroid.ui.adapter.MessageAdapter;
import com.zy.zhongyiandroid.ui.adapter.MainCategoryAdapter;
import com.zy.zhongyiandroid.ui.adapter.SubCategoryAdapter;
import com.zy.zhongyiandroid.ui.widget.Header;
import com.zy.zhongyiandroid.ui.widget.LoadingInfo.OnRefreshClickListener;
import com.zy.zhongyiandroid.ui.widget.PullToRefreshView;
import com.zy.zhongyiandroid.ui.widget.PullToRefreshView.OnFooterRefreshListener;
import com.zy.zhongyiandroid.ui.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.zy.zhongyiandroid.ui.widget.list.XListView;
import com.zy.zhongyiandroid.ui.widget.list.XListView.IXListViewListener;

/**
 *消息
 * 
 * @author Seven
 * 
 */
public class ItemListFragment extends BaseFragment {
	// 第一页
	public final int FRIST_PAGE_NUMBER = 1;
	
	private int pageNum=1;
	
	private int pageSize=15;

	private GridView mGridView;
	
	PullToRefreshGridView mPullToRefreshGridView;
	
	private List<Item> mList =new ArrayList<Item>();

	private ItemsAdapter mSortAdapter;

	// 请求是否已经结束
	private boolean isRequesEnd = true;

	public final static String TAG = "ItemListFragment";

	boolean mIsFirstLoad = true;
	
	SubCategory subCategory;
	
	Item item;
	
	String tvBack;
	
	
	public ItemListFragment(SubCategory subCategory,String tvBack) {
		// TODO Auto-generated constructor stub
		this.subCategory=subCategory;
		this.tvBack=tvBack;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sort, null);

		// 初始化ui\
		initUI(view);
		initHeader(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		request();
	}
	public static void startActivity(Context c){
		Intent i = new Intent(c,MyIntroduceActivity.class);
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
		
		mPullToRefreshGridView = (PullToRefreshGridView)view.findViewById(R.id.pull_refresh_grid);
		mGridView = mPullToRefreshGridView.getRefreshableView();
		mSortAdapter = new ItemsAdapter(getActivity());
		mSortAdapter.setDatas(mList);
		mGridView.setAdapter(mSortAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				BaseFragment newFragment = new MessageFragment();
//			switchFragment(newFragment, true);
/*			FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
					ft.replace(getContainerViewID(), newFragment);
					ft.addToBackStack(null);
					ft.commit();*/
				ProductDetailActivity.startActivity(getActivity(), mList.get(position),subCategory.getCatName());
				
			}
		});
		mPullToRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				// TODO Auto-generated method stub
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						request();
					}
				}, 200);
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
						request();
					}
				}, 200);
				mPullToRefreshGridView.onRefreshComplete();
			}
		});
			
			
		
	
		mSortAdapter.notifyDataSetChanged();
/*		initLoadingInfo(view);
		// 异常情况下点击刷新按钮处理
		setOnRefreshClickListener(mOnRefreshClickListener);*/
	}
	
	public void initHeader(View v){
		mHeader = (Header) v.findViewById(R.id.header);
		mHeader.setTitle(subCategory.getCatName());
		mHeader.setBackBtn(tvBack,new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				getActivity().finish();
				
				
			}
		});
		
	}
	
	public void request() {
		if (!isRequesEnd) {
			return;
		}

/*		isRequesEnd = false; // 改变正在请求的标识
		if ((mPageNum == 1) && ((mSort == null) || (mSort.size() == 0))) {
			setLoadingViewVisible(View.VISIBLE, mGridView);
		}*/
		HttpApi.getItemList(getActivity(), pageNum, pageSize, subCategory.getCatCode(), mOnRequestListener);
	}
	public OnRequestListener mOnRequestListener = new OnRequestListener() {

		@Override
		public void onResponse(String url, final int state, final Object result, int type) {
			mIsFirstLoad = false;
			if (!isAdded()) // fragment 已退出,返回
			{
				return;
			}
			isRequesEnd = true;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if ((state == HttpConnectManager.STATE_SUC) && (result != null)) {
						MyApiResult myApiResult = (MyApiResult) result;
						List<Item> items = null;
						if(myApiResult.getRows()!=null){
							items = (List<Item>) myApiResult.getRows();
						}

						if(items!=null&&items.size()!=0){
							initData(items);
						}
							
				}
				}
			});
					
		}
	};
/*	public OnRequestListener mOnRequestListener = new OnRequestListener() {

		@Override
		public void onResponse(String url, final int state, final Object result, int type) {
			mIsFirstLoad = false;
			if (!isAdded()) // fragment 已退出,返回
			{
				return;
			}
			isRequesEnd = true;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					if ((state == HttpConnectManager.STATE_SUC) && (result != null)) {
						List<Sort> sorts = (List<Sort>) result;

						if ((sorts != null) && (sorts.size() > 0)) {
							initData(mSort);
							onStopLoad();
							setLoadInfoGone(mGridView);
							if (sorts.size() < mPageSize) {
								mGridView.setPullLoadEnable(false);
							} else {
								mGridView.setPullLoadEnable(true);
							}

						} else {
							mGridView.setPullLoadEnable(false);
							if ((mPageNum == 1) && ((mSort == null) || (mSort.size() == 0))) {
								setNotDataVisible(View.VISIBLE, mGridView);
							}
						}
					} else if (state == HttpConnectManager.STATE_TIME_OUT) { // 请求超时
						if ((mPageNum == 1) && ((mSort == null) || (mSort.size() == 0))) {
							setNotNetVisible(View.VISIBLE, mGridView);
						}
						Toast.makeText(getActivity(), R.string.time_out, Toast.LENGTH_SHORT).show();
						mGridView.setPullLoadEnable(false);
					} else { // 请求失败
						if ((mPageNum == 1) && ((mSort == null) || (mSort.size() == 0))) {
							setNotNetVisible(View.VISIBLE, mGridView);
						}
						Toast.makeText(getActivity(), R.string.request_fail, Toast.LENGTH_SHORT).show();
						mGridView.setPullLoadEnable(false);

					}
				}
			});

		}
	};*/
	Handler mHandler = new Handler();
	public void initData( List<Item> sorts){
		if (mGridView.getAdapter() == null) {
			mSortAdapter = new ItemsAdapter(getActivity());
			mGridView.setAdapter(mSortAdapter);
		}
		if(sorts==null){
			request();
		}else{
			mList.addAll(sorts);
		}
		mSortAdapter.setDatas(mList);
		mSortAdapter.notifyDataSetChanged();
	}


	/*public void initData( List<Sort> sorts) {
		if (mGridView.getAdapter() == null) {
			mSortAdapter = new SortAdapter(getActivity());
			mGridView.setAdapter(mSortAdapter);
		}

		if (sorts == null) {// 第一次进入先读取缓存
			// 获取缓存数据
			if (mPageNum == 1) {
				mSort = NetCache.readCache(ServerUrl.URL_SORT_BASELIST);
			}
			if (mIsFirstLoad||mSort==null||mSort.size()==0) {
				request();
			}
			mSortAdapter.setDatas(mSort);
			mSortAdapter.notifyDataSetChanged();
		} else {
			if (sorts.size() != 0) {
				if (sorts == null) {
					sorts = new ArrayList<Sort>();
				}
				if (mPageNum == FRIST_PAGE_NUMBER) {
					mSort = sorts;
					// 保存第一页的缓存
					try {
						NetCache.saveCache(sorts, ServerUrl.URL_SORT_BASELIST);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					mSort.addAll(sorts);
				}

				mSortAdapter.setDatas(sorts);
				mSortAdapter.notifyDataSetChanged();
			} else {
				// 判断是否有网络的情况
				if ((mSort == null) && (mSort.size() == 0)) {
					setNotNetVisible(View.VISIBLE, mGridView);
				}
			}
		}

	}*/

	/**
	 * 刷新按钮
	 */
/*	private OnRefreshClickListener mOnRefreshClickListener = new OnRefreshClickListener() {

		@Override
		public void onClick(View v) {
			initData(null); // 请求数据

		}
	};*/
	
/*	public XGridViewListener mIXListViewListener = new XGridViewListener() {

		@Override
		public void onRefresh() {
			if (isRequesEnd) {
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						onStopLoad();
					}
				}, 2000);

				request();

			}
		}

		@Override
		public void onLoadMore() {
			if (isRequesEnd) {
				mPageNum++;
				request();
			}
		}
	};*/


/*	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		
		// TODO Auto-generated method stub
		if (isRequesEnd) {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					onStopLoad();
				}
			}, 2000);

			
		}
		view.postDelayed(new Runnable() {
			@Override
			public void run() {
				onStopLoad();
				for(int i=0;i<2;i++){
//					mSort.setGroupImage(R.drawable.info01);
					mSort.setGroupLabel("zhongyi");
					mList.add(mSort);
				}
					mSortAdapter.notifyDataSetChanged();
								mPullToRefreshView.onFooterRefreshComplete(0);
								
			}
		}, 200);
	}*/

/*	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		view.postDelayed(new Runnable() {
			@Override
			public void run() {
				onStopLoad();
				Toast.makeText(getActivity(), "runing", Toast.LENGTH_SHORT).show();
				for(int i=0;i<2;i++){
//					mSort.setGroupImage(R.drawable.info01);
					mSort.setGroupLabel("zhongyi");
					mList.add(mSort);
				}	
					mSortAdapter.notifyDataSetChanged();
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		}, 200);
	
			
	}*/

}
		



