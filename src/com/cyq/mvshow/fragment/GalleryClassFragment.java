package com.cyq.mvshow.fragment;

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

import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.R;
import com.cyq.mvshow.activity.PictureListsActivity;
import com.cyq.mvshow.adapter.ClassAdapter;
import com.cyq.mvshow.listener.GetClassesListener;
import com.cyq.mvshow.model.GetGalleryclassRespone;
import com.cyq.mvshow.widget.TitleView;

@SuppressLint("NewApi")
public class GalleryClassFragment extends Fragment implements OnClickListener  {
	private static final String TAG = GalleryClassFragment.class.getSimpleName();
	private TitleView titleView;
	
	private GridView mGridView;
	private ClassAdapter mClassAdapter;
	
	private GetGalleryclassRespone mGalleryclassRespone;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gallry_collect,container, false);
		titleView=(TitleView) view.findViewById(R.id.titleView);
		mGridView = (GridView) view.findViewById(R.id.mGridView);
		MyApplication.imageFetcherTianGouImp.getImgClassify(new GetClassesListener() {
			

			@Override
			public void success(GetGalleryclassRespone getGalleryclassRespone) {
				mGalleryclassRespone=getGalleryclassRespone;
				/*
				 * 初始化TitleView
				 */
				titleView.setLeftBtnVisibility(View.VISIBLE);
				titleView.setRightBtnVisibility(View.GONE);
				titleView.setRightBtn2Visibility(View.GONE);
				titleView.setRightBtnClickListener(titleViewBtnClick);
				titleView.setTitle("分类");
				/*
				 * 初始化GridView
				 */

				mClassAdapter = new ClassAdapter(getActivity());
				mGridView.setAdapter(mClassAdapter);
				mClassAdapter.galleryclassifies=mGalleryclassRespone.getTngou();
				mClassAdapter.notifyDataSetChanged();
				mGridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
						//如果不是收藏模式，跳转到对应的图片专辑中 TODO
						PictureListsActivity.actionStart(getActivity(),mClassAdapter.galleryclassifies.get((int) id).getId());
					}
				});
				
			}
			
			@Override
			public void erro(String erroString) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return view;
	}
	

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	@Override
	public void onClick(View v) {
		
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
				break;
			default:
				break;
			}
		}
	};

}
