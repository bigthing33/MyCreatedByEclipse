package com.example.searchimage.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.activity.PullToRefreshViewPagerDetailsActivity;
import com.example.searchimage.base.CommonAdapter;
import com.example.searchimage.base.ViewHolder;
import com.example.searchimage.db.TianGouImageDB;
import com.example.searchimage.listener.GetGalleriesListener;
import com.example.searchimage.listener.ListenerImp;
import com.example.searchimage.model.Gallery;
import com.example.searchimage.model.GetGalleriesRespone;
import com.example.searchimage.utils.DialogUtils;
import com.example.searchimage.utils.LogUtil;
import com.example.searchimage.utils.MyConstants;
import com.example.searchimage.utils.MyImageLoader;
import com.example.searchimage.utils.MyUrl;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

public class GallryCollectGallryFragment extends Fragment implements OnClickListener, OnRefreshListener2<GridView> {
	private static final String TAG = GallryCollectGallryFragment.class.getSimpleName();
	private PullToRefreshGridView mPullToRefreshGridView;
	private GridView mGridView;
	private ArrayList<Gallery> mGalleries = new ArrayList<Gallery>();
	private CommonAdapter<Gallery> galleryaAdapter;
	private int postion;

	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		LogUtil.e(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_gallry_random, null);
		mPullToRefreshGridView = (PullToRefreshGridView) view.findViewById(R.id.image_lv);
		mPullToRefreshGridView.setMode(Mode.PULL_FROM_START);
		mPullToRefreshGridView.setOnRefreshListener(this);
		mGridView = mPullToRefreshGridView.getRefreshableView();
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				GallryDetailsActivity.actionStart(getActivity(),galleryaAdapter.getItem((int) id).getId());
				PullToRefreshViewPagerDetailsActivity.actionStart(getActivity(),galleryaAdapter.getItem((int) id).getId());
				
			}
		});
		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, final long id) {
				DialogUtils.showIsDeleteGalleryDialog(getActivity(), galleryaAdapter.getItem((int) id), new ListenerImp() {
					
					@Override
					public void success(Object object) {
						mGalleries.remove((int) id);
						galleryaAdapter.notifyDataSetChanged();
					}
				});
				return true;
			}
		});
		mGridView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				postion=mGridView.getFirstVisiblePosition();
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});

		mGridView.postDelayed(new Runnable() {  
		    @Override  
		    public void run() {  
		    	mGridView.setSelection(postion);  
		    }  
		},150);  
		mGridView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mGridView.setSelection(postion);
			}
		}, 250);
		mGalleries = TianGouImageDB.getInstance(MyApplication.getcContext()).loadGalleries();
		setImage_lvAdapter();
		return view;
	}
	



	@Override
	public void onResume() {
		super.onResume();
		LogUtil.e(TAG, "onResume");
	}

	protected void setImage_lvAdapter() {
		galleryaAdapter = new CommonAdapter<Gallery>(getActivity(), mGalleries,
				R.layout.item_image_random) {
			@Override
			public void convert(ViewHolder holder, Gallery t, final int position) {
				final MyImageLoader myImageLoader = new MyImageLoader();
				TextView textView = holder.getView(R.id.item_text);
				ImageView imageView = holder.getView(R.id.item_img);
				ProgressBar progress_img = holder.getView(R.id.progress_img);
				TextView reload_tv = holder.getView(R.id.reload_tv);
				reload_tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						myImageLoader.displayImage(MyUrl.TIANGOU_SERVICE+ mGalleries.get(position).getImg());
					}
				});
				myImageLoader.mReload_tv = reload_tv;
				myImageLoader.mImageView = imageView;
				myImageLoader.mProgress_img = progress_img;
				myImageLoader.displayImage(MyUrl.TIANGOU_SERVICE+ mGalleries.get(position).getImg());
				textView.setText(position + 1+ mGalleries.get(position).getTitle());
			}
		};
		mGridView.setAdapter(galleryaAdapter);
	}

	public static GallryCollectGallryFragment getInstance() {
		GallryCollectGallryFragment gallryItemFragment = new GallryCollectGallryFragment();
		Bundle args = new Bundle();
		gallryItemFragment.setArguments(args);
		return gallryItemFragment;
	}

	@Override
	public void onClick(View v) {
		
	}
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
		mGalleries = TianGouImageDB.getInstance(MyApplication.getcContext()).loadGalleries();
		 setImage_lvAdapter() ;
		mPullToRefreshGridView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mPullToRefreshGridView.onRefreshComplete();
				
			}
		}, 1000);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
		
	}

}
