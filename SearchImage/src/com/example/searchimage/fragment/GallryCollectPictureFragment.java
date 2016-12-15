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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.base.CommonAdapter;
import com.example.searchimage.base.ViewHolder;
import com.example.searchimage.db.TianGouImageDB;
import com.example.searchimage.listener.ListenerImp;
import com.example.searchimage.model.Picture;
import com.example.searchimage.utils.DialogUtils;
import com.example.searchimage.utils.LogUtil;
import com.example.searchimage.utils.MyImageLoader;
import com.example.searchimage.utils.MyUrl;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class GallryCollectPictureFragment extends Fragment implements OnClickListener, OnRefreshListener2<ListView> {
	private static final String TAG = GallryCollectPictureFragment.class.getSimpleName();
	private PullToRefreshListView mPullRefreshListView;
	private ListView mListView;
	private ArrayList<Picture> mlocalPictures = new ArrayList<Picture>();
	private CommonAdapter<Picture> mPictureAdapter;
	private int mPostion;

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
	public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
		LogUtil.e(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_gallry_collect_picture, null);
		
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.image_lv);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mPullRefreshListView.setOnRefreshListener(this);
		mListView = mPullRefreshListView.getRefreshableView();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, final long id) {
				DialogUtils.showIsDeletePictureDialog(getActivity(), mlocalPictures.get((int) id),new ListenerImp() {
					@Override
					public void success(Object object) {
						mlocalPictures.remove((int) id);
						mPictureAdapter.notifyDataSetChanged();
					}
				});
				return true;
			}
		});
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				mPostion=mListView.getFirstVisiblePosition();
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		mlocalPictures=TianGouImageDB.getInstance(MyApplication.getcContext()).loadPictures();
		setImage_lvAdapter();
		mListView.postDelayed(new Runnable() {  
		    @Override  
		    public void run() {  
		    	mListView.setSelection(mPostion);  
		    }  
		},150);  
		mListView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mListView.setSelection(mPostion);
			}
		}, 250);
		
		return view;
	}
	



	@Override
	public void onResume() {
		super.onResume();
		LogUtil.e(TAG, "onResume");
	}

	protected void setImage_lvAdapter() {
		mPictureAdapter = new CommonAdapter<Picture>(getActivity(),mlocalPictures, R.layout.item_image) {
			@Override
			public void convert(ViewHolder holder, Picture t, final int position) {
				 final MyImageLoader myImageLoader=new MyImageLoader();
				TextView textView = holder.getView(R.id.item_text);
				ImageView imageView = holder.getView(R.id.item_img);
				ProgressBar progress_img = holder.getView(R.id.progress_img);
				TextView reload_tv = holder.getView(R.id.reload_tv);
				reload_tv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						myImageLoader.displayImage(MyUrl.TIANGOU_SERVICE+ mlocalPictures.get(position).getSrc());
					}
				});
				myImageLoader.mReload_tv=reload_tv;
				myImageLoader.mImageView=imageView;
				myImageLoader.mProgress_img=progress_img;
				myImageLoader.displayImage(MyUrl.TIANGOU_SERVICE+ mlocalPictures.get(position).getSrc());
				textView.setText(position+1+":"+mlocalPictures.get(position).getGallery());
			}
		};
			mListView.setAdapter(mPictureAdapter);
	}

	public static GallryCollectPictureFragment getInstance() {
		GallryCollectPictureFragment gallryItemFragment = new GallryCollectPictureFragment();
		Bundle args = new Bundle();
		gallryItemFragment.setArguments(args);
		return gallryItemFragment;
	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		
	}

}
