package com.cyq.mvshow.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.R;
import com.cyq.mvshow.activity.PictureListsActivity.SamplePagerAdapter;
import com.cyq.mvshow.listener.GetGallryDetailsListener;
import com.cyq.mvshow.model.GallryDetailsRespone;
import com.cyq.mvshow.model.Picture;
import com.cyq.mvshow.utils.MyImageLoader;
import com.cyq.mvshow.utils.MyUrl;
import com.cyq.mvshow.widget.PullToRefreshViewPager;
import com.cyq.mvshow.widget.ZoomImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class PictureItemsActivity extends BaseActivity implements OnRefreshListener2<ViewPager> {

	private PullToRefreshViewPager mPullToRefreshViewPager;
	private static ArrayList<Picture> mPictures;
	private SamplePagerAdapter samplePagerAdapter;
	private ViewPager vp;
	private static long mSelectId;
	private static Activity mActivity;
	private int headId;
	private int footId;
	private boolean isLoading;
	private boolean isForHead;
	

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_viewpager);
		mPictures = (ArrayList<Picture>) getIntent().getExtras().getSerializable("pictures");
		mSelectId = getIntent().getExtras().getLong("selectId");
		headId=mPictures.get(0).getGallery();
		footId=mPictures.get(0).getGallery();
		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(this);

		 vp = mPullToRefreshViewPager.getRefreshableView();
		vp.setOffscreenPageLimit(5);
		samplePagerAdapter = new SamplePagerAdapter();
		vp.setAdapter(samplePagerAdapter);
		vp.setCurrentItem((int) mSelectId);
		mActivity=PictureItemsActivity.this;
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
		MyApplication.imageFetcherTianGouImp.getImageDetailsByID(id, listener,isForHead);
	}

	private void requestPicturesForFoot(int id) {
		if (isLoading) {
			return;
		}
		isLoading = true;
		isForHead = false;
		MyApplication.imageFetcherTianGouImp.getImageDetailsByID(id, listener,isForHead);
	}
	private GetGallryDetailsListener listener = new GetGallryDetailsListener() {

		@Override
		public void success(GallryDetailsRespone gallryDetailsRespone,boolean isForHead) {
			isLoading = false;
			if (isForHead) {
				mPictures.addAll(0, gallryDetailsRespone.getList());
			} else {
				mPictures.addAll(gallryDetailsRespone.getList());
			}
			if (samplePagerAdapter == null) {
				samplePagerAdapter = new SamplePagerAdapter();
				vp.setAdapter(samplePagerAdapter);
			} else {
				if (isForHead) {
					samplePagerAdapter = new SamplePagerAdapter();
					vp.setAdapter(samplePagerAdapter);
					vp.setCurrentItem(gallryDetailsRespone.getList().size());
				} else {
					samplePagerAdapter.notifyDataSetChanged();
					vp.setCurrentItem(mPictures.size()-gallryDetailsRespone.getList().size());
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
			return mPictures.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, final int position) {
			
			ViewGroup view = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.item_pulltorefresh_viewpager, null);
			ZoomImageView zoomImageView = new ZoomImageView(container.getContext());
			ProgressBar progress_img=new ProgressBar(mActivity);
			TextView reload_tv=new TextView(mActivity);
			reload_tv.setText("加载失败，点击重试");
			
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

//	private class GetDataTask extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			try {
//				Thread.sleep(4000);
//			} catch (InterruptedException e) {
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			mPullToRefreshViewPager.onRefreshComplete();
//			super.onPostExecute(result);
//		}
//	}

	public static void actionStart(Activity activity,GallryDetailsRespone pictures, long selectId) {
		//TODO 需要修改
		Intent intent = new Intent(MyApplication.context,
				PictureItemsActivity.class);
		intent.putExtra("pictures", pictures.getList());
		intent.putExtra("selectId", selectId);
		activity.startActivity(intent);
	}

}
