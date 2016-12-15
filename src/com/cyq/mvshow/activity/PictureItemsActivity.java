package com.cyq.mvshow.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.R;
import com.cyq.mvshow.listener.GetGallryDetailsListener;
import com.cyq.mvshow.model.GallryDetailsRespone;
import com.cyq.mvshow.utils.MyImageLoader;
import com.cyq.mvshow.utils.MyUrl;
import com.cyq.mvshow.widget.PullToRefreshViewPager;
import com.cyq.mvshow.widget.ZoomImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class PictureItemsActivity extends BaseActivity implements
		OnRefreshListener2<ViewPager> {

	private PullToRefreshViewPager mPullToRefreshViewPager;
	// private static ArrayList<Picture> mPictures;
	private static GallryDetailsRespone mGallryDetailsRespone;
	private SamplePagerAdapter samplePagerAdapter;
	private ViewPager vp;
	private static long mIndex;
	private static Activity mActivity;
	private int headId;
	private int footId;
	private boolean isLoading;
	private boolean isForHead;
	private Timer mTimer;
	private int mperiodTime = 10;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_viewpager);
		mActivity = PictureItemsActivity.this;

		mGallryDetailsRespone = (GallryDetailsRespone) getIntent().getExtras()
				.getSerializable("gallryDetailsRespone");
		mIndex = getIntent().getExtras().getLong("selectId");
		headId = mGallryDetailsRespone.getId();
		footId = mGallryDetailsRespone.getId();
		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(this);
		vp = mPullToRefreshViewPager.getRefreshableView();
		vp.setOffscreenPageLimit(5);
		samplePagerAdapter = new SamplePagerAdapter();
		vp.setAdapter(samplePagerAdapter);
	
		vp.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				Log.d("测试代码", "onPageSelected选中了" + position);
				mIndex=position;

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
		vp.setCurrentItem((int) mIndex);
		// 自动播放初始化
		mTimer = new Timer();
		mTimer.scheduleAtFixedRate(new CyclePlayTask(), mperiodTime * 1000,
				mperiodTime * 1000);
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mTimer!=null) {
			mTimer.cancel();
		}
	}


	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ViewPager> refreshView) {
		// showToast("从左往右滑，上一页");
		headId--;
		requestPicturesForHead(headId);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ViewPager> refreshView) {
		// showToast("从右往左滑，下一页");
		footId++;
		requestPicturesForFoot(footId);
	}

	private void requestPicturesForHead(int id) {
		if (isLoading) {
			return;
		}
		isLoading = true;
		isForHead = true;
		MyApplication.imageFetcherTianGouImp.getImageDetailsByID(id, listener,
				isForHead);
	}

	private void requestPicturesForFoot(int id) {
		if (isLoading) {
			return;
		}
		isLoading = true;
		isForHead = false;
		MyApplication.imageFetcherTianGouImp.getImageDetailsByID(id, listener,
				isForHead);
	}

	private GetGallryDetailsListener listener = new GetGallryDetailsListener() {

		@Override
		public void success(GallryDetailsRespone gallryDetailsRespone,
				boolean isForHead) {
			isLoading = false;
			if (isForHead) {
				mGallryDetailsRespone.getList().addAll(0,
						gallryDetailsRespone.getList());
			} else {
				mGallryDetailsRespone.getList().addAll(
						gallryDetailsRespone.getList());
			}
			if (samplePagerAdapter == null) {
				samplePagerAdapter = new SamplePagerAdapter();
				vp.setAdapter(samplePagerAdapter);
			} else {
				if (isForHead) {
					samplePagerAdapter = new SamplePagerAdapter();
					vp.setAdapter(samplePagerAdapter);
					vp.setCurrentItem(gallryDetailsRespone.getList().size() - 1);
				} else {
					samplePagerAdapter.notifyDataSetChanged();
					vp.setCurrentItem(mGallryDetailsRespone.getList().size()
							- gallryDetailsRespone.getList().size());
				}
			}

			mPullToRefreshViewPager.postDelayed(new Runnable() {

				@Override
				public void run() {
					mPullToRefreshViewPager.onRefreshComplete();

				}
			}, 300);
			isLoading = false;
		}

		@Override
		public void erro(String erroString) {
			mPullToRefreshViewPager.postDelayed(new Runnable() {

				@Override
				public void run() {
					mPullToRefreshViewPager.onRefreshComplete();
				}
			}, 300);
			isLoading = false;

		}
	};

	static class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mGallryDetailsRespone.getList().size();
		}

		@SuppressLint("NewApi")
		@Override
		public View instantiateItem(ViewGroup container, final int position) {

			ViewGroup view = (ViewGroup) mActivity.getLayoutInflater().inflate(
					R.layout.item_pulltorefresh_viewpager, null);
			TextView title = (TextView) view.findViewById(R.id.title);
			ProgressBar progressbar = (ProgressBar) view
					.findViewById(R.id.progressbar);
			title.setText(position + 1 + "");

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

			ZoomImageView zoomImageView = new ZoomImageView(
					container.getContext());
			view.addView(zoomImageView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			final MyImageLoader myImageLoader = new MyImageLoader();
			myImageLoader.mImageView = zoomImageView;
			myImageLoader.setmProgress_img(progressbar);
			myImageLoader.displayImage(MyUrl.TIANGOU_SERVICE
					+ mGallryDetailsRespone.getList().get(position).getSrc());
			container.addView(view, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}

	public static void actionStart(Activity activity,
			GallryDetailsRespone gallryDetailsRespone, long selectId) {
		// TODO 需要修改
		Intent intent = new Intent(MyApplication.context,
				PictureItemsActivity.class);
		intent.putExtra("gallryDetailsRespone", gallryDetailsRespone);
		intent.putExtra("selectId", selectId);
		activity.startActivity(intent);
	}

	private class CyclePlayTask extends TimerTask {

		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				public void run() {
					mIndex++;
					if (mGallryDetailsRespone.getList().size() == mIndex) {
						footId++;
						requestPicturesForFoot(footId);
					} else {
						vp.setCurrentItem((int) mIndex);
					}
				}
			});

		}

	}

}
