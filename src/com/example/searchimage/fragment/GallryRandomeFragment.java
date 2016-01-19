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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.activity.GallryDetailsActivity;
import com.example.searchimage.base.CommonAdapter;
import com.example.searchimage.base.ViewHolder;
import com.example.searchimage.imageutils.GetGalleriesListener;
import com.example.searchimage.model.Gallery;
import com.example.searchimage.model.GetGalleriesRespone;
import com.example.searchimage.utils.LogUtil;
import com.example.searchimage.utils.MyConstants;
import com.example.searchimage.utils.MyUrl;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

public class GallryRandomeFragment extends Fragment implements OnClickListener, OnRefreshListener2<GridView> {
	private static final String TAG = GallryRandomeFragment.class.getSimpleName();
	private PullToRefreshGridView mPullToRefreshGridView;
	private GridView image_lv;
	private Button select ;
	private TextView image_tv;
	private ArrayList<Gallery> localGalleries = new ArrayList<Gallery>();
	private int pageNum = 1;// 请求的页数
	private boolean isLoading = false;
	private CommonAdapter<Gallery> galleryaAdapter;
	private int postion;
	private boolean isForHead;
	private GetGalleriesListener listener = new GetGalleriesListener() {
		@Override
		public void success(GetGalleriesRespone getGalleryListRespone,boolean isforhead) {
			getGalleryListRespone.getTngou();
			if (getGalleryListRespone.getTngou()==null) {
				LogUtil.e(TAG, "请求的数据为空");
				Toast.makeText(getActivity(), "最后一页啦", Toast.LENGTH_SHORT).show();
				return;
			}else if (getGalleryListRespone.getTngou().size()<MyConstants.PAGE_SIZE) {
				Toast.makeText(getActivity(), "最后一页啦", Toast.LENGTH_SHORT).show();
			}
			if (isforhead) {
				pageNum=1;
				localGalleries.clear();
			}
			for (Gallery gallery : getGalleryListRespone.getTngou()) {
				localGalleries.add(gallery);
			}
			galleryaAdapter.notifyDataSetChanged();
			mPullToRefreshGridView.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					mPullToRefreshGridView.onRefreshComplete();
					
				}
			},500);
			LogUtil.e(TAG, image_lv.getFirstVisiblePosition()+"::"+image_lv.getSelectedItemPosition());
			isLoading = false;
			// 数据加载完后，设置是否为空，是否有更多
		}

		@Override
		public void erro(String erroString) {
			isLoading = false;
			int errorCode = 0;
			String errorMessage = "加载失败，点击加载更多";
			mPullToRefreshGridView.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					mPullToRefreshGridView.onRefreshComplete();
					
				}
			},500);
			
		}
	};

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
		View view = inflater.inflate(R.layout.fragment_gallry_random, null);
		
		mPullToRefreshGridView = (PullToRefreshGridView) view.findViewById(R.id.image_lv);
		mPullToRefreshGridView.setMode(Mode.BOTH);
		mPullToRefreshGridView.setOnRefreshListener(this);
		image_lv = mPullToRefreshGridView.getRefreshableView();
		image_tv = (TextView) view.findViewById(R.id.image_tv);
		select = (Button) view.findViewById(R.id.select);
		select.setOnClickListener(this);
		image_tv.setText(getArguments().getInt("classifyId") + "");
		image_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GallryDetailsActivity.actionStart(getActivity(),galleryaAdapter.getItem((int) id).getId());
				
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
		    	image_lv.setSelection(postion);  
		    }  
		},150);  
		image_lv.postDelayed(new Runnable() {
			@Override
			public void run() {
				image_lv.setSelection(postion);
			}
		}, 250);
		
		LogUtil.e("onCreateView", getArguments().getInt("classifyId")+"");
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
		.getGallriesNews(pageNum, MyConstants.PAGE_SIZE, 0, isForHead, listener);
	}
	private void requestGallries(int paramterPagNum) {
		if (isLoading) {
			return;
		}
		pageNum=paramterPagNum;
		isLoading = true;
		isForHead=false;
		MyApplication.imageFetcherTianGouImp
		.getGallriesNews(pageNum, MyConstants.PAGE_SIZE, 0, isForHead, listener);
//		.getImageListByID(getArguments().getInt("classifyId"), pageNum, MyConstants.PAGE_SIZE,isForHead,listener);
//		.getImageListByID(pageNum, MyConstants.PAGE_SIZE, getArguments().getInt("classifyId"), listener);
		pageNum++;
	}

	protected void setImage_lvAdapter() {
		galleryaAdapter = new CommonAdapter<Gallery>(getActivity(),
				localGalleries, R.layout.item_image_random) {

			@Override
			public void convert(ViewHolder holder, Gallery t, int position) {
				TextView textView = holder.getView(R.id.item_text);
				ImageView imageView = holder.getView(R.id.item_img);
				MyApplication.imageLoader.displayImage(MyUrl.TIANGOU_SERVICE
						+ localGalleries.get(position).getImg(), imageView);
				textView.setText(position+1+localGalleries.get(position).getTitle());
//				textView.setText(position+"");
//				imageView.setImageResource(R.drawable.sample);
			}
		};
			image_lv.setAdapter(galleryaAdapter);
	}

	public static GallryRandomeFragment getInstance(int pos) {
		GallryRandomeFragment gallryItemFragment = new GallryRandomeFragment();
		Bundle args = new Bundle();
		args.putInt("classifyId", pos);
		gallryItemFragment.setArguments(args);

		return gallryItemFragment;
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
	public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
		requestGallriesForHead();
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
		requestGallries(pageNum);
		
	}

}
