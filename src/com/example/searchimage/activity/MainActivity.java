package com.example.searchimage.activity;


import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.searchimage.R;
import com.example.searchimage.fragment.GallryListFragment;
import com.example.searchimage.fragment.GallryNewsFragment;
import com.example.searchimage.fragment.GallryRandomeFragment;
import com.example.searchimage.utils.MyConstants;

public class MainActivity extends SelectFragmentAcitvity implements OnClickListener {
	private String selectTag=MyConstants.NEWS_IMAGE;
	private Button news_btn;
	private Button classies_btn;
	private Button random_btn;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		news_btn=(Button) findViewById(R.id.news_btn);
		classies_btn=(Button) findViewById(R.id.classies_btn);
		random_btn=(Button) findViewById(R.id.random_btn);
		news_btn.setOnClickListener(this);
		classies_btn.setOnClickListener(this);
		random_btn.setOnClickListener(this);
	}


	@Override
	protected HashMap<String, Fragment> createFragments() {
		HashMap<String, Fragment> fragments=new HashMap<String, Fragment>();
		fragments.put(MyConstants.NEWS_IMAGE, GallryListFragment.getInstance(MyConstants.NEWS_IMAGE));
		fragments.put(MyConstants.CLASSIES_IMAGE,GallryListFragment.getInstance(MyConstants.CLASSIES_IMAGE));
		fragments.put(MyConstants.RANDOME_IMAGE,new GallryRandomeFragment());
		return fragments;
	}


	@Override
	protected void onStart() {
		super.onStart();
		selectFragment(MyConstants.NEWS_IMAGE);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.news_btn:
			showFragmentByTag(MyConstants.NEWS_IMAGE);
			break;
		case R.id.classies_btn:
			showFragmentByTag(MyConstants.CLASSIES_IMAGE);
			break;
		case R.id.random_btn:
			showFragmentByTag(MyConstants.RANDOME_IMAGE);
			break;
		default:
			break;
		}
	}
	/**
	 * 根据参数tag，显示对应的fragment
	 * @param tag
	 */
	private void showFragmentByTag(String tag){
		if (selectTag.equals(tag)) {
			return;
		}
		selectTag=tag;
		selectFragment(tag);
	}
	
	
	
}
