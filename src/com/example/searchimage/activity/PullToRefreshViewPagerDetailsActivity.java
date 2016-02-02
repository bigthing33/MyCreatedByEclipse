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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.base.BaseActivity;
import com.example.searchimage.base.CommonAdapter;
import com.example.searchimage.base.ViewHolder;
import com.example.searchimage.listener.GetGallryDetailsListener;
import com.example.searchimage.model.GallryDetailsRespone;
import com.example.searchimage.model.Picture;
import com.example.searchimage.utils.DialogUtils;
import com.example.searchimage.utils.LogUtil;
import com.example.searchimage.utils.MyImageLoader;
import com.example.searchimage.utils.MyUrl;
import com.example.searchimage.widget.PullToRefreshViewPager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class PullToRefreshViewPagerDetailsActivity extends BaseActivity implements
		OnRefreshListener2<ViewPager> {

	public static final String TAG = PullToRefreshViewPagerDetailsActivity.class.getSimpleName();
	private PullToRefreshViewPager mPullToRefreshViewPager;
	private static ArrayList<ArrayList<Picture>> localGalleries = new ArrayList<ArrayList<Picture>>();
	private static ArrayList<String> titles = new ArrayList<String>();
	private static String  currentTitle ;
	private int headId = 1;// 请求的页数
	private int footId = 1;// 请求的页数
	private boolean isLoading;
	private boolean isForHead;
	private ViewPager mVp;
	private SamplePagerAdapter samplePagerAdapter;
	private GetGallryDetailsListener listener=new GetGallryDetailsListener() {
		
		@Override
		public void success(GallryDetailsRespone gallryDetailsRespone,
				boolean isForHead) {
			isLoading = false;
			if (isForHead) {
				titles.add(0, gallryDetailsRespone.getTitle());
				localGalleries.add(0, gallryDetailsRespone.getList());
			}else {
				titles.add(gallryDetailsRespone.getTitle());
				localGalleries.add(gallryDetailsRespone.getList());
			}
			if (samplePagerAdapter==null) {
				samplePagerAdapter= new SamplePagerAdapter();
				mVp.setAdapter(samplePagerAdapter);
			}else {
				if (isForHead) {
					samplePagerAdapter= new SamplePagerAdapter();
					mVp.setAdapter(samplePagerAdapter);
				}else {
					samplePagerAdapter.notifyDataSetChanged();
					mVp.setCurrentItem(localGalleries.size());
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
	private static Activity mActivity;
	private static CommonAdapter<Picture> picturesAdapter;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity=PullToRefreshViewPagerDetailsActivity.this;
		setContentView(R.layout.activity_ptr_viewpager);
		headId=getIntent().getIntExtra("id", 0);
		footId=getIntent().getIntExtra("id", 0);
		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(this);
		mVp = mPullToRefreshViewPager.getRefreshableView();
		mVp.setOffscreenPageLimit(5);
		localGalleries.clear();
		requestPicturesForHead(headId);
	}


	static class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
//			return localGalleries.size();
			return localGalleries.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, final int initItemposition) {
			
			LogUtil.e(TAG, "instantiateItem");
			ViewGroup view = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.fragment_gallry_details, null);
			ListView image_lv = (ListView) view.findViewById(R.id.image_lv);
			image_lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					PullToRefreshViewPagerItemsActivity.actionStart(mActivity,localGalleries.get(position),id);
				}
			});
			image_lv.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
					DialogUtils.showIsSavePictureDialog(mActivity,localGalleries.get(initItemposition).get((int) id));
					return true;
				}
			});
			TextView image_tv = (TextView) view.findViewById(R.id.image_tv);
			image_tv.setText(titles.get(initItemposition));
			setImage_lvAdapter(image_lv,initItemposition);
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

	private static void setImage_lvAdapter(ListView image_lv, int position) {
		final ArrayList<Picture> pictures=localGalleries.get(position);
		
		picturesAdapter = new CommonAdapter<Picture>(mActivity,
				pictures, R.layout.item_image) {
			@Override
			public void convert(ViewHolder holder, Picture t, final int position) {
				final MyImageLoader myImageLoader = new MyImageLoader();
				TextView textView = holder.getView(R.id.item_text);
				ImageView imageView = holder.getView(R.id.item_img);
				ProgressBar progress_img = holder.getView(R.id.progress_img);
				TextView reload_tv = holder.getView(R.id.reload_tv);
				reload_tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						myImageLoader.displayImage(MyUrl.TIANGOU_SERVICE+ pictures.get(position).getSrc());
					}
				});
				myImageLoader.mReload_tv = reload_tv;
				myImageLoader.mImageView = imageView;
				myImageLoader.mProgress_img = progress_img;
				myImageLoader.displayImage(MyUrl.TIANGOU_SERVICE+ pictures.get(position).getSrc());
				textView.setText(position + 1+"");
			}
		};
		image_lv.setAdapter(picturesAdapter);
	
	}
	private void requestPicturesForHead(int id) {
		if (isLoading) {
			return;
		}
		isLoading = true;
		isForHead=true;
		MyApplication.imageFetcherTianGouImp
		.getImageDetailsByID(id, listener,isForHead);
	}
	private void requestPicturesForFoot(int id) {
		if (isLoading) {
			return;
		}
		isLoading = true;
		isForHead=false;
		MyApplication.imageFetcherTianGouImp
		.getImageDetailsByID(id, listener,isForHead);
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

	public static void actionStart(FragmentActivity fragmentActivity, int id){
		Intent intent =new Intent(fragmentActivity, PullToRefreshViewPagerDetailsActivity.class);
		intent.putExtra("id", id);
		fragmentActivity.startActivity(intent);
	}
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ViewPager> refreshView) {
//		showToast("从左往右滑，上一页");
		headId--;
		requestPicturesForHead(headId);
	}
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ViewPager> refreshView) {
//		showToast("从右往左滑，下一页");
		footId++;
		requestPicturesForFoot(footId);
		new GetDataTask().execute();
		
	}
	@Override
	public void initUI() {
		
	}

}
