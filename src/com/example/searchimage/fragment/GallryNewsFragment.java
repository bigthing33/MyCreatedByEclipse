package com.example.searchimage.fragment;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.base.CommonAdapter;
import com.example.searchimage.base.ViewHolder;
import com.example.searchimage.imageutils.GetGalleriesListener;
import com.example.searchimage.model.Gallery;
import com.example.searchimage.model.GetGalleriesRespone;
import com.example.searchimage.utils.LogUtil;
import com.example.searchimage.utils.MyConstants;
import com.example.searchimage.utils.SharedPreferencesManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GallryNewsFragment extends Fragment {
	private ListView image_lv;
	private TextView image_tv;
	private CopyOnWriteArrayList<Gallery> localGalleries;
	private CommonAdapter<Gallery> galleryaAdapter;
	private LoadMoreListViewContainer loadmoreContainer;
	private PtrFrameLayout mPtrFrameLayout;
	private int newsId = 0;// 最新热词的id
	private boolean isLoading = false;
	private GetGalleriesListener listener = new GetGalleriesListener() {

		@Override
		public void success(GetGalleriesRespone getGalleryListRespone,int requestPageNum) {
			getGalleryListRespone.getTngou();
			if (getGalleryListRespone.getTngou() == null) {
				newsId--;
			} else if (getGalleryListRespone.getTngou().size() < MyConstants.PAGE_SIZE) {
				newsId--;
			}
			if (requestPageNum==0) {
				localGalleries.clear();
			}
			//将新请求到的数据填充到ListView
			setImage_lvAdapter(getGalleryListRespone.getTngou());
			isLoading = false;
			loadmoreContainer.loadMoreFinish(localGalleries.isEmpty(),getGalleryListRespone.getTngou() != null);
			mPtrFrameLayout.refreshComplete();
		}

		@Override
		public void erro(String erroString) {
			isLoading = false;
			int errorCode = 0;
			String errorMessage = "加载失败，点击加载更多";
			loadmoreContainer.loadMoreError(errorCode, errorMessage);
			mPtrFrameLayout.refreshComplete();
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		localGalleries = new CopyOnWriteArrayList<Gallery>();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gallries, null);
		image_lv = (ListView) view.findViewById(R.id.image_lv);
		image_tv = (TextView) view.findViewById(R.id.image_tv);
		loadmoreContainer = (LoadMoreListViewContainer) view
				.findViewById(R.id.loadmorecontainer);
		loadmoreContainer.useDefaultFooter();// 使用默认的页脚
		// 向上滑动触发的事件
		loadmoreContainer.setLoadMoreHandler(new LoadMoreHandler() {

			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				// TODO 执行请求方法
				requestGallries(newsId);
			}
		});
		mPtrFrameLayout = (PtrFrameLayout) view
				.findViewById(R.id.load_ptr_frame);
		mPtrFrameLayout.setLoadingMinTime(1000);
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				requestGallries(0);
				LogUtil.e("mPtrFrameLayout", "onRefreshBegin");
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				// here check list view, not content.
				LogUtil.e("mPtrFrameLayout", "checkCanDoRefresh");
			//判断是否可以下拉刷新，如果 Content 是 ListView ，当第一条在顶部时返回 true ，表示可以下拉刷新。
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,image_lv, header);
			}
		});
		loadmoreContainer.setVisibility(View.VISIBLE);
		// getDate();
		// auto load data
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				// 自动更新，会执行onRefreshBegin方法
				mPtrFrameLayout.autoRefresh(false);
			}
		}, 150);

		image_tv.setText(newsId + "");
		requestGallries(0);
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	

	@Override
	public void onResume() {
		super.onResume();
		if (localGalleries.isEmpty()) {
			if (SharedPreferencesManager.getNews() != null) {
				localGalleries = SharedPreferencesManager.getNews().getTngou();
				setImage_lvAdapter(localGalleries);
			}
		}
	}
/**
 * 请求数据
 * @param isCleanListView 是否要清空ListView
 */
	private void requestGallries(int pageNum) {
		if (isLoading) {
			return;
		}
		if (pageNum==0) {
			newsId=0;
		}
		isLoading = true;
		MyApplication.imageFetcherTianGouImp.getImgNews(newsId,
				MyConstants.PAGE_SIZE, 1, listener);
		newsId++;
	}

	protected void setImage_lvAdapter(CopyOnWriteArrayList<Gallery> tngou) {
		if (tngou == null) {
			return;
		}
		for (Gallery gallery : tngou) {
			localGalleries.add(gallery);
		}
		if (galleryaAdapter == null) {
			galleryaAdapter = new CommonAdapter<Gallery>(getActivity(),
					localGalleries, R.layout.item_image) {
				@Override
				public void convert(ViewHolder holder, Gallery t, int position) {
					TextView textView = holder.getView(R.id.item_text);
					ImageView imageView = holder.getView(R.id.item_img);
					// MyApplication.imageLoader.displayImage("http://tnfs.tngou.net/img"+localGalleries.get(position).getImg(),
					// imageView);
					// textView.setText(localGalleries.get(position).getTitle());
					imageView.setImageResource(R.drawable.sample);
					textView.setText(position+ localGalleries.get(position).getTitle());
				}
			};
			image_lv.setAdapter(galleryaAdapter);
		} else {
			galleryaAdapter.notifyDataSetChanged();
		}
	}

	public static GallryNewsFragment getInstance(int pos) {
		GallryNewsFragment gallryNewsFragment = new GallryNewsFragment();
		 Bundle args=new Bundle();
		 args.putInt("classifyId", pos);
		 gallryNewsFragment.setArguments(args);

		return gallryNewsFragment;
	}

}
