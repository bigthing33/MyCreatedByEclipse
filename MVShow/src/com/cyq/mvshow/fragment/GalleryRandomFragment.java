package com.cyq.mvshow.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.R;
import com.cyq.mvshow.activity.PictureListsActivity;
import com.cyq.mvshow.adapter.GalleryAdapter;
import com.cyq.mvshow.db.TianGouImageDB;
import com.cyq.mvshow.listener.GetGalleriesListener;
import com.cyq.mvshow.model.Gallery;
import com.cyq.mvshow.model.GetGalleriesRespone;
import com.cyq.mvshow.utils.LogUtil;
import com.cyq.mvshow.utils.MyConstants;
import com.cyq.mvshow.widget.TitleView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

@SuppressLint("NewApi")
public class GalleryRandomFragment extends Fragment implements
		OnRefreshListener2<GridView>, OnClickListener {
	private static final String TAG = GalleryRandomFragment.class
			.getSimpleName();
	private TitleView titleView;

	private PullToRefreshGridView mPullToRefreshGridView;
	private GalleryAdapter mGalleryAdapter;
	private LinearLayout foot_layout;
	private LinearLayout collect_allselect;
	private LinearLayout collect_confrim;
	private TextView allSelect_tv;

	private int mPageNum = 1;// 请求的页数
	private boolean isLoading = false;
	private GridView refreshableView;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gallry_random,
				container, false);
		/*
		 * 初始化TitleView
		 */
		titleView = (TitleView) view.findViewById(R.id.titleView);
		titleView.setLeftBtnVisibility(View.VISIBLE);
		titleView.setRightBtnVisibility(View.VISIBLE);
		titleView.setRightBtnBackground(R.drawable.menu_collected);
		titleView.setRightBtn2Visibility(View.GONE);
		titleView.setRightBtnClickListener(titleViewBtnClick);
		/*
		 * 初始化GridView
		 */
		mPullToRefreshGridView = (PullToRefreshGridView) view
				.findViewById(R.id.mPullToRefreshGridView);
		mPullToRefreshGridView.setMode(Mode.PULL_FROM_END);
		mPullToRefreshGridView.setOnRefreshListener(this);
		mGalleryAdapter = new GalleryAdapter(getActivity());
		mPullToRefreshGridView.setAdapter(mGalleryAdapter);
		refreshableView = mPullToRefreshGridView.getRefreshableView();
		mPullToRefreshGridView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if (mGalleryAdapter.isCollectModel()) {
							// 如果是收藏模式
							ImageView selectImg = (ImageView) view.findViewById(R.id.select_img);
							if (mGalleryAdapter.selectGalleries.contains(mGalleryAdapter.localGalleries.get((int) id))) {
								mGalleryAdapter.selectGalleries.remove(mGalleryAdapter.localGalleries.get((int) id));
								selectImg.setImageDrawable(getActivity().getDrawable(R.drawable.pr_unselected));
							} else {
								mGalleryAdapter.selectGalleries.add(mGalleryAdapter.localGalleries.get((int) id));
								selectImg.setImageDrawable(getActivity().getDrawable(R.drawable.pr_selected));
							}
						} else {
							// 如果不是收藏模式，跳转到对应的图片专辑中
							PictureListsActivity.actionStart(getActivity(),mGalleryAdapter.localGalleries.get((int) id).getId());
						}

					}
				});
		if (mGalleryAdapter.localGalleries.isEmpty()) {
			requestGallries(1);
		}
		/*
		 * 初始化foot_layout
		 */
		foot_layout = (LinearLayout) view.findViewById(R.id.foot_layout);
		collect_allselect = (LinearLayout) view
				.findViewById(R.id.collect_allselect);
		collect_confrim = (LinearLayout) view
				.findViewById(R.id.collect_confrim);
		allSelect_tv = (TextView) view.findViewById(R.id.allSelect_tv);
		collect_allselect.setOnClickListener(this);
		collect_confrim.setOnClickListener(this);
		foot_layout.setVisibility(View.GONE);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.collect_allselect:
			if (allSelect_tv.getText().equals("全选")) {
				allSelect_tv.setText("全不选");
				for (Gallery gallery : mGalleryAdapter.localGalleries) {
					if (!mGalleryAdapter.selectGalleries.contains(gallery)) {
						mGalleryAdapter.selectGalleries.add(gallery);
					}
				}
			} else {
				allSelect_tv.setText("全选");
				mGalleryAdapter.selectGalleries.clear();
			}
			mGalleryAdapter.notifyDataSetChanged();
			break;
		case R.id.collect_confrim:
			if (mGalleryAdapter.selectGalleries.size()>0) {
				for (Gallery gallery : mGalleryAdapter.selectGalleries) {
					TianGouImageDB.getInstance(MyApplication.getcContext()).saveGallry(gallery);
				}
				//将显示模式改成非收藏模式
				changeShowModel(mGalleryAdapter.isCollectModel());
				Toast.makeText(getActivity(), "已收藏", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
		requestGallries(mPageNum);

	}

	private void requestGallries(int paramterPagNum) {
		if (isLoading) {
			return;
		}
		mPageNum = paramterPagNum;
		isLoading = true;
		MyApplication.imageFetcherTianGouImp.getGallriesNews(mPageNum,
				MyConstants.PAGE_SIZE, 0, false, listener);
		mPageNum++;
	}

	/**
	 * 给标题栏设置监听器
	 */

	private TitleView.OnBtnClickListener titleViewBtnClick = new TitleView.OnBtnClickListener() {
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
				changeShowModel(mGalleryAdapter.isCollectModel());
				break;
			default:
				break;
			}
		}

	};
	/**
	 * 修改listView的显示模式
	 * 
	 * @param isCollectModel
	 */
	private void changeShowModel(boolean isCollectModel) {
		if (isCollectModel) {
			// 点击的时候是收藏模式，那么变成正常模式
			mGalleryAdapter.setCollectModel(false);
			mGalleryAdapter.selectGalleries.clear();
			mGalleryAdapter.selectTags.clear();
			titleView.setRightBtnBackground(R.drawable.menu_collected);
			foot_layout.setVisibility(View.GONE);
		} else {
			// 点击的时候是正常模式，则变成收藏模式
			mGalleryAdapter.setCollectModel(true);
			foot_layout.setVisibility(View.VISIBLE);
			titleView.setRightBtnBackground(R.drawable.menu_cancel);
		}
		mGalleryAdapter.notifyDataSetChanged();
	}
	private GetGalleriesListener listener = new GetGalleriesListener() {

		@Override
		public void success(GetGalleriesRespone getGalleryListRespone,
				boolean isForHead) {
			if (getGalleryListRespone.getTngou() == null) {
				LogUtil.e(TAG, "请求的数据为空");
				Toast.makeText(getActivity(), "最后一页啦", Toast.LENGTH_SHORT)
						.show();
				return;
			} else if (getGalleryListRespone.getTngou().size() < MyConstants.PAGE_SIZE) {
				Toast.makeText(getActivity(), "最后一页啦", Toast.LENGTH_SHORT)
						.show();
			}
			for (Gallery gallery : getGalleryListRespone.getTngou()) {
				mGalleryAdapter.localGalleries.add(gallery);
				mGalleryAdapter.tags.add(new StringBuilder("初始态"));
				LogUtil.e("tags.size",
						("tags.size:" + mGalleryAdapter.tags.size()));
			}
			mPullToRefreshGridView.postDelayed(new Runnable() {

				@Override
				public void run() {
					mPullToRefreshGridView.onRefreshComplete();
					mGalleryAdapter.notifyDataSetChanged();

				}
			}, 500);
			isLoading = false;

		}

		@Override
		public void erro(String erroString) {
			isLoading = false;
			mPullToRefreshGridView.postDelayed(new Runnable() {

				@Override
				public void run() {
					mPullToRefreshGridView.onRefreshComplete();

				}
			}, 500);
		}
	};

}
