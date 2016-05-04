package com.cyq.mvshow.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.R;
import com.cyq.mvshow.adapter.GalleryAdapter;
import com.cyq.mvshow.db.TianGouImageDB;
import com.cyq.mvshow.model.Gallery;
import com.cyq.mvshow.utils.LogUtil;
import com.cyq.mvshow.widget.TitleView;

public class GalleryCollectFragment extends Fragment  {
	private static final String TAG = GalleryCollectFragment.class.getSimpleName();
	private TitleView titleView;
	
	private GridView mGridView;
	private GalleryAdapter mGalleryAdapter;
	private ArrayList<Gallery> localGalleries;
	
	private LinearLayout foot_layout;
	private LinearLayout decollect_allselect;
	private LinearLayout decollect_confrim;
	private TextView allSelect_tv;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gallry_collect,container, false);
		
		/*
		 * 初始化TitleView
		 */
		titleView=(TitleView) view.findViewById(R.id.titleView);
		titleView.setLeftBtnVisibility(View.VISIBLE);
		titleView.setRightBtnVisibility(View.VISIBLE);
		titleView.setRightBtnBackground(R.drawable.menu_edit);
		titleView.setRightBtn2Visibility(View.GONE);
		titleView.setRightBtnClickListener(titleViewBtnClick);
		
		/*
		 * 初始化GridView
		 */
		mGridView = (GridView) view.findViewById(R.id.mGridView);
		mGalleryAdapter = new GalleryAdapter(getActivity());
		mGridView.setAdapter(mGalleryAdapter);
		localGalleries=getCollectGallriesFromDB();
		mGalleryAdapter.setGroup(localGalleries);
		mGalleryAdapter.notifyDataSetChanged();
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

/**
 * 从数据库中获取收藏的Galleries
 */
	private ArrayList<Gallery> getCollectGallriesFromDB() {
		return TianGouImageDB.getInstance(MyApplication.getcContext()).loadGalleries();
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
				if (mGalleryAdapter.isCollectModel()) {
					//点击的时候是收藏模式，那么变成正常模式
					mGalleryAdapter.setCollectModel(false);
					foot_layout.setVisibility(View.GONE);
					titleView.setRightBtnBackground(R.drawable.menu_collect);
				}else{
					//点击的时候是正常模式，则变成收藏模式
					mGalleryAdapter.setCollectModel(true);
					foot_layout.setVisibility(View.VISIBLE);
					titleView.setRightBtnBackground(R.drawable.menu_cancel);
				}
				mGalleryAdapter.notifyDataSetChanged();

				break;
			default:
				break;
			}
		}
	};

}
