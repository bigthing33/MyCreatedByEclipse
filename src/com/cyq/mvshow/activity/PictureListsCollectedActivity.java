package com.cyq.mvshow.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.R;
import com.cyq.mvshow.adapter.PictureAdapter;
import com.cyq.mvshow.db.TianGouImageDB;
import com.cyq.mvshow.listener.GetGallryDetailsListener;
import com.cyq.mvshow.model.Gallery;
import com.cyq.mvshow.model.GallryDetailsRespone;
import com.cyq.mvshow.model.Picture;
import com.cyq.mvshow.utils.DialogUtils;
import com.cyq.mvshow.utils.LogUtil;
import com.cyq.mvshow.widget.PullToRefreshViewPager;
import com.cyq.mvshow.widget.TitleView;
import com.cyq.mvshow.widget.TitleView.OnBtnClickListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class PictureListsCollectedActivity extends BaseActivity implements OnRefreshListener2<ViewPager> {
	public static final String TAG = PictureListsCollectedActivity.class.getSimpleName();
	private static Activity mActivity;
	private static ArrayList<GallryDetailsRespone> localGallries = new ArrayList<GallryDetailsRespone>();
	private SamplePagerAdapter samplePagerAdapter;
	private PullToRefreshViewPager mPullToRefreshViewPager;
	private static PictureAdapter picturesAdapter;
	private static ArrayList<String> titles = new ArrayList<String>();
	private int headId = 1;// 请求的页数
	private int footId = 1;// 请求的页数
	private boolean isLoading;
	private boolean isForHead;
	private ViewPager mVp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = PictureListsCollectedActivity.this;
		setContentView(R.layout.activity_ptr_viewpager);
		headId = getIntent().getIntExtra("id", 0);
		footId = getIntent().getIntExtra("id", 0);
		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(this);
		mVp = mPullToRefreshViewPager.getRefreshableView();
		mVp.setOffscreenPageLimit(5);
		localGallries.clear();
		requestPicturesForHead(headId);
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

	public static void actionStart(FragmentActivity fragmentActivity, int id) {
		Intent intent = new Intent(fragmentActivity,PictureListsCollectedActivity.class);
		intent.putExtra("id", id);
		fragmentActivity.startActivity(intent);
	}

	private GetGallryDetailsListener listener = new GetGallryDetailsListener() {

		@Override
		public void success(GallryDetailsRespone gallryDetailsRespone,boolean isForHead) {
			isLoading = false;
			if (isForHead) {
//				titles.add(0, gallryDetailsRespone.getTitle());
				titles.add(0, "图片");
				localGallries.add(0, gallryDetailsRespone);
			} else {
//				titles.add(gallryDetailsRespone.getTitle());
				titles.add(0, "图片");
				localGallries.add(gallryDetailsRespone);
			}
			if (samplePagerAdapter == null) {
				samplePagerAdapter = new SamplePagerAdapter();
				mVp.setAdapter(samplePagerAdapter);
			} else {
				if (isForHead) {
					samplePagerAdapter = new SamplePagerAdapter();
					mVp.setAdapter(samplePagerAdapter);
					mVp.setCurrentItem(0);
				} else {
					samplePagerAdapter.notifyDataSetChanged();
					mVp.setCurrentItem(localGallries.size()-1);
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

		private TitleView title;
		@Override
		public int getCount() {
			// return localGalleries.size();
			return localGallries.size();
		}

		@Override
		public View instantiateItem(ViewGroup container,final int initItemposition) {

			LogUtil.e(TAG, "instantiateItem");
			ViewGroup view = (ViewGroup) mActivity.getLayoutInflater().inflate(R.layout.fragment_picture_list, null);
			ListView image_lv = (ListView) view.findViewById(R.id.image_lv);
			image_lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					PictureItemsActivity.actionStart(mActivity,localGallries.get(initItemposition), id);
				}
			});
			image_lv.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent,View view, int position, long id) {
					DialogUtils.showIsSavePictureDialog(mActivity,localGallries.get(initItemposition).getList().get((int) id));
					return true;
				}
			});
			/*
			 * titleView的初始化
			 */
			title = (TitleView) view.findViewById(R.id.titleView);
			title.setTitle(titles.get(initItemposition)+initItemposition);
			title.setLeftBtnVisibility(View.VISIBLE);
			title.setRightBtnVisibility(View.VISIBLE);
			if (TianGouImageDB.getInstance(mActivity).isContainGallery(localGallries.get(initItemposition).getId())) {
				title.setRightBtnBackground(R.drawable.menu_collected);
			}else{
				title.setRightBtnBackground(R.drawable.menu_uncollected);
			}
			title.setRightBtn2Visibility(View.GONE);
			title.setRightBtnClickListener(new OnBtnClickListener() {
				
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.head_left_btn:
						/*
						 * Intent i = new Intent(FristApplyActivity.this,
						 * MainActivity.class); startActivity(i);
						 */
						break;
					case R.id.head_right_btn:
						if (TianGouImageDB.getInstance(mActivity).isContainGallery(localGallries.get(initItemposition).getId())) {
							//说明已经收藏了，需要取消收藏
							TianGouImageDB.getInstance(mActivity).deleteGallery(localGallries.get(initItemposition).getId());
						}else{
							//收藏这个Gallery 
							Gallery gallery = new Gallery();
							gallery.setCount(localGallries.get(initItemposition).getCount());
							gallery.setFcount(localGallries.get(initItemposition).getFcount());
							gallery.setGalleryclass(localGallries.get(initItemposition).getGalleryclass());
							gallery.setId(localGallries.get(initItemposition).getId());
							gallery.setImg(localGallries.get(initItemposition).getImg());
							gallery.setRcount(localGallries.get(initItemposition).getRcount());
							gallery.setSize(localGallries.get(initItemposition).getSize());
							gallery.setTitle(localGallries.get(initItemposition).getTitle());
							TianGouImageDB.getInstance(mActivity).saveGallry(gallery);
						}
						if (TianGouImageDB.getInstance(mActivity).isContainGallery(localGallries.get(initItemposition).getId())) {
							title.setRightBtnBackground(R.drawable.menu_collected);
						}else{
							title.setRightBtnBackground(R.drawable.menu_uncollected);
						}
						break;
					default:
						break;
					}
				}
			});
			picturesAdapter = new PictureAdapter(mActivity);
			image_lv.setAdapter(picturesAdapter);
			picturesAdapter.setLocalPictures(localGallries.get(initItemposition).getList());
			for (Picture picture : localGallries.get(initItemposition).getList()) {
				picturesAdapter.tags.add(new StringBuilder("初始态"));
			}
			container.addView(view, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
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

}
