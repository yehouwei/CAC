package com.zy.zhongyiandroid.ui.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.encore.libs.http.HttpConnectManager;
import com.encore.libs.http.OnRequestListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.NetCache;
import com.zy.zhongyiandroid.data.Api.HttpApi;
import com.zy.zhongyiandroid.data.Api.ServerUrl;
import com.zy.zhongyiandroid.data.bean.Item;
import com.zy.zhongyiandroid.data.bean.Message;
import com.zy.zhongyiandroid.data.bean.MyApiResult;
import com.zy.zhongyiandroid.ui.activity.MyIntroduceActivity;
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
public class ProductDetailFragment extends BaseFragment {
	// 第一页
	public final int FRIST_PAGE_NUMBER = 1;
	DisplayImageOptions options;
	
	

	// 请求是否已经结束
	private boolean isRequesEnd = true;

	public final static String TAG = "ItemListFragment";

	boolean mIsFirstLoad = true;
	
	Item subSort;
	
	Item item;
	
	String btnbackString;
	
	ImageView mimgProduct;
	TextView mtvTitle;
	TextView mtvContent;
	
	
	public ProductDetailFragment(Item subSort,String btnbackString) {
		// TODO Auto-generated constructor stub
		this.subSort=subSort;
		this.btnbackString=btnbackString;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_product_detail, null);

		// 初始化ui\
		initUI(view);
		initHeader(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//request();
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
		
		mimgProduct=(ImageView)view.findViewById(R.id.imgLogo);
		mtvTitle=(TextView)view.findViewById(R.id.tvTitle);
		mtvContent=(TextView)view.findViewById(R.id.tvContent);
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		mtvTitle.setText(subSort.getItemShop());
		mtvContent.setText(subSort.getItemDescription());
		ImageLoader mImageLoader = ImageLoader.getInstance();
		mImageLoader.displayImage(subSort.getHdImage(),mimgProduct,options);
	}
	
	public void initHeader(View v){
		mHeader = (Header) v.findViewById(R.id.header);
		mHeader.setTitle(subSort.getItemName());
		mHeader.setBackBtn(btnbackString,new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().finish();
				
			}
		});
		
	}
	
/*	public void request() {
		if (!isRequesEnd) {
			return;
		}

		isRequesEnd = false; // 改变正在请求的标识
		if ((mPageNum == 1) && ((mSort == null) || (mSort.size() == 0))) {
			setLoadingViewVisible(View.VISIBLE, mGridView);
		}
//		HttpApi.getItemList(getActivity(), pageNum, pageSize, subSort.getGroupId(), subSort.getSubGroupId(), mOnRequestListener);
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
	};*/
	Handler mHandler = new Handler();
	public void initData( List<Item> sorts){
		
	}


	

}
		



