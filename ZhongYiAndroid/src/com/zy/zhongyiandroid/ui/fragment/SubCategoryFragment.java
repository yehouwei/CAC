package com.zy.zhongyiandroid.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.bean.BaseCategory;
import com.zy.zhongyiandroid.data.bean.SubCategory;
import com.zy.zhongyiandroid.ui.activity.ItemListActivity;
import com.zy.zhongyiandroid.ui.activity.MyIntroduceActivity;
import com.zy.zhongyiandroid.ui.adapter.SubCategoryAdapter;
import com.zy.zhongyiandroid.ui.widget.Header;

/**
 *消息
 * 
 * @author Seven
 * 
 */
public class SubCategoryFragment extends BaseFragment {
	// 第一页
	public final int FRIST_PAGE_NUMBER = 1;

	private GridView mGridView;
	PullToRefreshGridView mPullToRefreshGridView;
	private List<SubCategory> mList =new ArrayList<SubCategory>();

	private SubCategoryAdapter mSortAdapter;

	// 请求是否已经结束
	private boolean isRequesEnd = true;

	public final static String TAG = "SortFragment";

	boolean mIsFirstLoad = true;
	
	BaseCategory baseCategory;
	
//	Sort BaseSorts;
//	Sort mSort=new Sort();
	
	public SubCategoryFragment(BaseCategory baseCategory) {
		// TODO Auto-generated constructor stub
		this.baseCategory=baseCategory;
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
		mSortAdapter = new SubCategoryAdapter(getActivity());
		mSortAdapter.setDatas(mList);
		mGridView.setAdapter(mSortAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//			BaseFragment newFragment = new ItemListFragment(mList.get(position));
//			switchFragment(newFragment, true);
//			FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
//					ft.replace(getContainerViewID(), newFragment);
//					ft.addToBackStack(null);
//					ft.commit();
				ItemListActivity.startActivity(getActivity(), mList.get(position),mHeader.getTitle());
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
		mHeader.setTitle(baseCategory.getCatName());
		mHeader.setBackBtn(getResources().getString(R.string.tab_selected_brand), new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		HttpApi.getSubCategory(getActivity(),baseCategory.getCatCode(),1,10, mOnRequestListener);
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
						List<SubCategory> sorts = (List<SubCategory>) result;
						if(sorts!=null&&sorts.size()!=0){
							initData(sorts);
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
	public void initData( List<SubCategory> sorts){
		if (mGridView.getAdapter() == null) {
			mSortAdapter = new SubCategoryAdapter(getActivity());
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
		



