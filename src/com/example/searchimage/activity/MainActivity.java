package com.example.searchimage.activity;


import android.support.v4.app.Fragment;

import com.example.searchimage.fragment.GallryListFragment;

public class MainActivity extends SingleFragmentAcitvity {

	@Override
	protected Fragment createFragment() {
		return new GallryListFragment();
	}
	
}
