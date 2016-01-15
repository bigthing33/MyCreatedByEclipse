package com.example.searchimage.fragment;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.base.CommonAdapter;
import com.example.searchimage.base.ViewHolder;
import com.example.searchimage.imageutils.GetGalleriesListener;
import com.example.searchimage.model.Gallery;
import com.example.searchimage.model.GetGalleriesRespone;
import com.example.searchimage.utils.MyConstants;

public class GallriesFragment extends Fragment {
	private static final String TAG = GallriesFragment.class.getSimpleName();
	private ListView image_lv;
	private TextView imagetitle_tv;
	private TextView image_tv;
	private ArrayList<Gallery> localGalleries = new ArrayList<Gallery>();
	private CommonAdapter<Gallery> galleryaAdapter;
	private LoadMoreListViewContainer loadmoreContainer;
	private int pageNum = 1;// 请求的页数
	private boolean isLoading = false;
    private int scrolledX;
	private int scrolledY;
	private GetGalleriesListener listener = new GetGalleriesListener() {
		@Override
		public void success(GetGalleriesRespone getGalleryListRespone,int requestPageNum) {
			getGalleryListRespone.getTngou();
			if (getGalleryListRespone.getTngou()==null) {
				pageNum--;
			}else if (getGalleryListRespone.getTngou().size()<MyConstants.PAGE_SIZE) {
				pageNum--;
			}
			if (requestPageNum==1 ) {
				localGalleries.clear();
			}
			for (Gallery gallery : getGalleryListRespone.getTngou()) {
				localGalleries.add(gallery);
			}
			imagetitle_tv.setText(localGalleries.get(1).getTitle());
			setImage_lvAdapter();
			isLoading = false;
			// 数据加载完后，设置是否为空，是否有更多
			loadmoreContainer.loadMoreFinish(localGalleries.isEmpty(), getGalleryListRespone.getTngou()!=null);
		}

		@Override
		public void erro(String erroString) {
			isLoading = false;
			int errorCode = 0;
			String errorMessage = "加载失败，点击加载更多";
			loadmoreContainer.loadMoreError(errorCode, errorMessage);
		}
	};

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gallryitem, null);
		galleryaAdapter=null;
		image_lv = (ListView) view.findViewById(R.id.image_lv);
		image_tv = (TextView) view.findViewById(R.id.image_tv);
		imagetitle_tv = (TextView) view.findViewById(R.id.imagetitle_tv);
		loadmoreContainer = (LoadMoreListViewContainer) view
				.findViewById(R.id.loadmorecontainer);
		loadmoreContainer.useDefaultFooter();// 使用默认的页脚
		// 向上滑动触发的事件
		loadmoreContainer.setLoadMoreHandler(new LoadMoreHandler() {

			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				requestGallries(pageNum);
			}
		});
		image_tv.setText(getArguments().getInt("classifyId") + "");
		
		image_lv.setOnScrollListener(new OnScrollListener() {   
			  
			/**  
		     * 滚动状态改变时调用  
		     */  
		    @Override  
		    public void onScrollStateChanged(AbsListView view, int scrollState) {   
		        // 不滚动时保存当前滚动到的位置  
		        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {   
		                scrolledX = image_lv.getScrollX();   
		                scrolledY = image_lv.getScrollY();   
		        }   
		    }   
		  
		    /**  
		     * 滚动时调用  
		     */  
		    @Override  
		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {   
		    }   
		});
		if (localGalleries.isEmpty()) {
			requestGallries(0);
		}
		setImage_lvAdapter();
//		LogUtil.e(TAG, getArguments().getInt("classifyId")+localGalleries.get(1).getImg());
		return view;
	}

	private void requestGallries(int paramterPagNum) {
		if (isLoading) {
			return;
		}
		pageNum=paramterPagNum;
		isLoading = true;
		MyApplication.imageFetcherTianGouImp
				.getImageListByID(getArguments().getInt("classifyId"), pageNum,
						MyConstants.PAGE_SIZE, listener);
		pageNum++;
	}

	protected void setImage_lvAdapter() {

		if (galleryaAdapter == null) {
			galleryaAdapter = new CommonAdapter<Gallery>(getActivity(),
					localGalleries, R.layout.item_image) {

				@Override
				public void convert(ViewHolder holder, Gallery t, int position) {
					TextView textView = holder.getView(R.id.item_text);
					ImageView imageView = holder.getView(R.id.item_img);
//					MyApplication.imageLoader.displayImage(
//							"http://tnfs.tngou.net/img"
//									+ localGalleries.get(position).getImg(),
//							imageView);
//					textView.setText(position+localGalleries.get(position).getTitle());
					textView.setText(position+"");
					imageView.setImageResource(R.drawable.sample);
				}
			};
			image_lv.setAdapter(galleryaAdapter);
			image_lv.scrollTo(scrolledX, scrolledY); 
		}else {
			galleryaAdapter.notifyDataSetChanged();
		}
	}

	public static GallriesFragment getInstance(int pos) {
		GallriesFragment gallryItemFragment = new GallriesFragment();
		Bundle args = new Bundle();
		args.putInt("classifyId", pos);
		gallryItemFragment.setArguments(args);

		return gallryItemFragment;
	}

}
