package com.example.searchimage.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.model.Picture;
import com.example.searchimage.utils.MyImageLoader;
import com.example.searchimage.utils.MyUrl;
import com.example.searchimage.widget.PullToRefreshViewPager;
import com.example.searchimage.widget.ZoomImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class PullToRefreshViewPagerActivity extends Activity implements
		OnRefreshListener<ViewPager> {

	private PullToRefreshViewPager mPullToRefreshViewPager;
	private static ArrayList<Picture> mPictures;
	private static long mSelectId;
	

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_viewpager);
		mPictures = (ArrayList<Picture>) getIntent().getExtras().getSerializable("pictures");
		mSelectId = getIntent().getExtras().getLong("selectId");
		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(this);

		ViewPager vp = mPullToRefreshViewPager.getRefreshableView();
		vp.setOffscreenPageLimit(5);
		vp.setAdapter(new SamplePagerAdapter());
		vp.setCurrentItem((int) mSelectId);
	}

	@Override
	public void onRefresh(PullToRefreshBase<ViewPager> refreshView) {
		new GetDataTask().execute();
	}

	static class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPictures.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, final int position) {
			final ZoomImageView zoomImageView = new ZoomImageView(container.getContext());
			MyImageLoader.displayImage(
					MyUrl.TIANGOU_SERVICE + mPictures.get(position).getSrc(),
					zoomImageView);

            container.addView(zoomImageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            return zoomImageView;
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

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mPullToRefreshViewPager.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	public static void actionStart(FragmentActivity activity,
			ArrayList<Picture> pictures, long selectId) {
		Intent intent = new Intent(MyApplication.context,
				PullToRefreshViewPagerActivity.class);
		intent.putExtra("pictures", pictures);
		intent.putExtra("selectId", selectId);
		activity.startActivity(intent);
	}

}
