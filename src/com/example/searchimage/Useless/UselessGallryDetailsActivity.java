package com.example.searchimage.Useless;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.searchimage.MyApplication;
import com.example.searchimage.activity.SingleFragmentAcitvity;

public class UselessGallryDetailsActivity extends SingleFragmentAcitvity {

	@Override
	protected Fragment createFragment() {
		return UselessGallryDetailsFragment.getInstance(getIntent().getIntExtra("id", 0));
	}
	public static void actionStart(FragmentActivity fragmentActivity, int id){
		Intent intent =new Intent(fragmentActivity, UselessGallryDetailsActivity.class);
		intent.putExtra("id", id);
		fragmentActivity.startActivity(intent);
	}

}
