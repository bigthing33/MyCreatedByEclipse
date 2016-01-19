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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.activity.PullToRefreshViewPagerActivity;
import com.example.searchimage.base.CommonAdapter;
import com.example.searchimage.base.ViewHolder;
import com.example.searchimage.imageutils.GetGallryDetailsListener;
import com.example.searchimage.model.GallryDetailsRespone;
import com.example.searchimage.model.Picture;
import com.example.searchimage.utils.LogUtil;
import com.example.searchimage.utils.MyUrl;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class GallryDetailsFragment extends Fragment implements OnClickListener, OnRefreshListener2<ListView> {
	private static final String TAG = GallryDetailsFragment.class.getSimpleName();
	private PullToRefreshListView mPullRefreshListView;
	private ListView image_lv;
	private TextView image_tv;
	private ArrayList<Picture> localGalleries = new ArrayList<Picture>();
	private int pageNum = 1;// 请求的页数
	private boolean isLoading = false;
	private CommonAdapter<Picture> galleryaAdapter;
	private boolean isForHead;
	private GetGallryDetailsListener listener = new GetGallryDetailsListener() {
		
		
		@Override
		public void erro(String erroString) {
			isLoading = false;
			int errorCode = 0;
			String errorMessage = "加载失败，点击加载更多";
			mPullRefreshListView.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					mPullRefreshListView.onRefreshComplete();
					
				}
			},500);
		}

		@Override
		public void success(GallryDetailsRespone gallryDetailsRespone) {
			Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
			localGalleries=gallryDetailsRespone.getList();
			setImage_lvAdapter();
			image_tv.setText(gallryDetailsRespone.getTitle()+"");
			mPullRefreshListView.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					mPullRefreshListView.onRefreshComplete();
					
				}
			},500);
			
			
			
		}
	};
	protected int postion;

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
		View view = inflater.inflate(R.layout.fragment_gallry_details, null);
		
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.image_lv);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setOnRefreshListener(this);
		image_lv = mPullRefreshListView.getRefreshableView();
		image_tv = (TextView) view.findViewById(R.id.image_tv);
		
		image_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				PullToRefreshViewPagerActivity.actionStart(getActivity(),localGalleries,id);
			}
		});
		image_lv.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				postion=image_lv.getFirstVisiblePosition();
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		
		if (localGalleries.isEmpty()) {
			requestGallries(1);
		}
		setImage_lvAdapter();
		image_lv.postDelayed(new Runnable() {  
		    @Override  
		    public void run() {  
//		    	image_lv.setSelection(postion);  
		    }  
		},150);  
		image_lv.postDelayed(new Runnable() {
			@Override
			public void run() {
//				image_lv.setSelection(postion);
			}
		}, 250);
		
		LogUtil.e("onCreateView", getArguments().getInt("id")+"");
		return view;
	}
	



	@Override
	public void onResume() {
		super.onResume();
//		LogUtil.e(TAG, "onResume"+getArguments().getInt("classifyId"));
	}
	private void requestGallriesForHead() {
		if (isLoading) {
			return;
		}
		isLoading = true;
		isForHead=true;
		MyApplication.imageFetcherTianGouImp
		.getImageDetailsByID(getArguments().getInt("id"), listener);
//		.getImageListByID(pageNum, MyConstants.PAGE_SIZE, getArguments().getInt("classifyId"), listener);
	}
	private void requestGallries(int paramterPagNum) {
		if (isLoading) {
			return;
		}
		pageNum=paramterPagNum;
		isLoading = true;
		isForHead=false;
		MyApplication.imageFetcherTianGouImp
		.getImageDetailsByID(getArguments().getInt("id"), listener);
//		.getImageListByID(getArguments().getInt("classifyId"), pageNum, MyConstants.PAGE_SIZE,isForHead,listener);
		pageNum++;
	}

	protected void setImage_lvAdapter() {
		galleryaAdapter = new CommonAdapter<Picture>(getActivity(),
				localGalleries, R.layout.item_image) {

			@Override
			public void convert(ViewHolder holder, Picture t, int position) {
				TextView textView = holder.getView(R.id.item_text);
				ImageView imageView = holder.getView(R.id.item_img);
				textView.setText(localGalleries.get(position).getSrc());
				MyApplication.imageLoader.displayImage(MyUrl.TIANGOU_SERVICE
						+ localGalleries.get(position).getSrc(), imageView);
//				textView.setText(position+"");
//				imageView.setImageResource(R.drawable.sample);
			}
		};
			image_lv.setAdapter(galleryaAdapter);
	}

	public static GallryDetailsFragment getInstance(int id) {
		GallryDetailsFragment gallriesDetailsFragment = new GallryDetailsFragment();
		Bundle args = new Bundle();
		args.putInt("id", id);
		LogUtil.e(TAG, id+"aaaaaaaaaa");
		gallriesDetailsFragment.setArguments(args);

		return gallriesDetailsFragment;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.select:
			LogUtil.e(TAG, image_lv.getFirstVisiblePosition()+"");
			
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		requestGallriesForHead();
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		requestGallries(pageNum);
		
	}

}
