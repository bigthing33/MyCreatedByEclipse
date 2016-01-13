package com.example.searchimage.fragment;

import java.util.ArrayList;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.base.CommonAdapter;
import com.example.searchimage.base.ViewHolder;
import com.example.searchimage.imageutils.GetGalleriesListener;
import com.example.searchimage.model.Gallery;
import com.example.searchimage.model.GetGalleryListRespone;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.R.integer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GallryItemFragment extends Fragment {
	private ListView image_lv;
	private TextView image_tv;
	private ArrayList<Gallery> localGalleries;
	private CommonAdapter<Gallery> galleryaAdapter;
	private int pageNum=1;//请求的页数
	private boolean isLoading=false;
	GetGalleriesListener listener=new GetGalleriesListener() {
		
		@Override
		public void success(GetGalleryListRespone getGalleryListRespone) {
			getGalleryListRespone.getTngou();
				setImage_lvAdapter(getGalleryListRespone.getTngou());
				isLoading=false;
			
		}
		
		@Override
		public void erro(String erroString) {
			isLoading=false;
			
		}
	};

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		localGalleries=new ArrayList<Gallery>();

		requestGallries();
		super.onCreate(savedInstanceState);
	}

	private void requestGallries() {
		isLoading=true;
		MyApplication.imageFetcherTianGouImp.getImageListByID(getArguments().getInt("classifyId"), pageNum, 20, listener);
		pageNum++;
	}

	protected void setImage_lvAdapter(ArrayList<Gallery> tngou) {
		for (Gallery gallery : tngou) {
			localGalleries.add(gallery);
		}
		if (localGalleries.size()==20) {
			galleryaAdapter=new CommonAdapter<Gallery>(getActivity(), localGalleries, R.layout.item_image) {

				@Override
				public void convert(ViewHolder holder, Gallery t, int position) {
					TextView textView = holder.getView(R.id.item_text);
					ImageView imageView = holder.getView(R.id.item_img);
					MyApplication.imageLoader.displayImage("http://tnfs.tngou.net/img"+localGalleries.get(position).getImg(), imageView);
					textView.setText(localGalleries.get(position).getTitle());
					if (position==localGalleries.size()-1&&!isLoading) {
						requestGallries();
					}
					
				}
			};
			
			image_lv.setAdapter(galleryaAdapter);
		}else {
			galleryaAdapter.notifyDataSetChanged();
		}
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gallryitem, null);
		image_lv=(ListView) view.findViewById(R.id.image_lv);
		image_tv=(TextView) view.findViewById(R.id.image_tv);
		image_tv.setText(getArguments().getInt("classifyId")+"");
		return view;
	}

	public static Fragment getInstance(int pos) {
		GallryItemFragment gallryItemFragment=new GallryItemFragment();
		Bundle args=new Bundle();
		args.putInt("classifyId", pos);
		gallryItemFragment.setArguments(args);
		
		return gallryItemFragment ;
	}

}
