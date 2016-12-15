package com.cyq.mvshow.fragment;

import java.util.ArrayList;

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
import com.cyq.mvshow.model.Gallery;
import com.cyq.mvshow.widget.TitleView;

@SuppressLint("NewApi")
public class GalleryCollectFragment extends Fragment implements OnClickListener  {
	private static final String TAG = GalleryCollectFragment.class.getSimpleName();
	private TitleView titleView;
	
	private GridView mGridView;
	private GalleryAdapter mGalleryAdapter;
	
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
		mGalleryAdapter.localGalleries=getCollectGallriesFromDB();
		mGalleryAdapter.notifyDataSetChanged();
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if (mGalleryAdapter.isCollectModel()) {
					ImageView selectImg = (ImageView) view.findViewById(R.id.select_img);
					//如果是收藏模式
					if (mGalleryAdapter.selectGalleries.contains(mGalleryAdapter.localGalleries.get((int) id))) {
						mGalleryAdapter.selectGalleries.remove(mGalleryAdapter.localGalleries.get((int) id));
						mGalleryAdapter.selectTags.remove(mGalleryAdapter.tags.get((int) id));
						selectImg.setImageDrawable(getActivity().getDrawable(R.drawable.pr_unselected));
					}else {
						mGalleryAdapter.selectGalleries.add(mGalleryAdapter.localGalleries.get((int) id));
						mGalleryAdapter.selectTags.add(mGalleryAdapter.tags.get((int) id));
						selectImg.setImageDrawable(getActivity().getDrawable(R.drawable.pr_selected));
					}
					mGalleryAdapter.notifyDataSetChanged();
				}else{
					//如果不是收藏模式，跳转到对应的图片专辑中
					PictureListsActivity.actionStart(getActivity(),mGalleryAdapter.localGalleries.get((int) id).getId());
				}
				
				
			}
		});
		/*
		 * 初始化foot_layout
		 */
		foot_layout=(LinearLayout) view.findViewById(R.id.foot_layout);
		decollect_allselect=(LinearLayout) view.findViewById(R.id.decollect_allselect);
		decollect_confrim=(LinearLayout) view.findViewById(R.id.decollect_confrim);
		allSelect_tv=(TextView) view.findViewById(R.id.allSelect_tv);
		decollect_allselect.setOnClickListener(this);
		decollect_confrim.setOnClickListener(this);
		foot_layout.setVisibility(View.GONE);
		return view;
	}
	

	@Override
	public void onResume() {
		super.onResume();
		if (mGalleryAdapter!=null) {
			mGalleryAdapter.localGalleries=getCollectGallriesFromDB();
			for (Gallery gallery : mGalleryAdapter.localGalleries) {
				mGalleryAdapter.tags.add(new StringBuilder("初始态"));
			}
			mGalleryAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.decollect_allselect:
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
		case R.id.decollect_confrim:
			for (Gallery gallery : mGalleryAdapter.selectGalleries) {
				TianGouImageDB.getInstance(MyApplication.getcContext()).deleteGallery(gallery);
				mGalleryAdapter.localGalleries.remove(gallery);
			}
			for (StringBuilder tag : mGalleryAdapter.selectTags) {
				mGalleryAdapter.tags.remove(tag);
			}
			changeShowModel(mGalleryAdapter.isCollectModel());
			Toast.makeText(getActivity(), "取消了收藏", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

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
			//点击的时候是收藏模式，那么变成正常模式
			mGalleryAdapter.setCollectModel(false);
			mGalleryAdapter.selectGalleries.clear();
			mGalleryAdapter.selectTags.clear();
			foot_layout.setVisibility(View.GONE);
			
			titleView.setRightBtnBackground(R.drawable.menu_collected);
		}else{
			//点击的时候是正常模式，则变成收藏模式
			mGalleryAdapter.setCollectModel(true);
			foot_layout.setVisibility(View.VISIBLE);
			titleView.setRightBtnBackground(R.drawable.menu_cancel);
		}
			mGalleryAdapter.notifyDataSetChanged();
	}

}
