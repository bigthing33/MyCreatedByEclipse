package com.example.searchimage.fragment;

import java.util.ArrayList;

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
import android.widget.Toast;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.listener.GetClassesListener;
import com.example.searchimage.model.Galleryclassify;
import com.example.searchimage.model.GetGalleryclassRespone;
import com.example.searchimage.utils.LogUtil;
import com.example.searchimage.utils.MyConstants;
import com.example.searchimage.utils.SharedPreferencesManager;

public class GallryClassesFragment extends Fragment {
	public static final String TAG = GallryClassesFragment.class.getName();
	private TextView title;
	private ViewPager mViewPager;
	private FrameLayout subFragmentContainer;
	private ArrayList<GallryClassFragment> gallriesFragment;
	private ArrayList<Galleryclassify> galleryclasses;
	private FragmentPagerAdapter fragmentStatePagerAdapter;
	private String tag;
	private int currentPostion=0;
	private GetClassesListener Listener= new GetClassesListener() {
		@Override
		public void success(GetGalleryclassRespone getGalleryclassRespone) {
			galleryclasses = getGalleryclassRespone.getTngou();
			initViewPager();
		}

		@Override
		public void erro(String erroString) {
			Toast.makeText(MyApplication.context, "请求失败",Toast.LENGTH_SHORT).show();
		}
	};
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mViewPager = new ViewPager(getActivity());
		mViewPager.setId(R.id.viewPager_imageclassify);
		gallriesFragment=new ArrayList<GallryClassFragment>();
		tag=getArguments().getString("tag");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.z_uselessfragment_gallrylist, null);
		subFragmentContainer = (FrameLayout) view.findViewById(R.id.subFragmentContainer);
		title = (TextView) view.findViewById(R.id.title);
		subFragmentContainer.addView(mViewPager);
		mViewPager.setOffscreenPageLimit(0);// 设置缓存个数,最多三个
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (SharedPreferencesManager.getClassies() != null) {
			galleryclasses = SharedPreferencesManager.getClassies().getTngou();
			 initViewPager();
		}else {
			MyApplication.imageFetcherTianGouImp.getImgClassify(Listener);
		}
		
	}

	private void initViewPager() {
			gallriesFragment.clear();
			for (int i = 0; i < galleryclasses.size(); i++) {
				GallryClassFragment gallriesNewsFragment = GallryClassFragment
						.getInstance(i+1);
				gallriesFragment.add(gallriesNewsFragment);
			
		}
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				
				FragmentManager fm = getFragmentManager();
				fragmentStatePagerAdapter=new FragmentPagerAdapter(fm) {
					@Override
					public int getCount() {
						return galleryclasses.size();
					}

					@Override
					public Fragment getItem(int pos) {
							
							return gallriesFragment.get(pos);
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

		if (galleryclasses.get(pos) != null
				&& galleryclasses.get(pos).getTitle() != null) {
			title.setText(galleryclasses.get(pos).getTitle()+(pos+1)+"/"+galleryclasses.size());
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
		GallryClassesFragment gallryListFragment = new GallryClassesFragment();
		Bundle bundle=new Bundle();
		bundle.putString("tag", tag);
		gallryListFragment.setArguments(bundle);
		return gallryListFragment;
	}

}
