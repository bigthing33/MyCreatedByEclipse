package com.example.searchimage.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.searchimage.MyApplication;
import com.example.searchimage.fragment.GallryDetailsFragment;

public class GallryDetailsActivity extends SingleFragmentAcitvity {

	@Override
	protected Fragment createFragment() {
		return GallryDetailsFragment.getInstance(getIntent().getIntExtra("id", 0));
	}
	public static void actionStart(FragmentActivity fragmentActivity, int id){
		Intent intent =new Intent(fragmentActivity, GallryDetailsActivity.class);
		intent.putExtra("id", id);
		fragmentActivity.startActivity(intent);
	}

}
