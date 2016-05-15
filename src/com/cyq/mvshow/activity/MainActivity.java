package com.cyq.mvshow.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cyq.mvshow.R;
import com.cyq.mvshow.fragment.GalleryClassFragment;
import com.cyq.mvshow.fragment.GalleryCollectFragment;
import com.cyq.mvshow.fragment.GalleryRandomFragment;
import com.cyq.mvshow.widget.TitleView;

public class MainActivity extends BaseActivity {
	private ViewPager mViewPager;
	private Activity mActivity = MainActivity.this;
	private FrameLayout subFragmentContainer;
	private long lastPress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mViewPager = new ViewPager(mActivity);
		mViewPager.setId(R.id.viewPager_imageclassify);
		subFragmentContainer = (FrameLayout) findViewById(R.id.subFragmentContainer);
		subFragmentContainer.addView(mViewPager);
		mViewPager.setOffscreenPageLimit(2);// 设置缓存个数
		initViewPager(mViewPager);
	}

	private void initViewPager(ViewPager viewPager) {
		FragmentManager fm = getSupportFragmentManager();
		viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public Fragment getItem(int index) {
				switch (index) {
				case 0:
					return new GalleryClassFragment();
				case 1:
					return new GalleryRandomFragment();
				case 2:
					return new GalleryCollectFragment();

				default:
					break;
				}
				return null;
			}

			@Override
			public int getCount() {
				return 3;
			}

		});

	}

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - lastPress > 3000) {
			Toast.makeText(this, "再按一次返回退出应用", Toast.LENGTH_SHORT).show();
			lastPress = System.currentTimeMillis();
			return;
		}
		lastPress = 0;
		super.onBackPressed();
	}
}
