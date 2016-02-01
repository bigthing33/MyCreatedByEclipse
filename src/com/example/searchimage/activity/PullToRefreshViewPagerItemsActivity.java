package com.example.searchimage.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.model.Picture;
import com.example.searchimage.utils.MyImageLoader;
import com.example.searchimage.utils.MyUrl;
import com.example.searchimage.widget.PullToRefreshViewPager;
import com.example.searchimage.widget.ZoomImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class PullToRefreshViewPagerItemsActivity extends Activity implements
		OnRefreshListener<ViewPager> {

	private PullToRefreshViewPager mPullToRefreshViewPager;
	private static ArrayList<Picture> mPictures;
	private static long mSelectId;
	private static Activity mActivity;
	

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
		mActivity=PullToRefreshViewPagerItemsActivity.this;
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
			
			ViewGroup view = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.item_pulltorefresh_viewpager, null);
			ZoomImageView zoomImageView = new ZoomImageView(container.getContext());
			ProgressBar progress_img=new ProgressBar(mActivity);
			TextView reload_tv=new TextView(mActivity);
			reload_tv.setText("加载失败点击重试");
			
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
			lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE); 
			view.addView(zoomImageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			view.addView(progress_img, lp);
			view.addView(reload_tv, lp);
			
			final MyImageLoader myImageLoader=new MyImageLoader();
			reload_tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					myImageLoader.displayImage(MyUrl.TIANGOU_SERVICE+ mPictures.get(position).getSrc());
				}
			});
			myImageLoader.mReload_tv = reload_tv;
			myImageLoader.mImageView=zoomImageView;
			myImageLoader.mProgress_img=progress_img;
			myImageLoader.displayImage(MyUrl.TIANGOU_SERVICE+ mPictures.get(position).getSrc());
			container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
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

	public static void actionStart(Activity activity,ArrayList<Picture> pictures, long selectId) {
		Intent intent = new Intent(MyApplication.context,
				PullToRefreshViewPagerItemsActivity.class);
		intent.putExtra("pictures", pictures);
		intent.putExtra("selectId", selectId);
		activity.startActivity(intent);
	}

}
