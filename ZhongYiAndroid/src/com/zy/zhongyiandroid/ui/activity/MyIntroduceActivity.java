package com.zy.zhongyiandroid.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.zy.zhongyiandroid.R;
import com.zy.zhongyiandroid.data.bean.Introduce;
import com.zy.zhongyiandroid.ui.adapter.IntroduceAdapter;
import com.zy.zhongyiandroid.ui.widget.Header;
import com.zy.zhongyiandroid.ui.widget.PageControlView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

public class MyIntroduceActivity extends BaseActivity {
	private ViewPager mViewPager;
	private IntroduceAdapter mIntroduceAdapter;
	private PageControlView mpageControlView;
	List<Introduce> introduces;
	Introduce introduce;
	Header mHeader;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_myintroduce);
		initHeader();
		initUi();

	}

	private void initUi() {
		mViewPager=(ViewPager)this.findViewById(R.id.vpIntroduce);

		// TODO Auto-generated method stub
		mIntroduceAdapter = new IntroduceAdapter(this);
		getDatas();
		mViewPager.setAdapter(mIntroduceAdapter);
		mpageControlView=(PageControlView)this.findViewById(R.id.pcIntroduce);
		mpageControlView.bindScrollViewGroup(introduces, 0);
		
//		mpageControlView.bindScrollViewGroup(mViewPager,introduces,mHeader);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				mHeader.setTitle(getResources().getStringArray(R.array.introduce_header)[arg0]);
				mpageControlView.bindScrollViewGroup(introduces, arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}

	private void getDatas() {
		// TODO Auto-generated method stub
		introduces=new ArrayList<Introduce>();
		for(int i=0;i<imgIntroduce.length;i++){
			introduce=new Introduce();
			introduce.setTitle(getResources().getStringArray(R.array.introduce_title)[i]);
			introduce.setContent(getResources().getStringArray(R.array.introduce_content)[i]);
			introduce.setImageview(imgIntroduce[i]);
			introduces.add(introduce);
		}
		mIntroduceAdapter.setDatas(introduces);
	}

	public static void startActivity(Context c, String value) {
		Intent i = new Intent(c, MyIntroduceActivity.class);
		i.putExtra("name", value);
		c.startActivity(i);
	}

	public void initHeader() {
		mHeader = (Header) this.findViewById(R.id.header);
		if (mHeader != null) {
			mHeader.setTitle(getResources().getStringArray(R.array.introduce_header)[0]);

			mHeader.setBackBtn(getIntent().getStringExtra("name"),
					new View.OnClickListener() {

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

	final static int imgIntroduce[] = { R.drawable.icon_myintroduce_item01,
			R.drawable.icon_myintroduce_item02,
			R.drawable.icon_myintroduce_item03,
			R.drawable.icon_myintroduce_item04,
			R.drawable.icon_myintroduce_item05,
			R.drawable.icon_myintroduce_item06,
			R.drawable.icon_myintroduce_item07,
			R.drawable.icon_myintroduce_item08 };

}
