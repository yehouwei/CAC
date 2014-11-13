package com.zy.zhongyiandroid.ui.fragment;

import java.io.IOException;
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
import android.widget.Toast;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.NetCache;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.Api.ServerUrl;
import com.zy.zhongyiandroid.data.bean.Message;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.ui.activity.MessageDeatailAcitvity;
import com.zy.zhongyiandroid.ui.activity.MyIntroduceActivity;
import com.zy.zhongyiandroid.ui.adapter.MessageAdapter;
import com.zy.zhongyiandroid.ui.widget.Header;
import com.zy.zhongyiandroid.ui.widget.LoadingInfo.OnRefreshClickListener;
import com.zy.zhongyiandroid.ui.widget.list.XListView;
import com.zy.zhongyiandroid.ui.widget.list.XListView.IXListViewListener;

/**
 *消息
 * 
 * @author Seven
 * 
 */
public class MessageFragment extends BaseFragment {
	// 第一页
	public final int FRIST_PAGE_NUMBER = 1;

	private XListView mListView;
	private List<Message> mMessages =new ArrayList<Message>();

	private MessageAdapter mMessageAdapter;

	// 请求是否已经结束
	private boolean isRequesEnd = true;

	public final static String TAG = "InfoFragment";

	private int mPageNum = 1;

	private int mPageSize = 5;

	boolean mIsFirstLoad = true;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_info, null);
		initHeader(view);
		// 初始化ui\
		initUI(view);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData(null);
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
		mListView = (XListView) view.findViewById(R.id.listView);
		mListView.setHeaderDividersEnabled(false);
		mListView.setDividerHeight(0);
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(true);		
		mListView.setXListViewListener(mIXListViewListener);
		mMessageAdapter = new MessageAdapter(getActivity());
		mListView.setAdapter(mMessageAdapter);
		mMessageAdapter.notifyDataSetChanged();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				

				MessageDeatailAcitvity.startActivity(getActivity(), mMessages.get(position-1).getId());
			}
		});
		
		initLoadingInfo(view);
		// 异常情况下点击刷新按钮处理
		setOnRefreshClickListener(mOnRefreshClickListener);
	}
	
	public void initHeader(View v){
		mHeader = (Header) v.findViewById(R.id.header);
		mHeader.setTitle(R.string.tab_informantion);
		mHeader.setIntroduceBtn(getActivity().getResources().getString(R.string.cac), new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyIntroduceActivity.startActivity(getActivity(),getActivity().getString(R.string.tab_informantion));
			}
		});
	}
	
	public void request() {
		if (!isRequesEnd) {
			return;
		}

		isRequesEnd = false; // 改变正在请求的标识
		if ((mPageNum == 1) && ((mMessages == null) || (mMessages.size() == 0))) {
			setLoadingViewVisible(View.VISIBLE, mListView);
		}
		HttpApi.getMessages(getActivity(),mPageNum,mPageSize, mOnRequestListener);
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
						List<Message> messages = null;
						if(myApiResult.getRows()!=null){
							messages = (List<Message>) myApiResult.getRows();
						}
						

						if ((messages != null) && (messages.size() > 0)) {
							initData(messages);
							onStopLoad();
							setLoadInfoGone(mListView);
							if (messages.size() < mPageSize) {
								mListView.setPullLoadEnable(false);
							} else {
								mListView.setPullLoadEnable(true);
							}

						} else {
							mListView.setPullLoadEnable(false);
							if ((mPageNum == 1) && ((mMessages == null) || (mMessages.size() == 0))) {
								setNotDataVisible(View.VISIBLE, mListView);
							}
						}
					} else if (state == HttpConnectManager.STATE_TIME_OUT) { // 请求超时
						if ((mPageNum == 1) && ((mMessages == null) || (mMessages.size() == 0))) {
							setNotNetVisible(View.VISIBLE, mListView);
						}
						Toast.makeText(getActivity(), R.string.time_out, Toast.LENGTH_SHORT).show();
						mListView.setPullLoadEnable(false);
					} else { // 请求失败
						if ((mPageNum == 1) && ((mMessages == null) || (mMessages.size() == 0))) {
							setNotNetVisible(View.VISIBLE, mListView);
						}
						Toast.makeText(getActivity(), R.string.request_fail, Toast.LENGTH_SHORT).show();
						mListView.setPullLoadEnable(false);

					}
				}
			});

		}
	};
	Handler mHandler = new Handler();

	public void initData( List<Message> messages) {
		if (mListView.getAdapter() == null) {
			mMessageAdapter = new MessageAdapter(getActivity());
			mListView.setAdapter(mMessageAdapter);
		}

		if (messages == null) {// 第一次进入先读取缓存
			// 获取缓存数据
			if (mPageNum == 1) {
				mMessages = NetCache.readCache(ServerUrl.URL_MESSAGES);
			}
			if (mIsFirstLoad||mMessages==null||mMessages.size()==0) {
				request();
			}
			mMessageAdapter.setDatas(mMessages);
			mMessageAdapter.notifyDataSetChanged();
		} else {
			if (messages.size() != 0) {
				if (messages == null) {
					messages = new ArrayList<Message>();
				}
				if (mPageNum == FRIST_PAGE_NUMBER) {
					mMessages = messages;
					// 保存第一页的缓存
					try {
						NetCache.saveCache(messages, ServerUrl.URL_MESSAGES);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					mMessages.addAll(messages);
				}

				mMessageAdapter.setDatas(mMessages);
				mMessageAdapter.notifyDataSetChanged();
			} else {
				// 判断是否有网络的情况
				if ((mMessages == null) && (mMessages.size() == 0)) {
					setNotNetVisible(View.VISIBLE, mListView);
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
	
	public IXListViewListener mIXListViewListener = new IXListViewListener() {

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
	};

	/** 关闭Load的显示状态 **/
	private void onStopLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}


}
