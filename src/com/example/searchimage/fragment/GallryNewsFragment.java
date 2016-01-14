package com.example.searchimage.fragment;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

import java.util.ArrayList;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.base.CommonAdapter;
import com.example.searchimage.base.ViewHolder;
import com.example.searchimage.imageutils.GetGalleriesListener;
import com.example.searchimage.model.Gallery;
import com.example.searchimage.model.GetGalleriesRespone;
import com.example.searchimage.utils.MyConstants;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 显示最新20张的图片
 * @author Administrator
 *
 */

public class GallryNewsFragment extends Fragment {
	private ListView image_lv;
	private TextView image_tv;
	private ArrayList<Gallery> localGalleries;
	private CommonAdapter<Gallery> galleryaAdapter;
	private LoadMoreListViewContainer loadmoreContainer;
	private int newsId=0;//最新热词的id
	private boolean isLoading=false;
	GetGalleriesListener listener=new GetGalleriesListener() {
		
		@Override
		public void success(GetGalleriesRespone getGalleryListRespone) {
			getGalleryListRespone.getTngou();
				setImage_lvAdapter(getGalleryListRespone.getTngou());
				isLoading=false;
				loadmoreContainer.loadMoreFinish(galleryaAdapter.isEmpty(),
						localGalleries != null && localGalleries.size() == MyConstants.PAGE_SIZE);
		}
		
		@Override
		public void erro(String erroString) {
			isLoading=false;
			loadmoreContainer.loadMoreFinish(galleryaAdapter.isEmpty(),
					localGalleries != null && localGalleries.size() == MyConstants.PAGE_SIZE);
		}
	};

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		localGalleries=new ArrayList<Gallery>();
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gallries, null);
		image_lv=(ListView) view.findViewById(R.id.image_lv);
		image_tv=(TextView) view.findViewById(R.id.image_tv);
		loadmoreContainer = (LoadMoreListViewContainer) view.findViewById(R.id.loadmorecontainer);
		loadmoreContainer.useDefaultFooter();//使用默认的页脚
		//向上滑动触发的事件
		loadmoreContainer.setLoadMoreHandler(new LoadMoreHandler() {

			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				// TODO 执行请求方法
				 requestGallries();
				 galleryaAdapter.notifyDataSetChanged();
			}
		});
		image_tv.setText(newsId+"");
		setImage_lvAdapter(localGalleries);
		requestGallries();
		return view;
	}

	private void requestGallries() {
		if (isLoading) {
			return;
		}
		isLoading=true;
		MyApplication.imageFetcherTianGouImp.getImgNews(newsId, MyConstants.PAGE_SIZE, 1, listener);
		newsId++;
	}
	

	protected void setImage_lvAdapter(ArrayList<Gallery> tngou) {
		for (Gallery gallery : tngou) {
			localGalleries.add(gallery);
		}
		if (localGalleries.size()==0) {
			galleryaAdapter=new CommonAdapter<Gallery>(getActivity(), localGalleries, R.layout.item_image) {

				@Override
				public void convert(ViewHolder holder, Gallery t, int position) {
					TextView textView = holder.getView(R.id.item_text);
					ImageView imageView = holder.getView(R.id.item_img);
					MyApplication.imageLoader.displayImage("http://tnfs.tngou.net/img"+localGalleries.get(position).getImg(), imageView);
					textView.setText(localGalleries.get(position).getTitle());
				}
			};
			image_lv.setAdapter(galleryaAdapter);
		}
	}


	public static Fragment getInstance() {
		GallryNewsFragment gallryNewsFragment=new GallryNewsFragment();
//		Bundle args=new Bundle();
//		args.putInt("classifyId", pos);
//		gallryItemFragment.setArguments(args);
		
		return gallryNewsFragment ;
	}

}
