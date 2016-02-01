package com.example.searchimage.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.searchimage.R;
import com.example.searchimage.utils.LogUtil;

public class GallryCollectFragment extends Fragment {
	public static final String TAG = GallryCollectFragment.class.getName();
	private TextView title;
	private ViewPager mViewPager;
	private FrameLayout subFragmentContainer;
	private FragmentPagerAdapter fragmentStatePagerAdapter;
	private String tag;
	private int currentPostion=0;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(getActivity());
		mViewPager.setId(R.id.viewPager_imagecollect);
		tag=getArguments().getString("tag");
//		LogUtil.e(TAG, "pictures"+TianGouImageDB.getInstance(MyApplication.getcContext()).loadPictures().size());
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.z_uselessfragment_gallrylist, null);
		subFragmentContainer = (FrameLayout) view.findViewById(R.id.subFragmentContainer);
		title = (TextView) view.findViewById(R.id.title);
		title.setText("收藏的图片");
		subFragmentContainer.addView(mViewPager);
		mViewPager.setOffscreenPageLimit(2);// 设置缓存个数,最多三个
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		initViewPager();
	}

	private void initViewPager() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				
				FragmentManager fm = getFragmentManager();
				fragmentStatePagerAdapter=new FragmentPagerAdapter(fm) {
					@Override
					public int getCount() {
						return 2;
					}

					@Override
					public Fragment getItem(int pos) {
						switch (pos) {
						case 0:
							
							return GallryClassFragment.getInstance(0);
						case 1:
							return GallryClassFragment.getInstance(1);
							
						default:
							return GallryClassFragment.getInstance(0);
						}
							
					}
				};
				
				mViewPager.setAdapter(fragmentStatePagerAdapter);
				mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

							@Override
							public void onPageSelected(int pos) {
								switchItem(pos);
								currentPostion=pos;
							}

							@Override
							public void onPageScrolled(int pos,
									float posOffset, int posffsetPixels) {

							}

							@Override
							public void onPageScrollStateChanged(int state) {

							}
						});

			}
		}, 100);
		switchItem(currentPostion);
	}

	private void switchItem(final int pos) {

		if (pos== 0) {
			title.setText("收藏的图片");
		}else if (pos== 1) {
			title.setText("收藏的相册");
		}
		LogUtil.e(TAG, pos+"setCurrentItem");
		mViewPager.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mViewPager.setCurrentItem(pos);
				
			}
		}, 150);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	public static Fragment getInstance(String tag) {
		GallryCollectFragment gallryListFragment = new GallryCollectFragment();
		Bundle bundle=new Bundle();
		bundle.putString("tag", tag);
		gallryListFragment.setArguments(bundle);
		return gallryListFragment;
	}

}
