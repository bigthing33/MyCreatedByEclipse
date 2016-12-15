package com.cyq.recorder;

import java.util.ArrayList;
import java.util.UUID;

import com.cyq.recorder.models.Record;
import com.cyq.recorder.models.RecordLab;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewParent;
import android.view.ActionMode.Callback;
import android.view.accessibility.AccessibilityEvent;

public class RecordPagerActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private ArrayList<Record> mRecords;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		System.out.println(getFilesDir());
		super.onCreate(arg0);
		mViewPager=new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		mRecords=RecordLab.get(this).getRecords();
		FragmentManager fm=getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mRecords.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				Record record=mRecords.get(arg0);
				
				return RecordFragment.newInstance(record.getId());
			}
		});
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			public void onPageScrollstate(int state){}
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Record record=mRecords.get(arg0);
				if(record.getTitle()!=null){
					setTitle(record.getTitle());
				}
				
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
		UUID recordId =(UUID)getIntent().getSerializableExtra(RecordFragment.EXTRA_RECORD_ID);
		for(int i=0;i<mRecords.size();i++){
			if(mRecords.get(i).getId().equals(recordId)){
				mViewPager.setCurrentItem(i);
				break;
			}
		}
		
		
	}
	

}
