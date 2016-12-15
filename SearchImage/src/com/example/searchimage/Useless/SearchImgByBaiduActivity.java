package com.example.searchimage.Useless;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.base.BaseActivity;
import com.example.searchimage.base.CommonAdapter;
import com.example.searchimage.base.ViewHolder;
import com.example.searchimage.imageutils.ImageFetcher;
import com.example.searchimage.imageutils.ImageFetcher.ImageFetcherListener;
import com.example.searchimage.imageutils.imp.ImageDownloadThread;
import com.example.searchimage.imageutils.imp.ImageFetcherBaiduImp;
import com.example.searchimage.imageutils.imp.ImageDownloadThread.Listener;
import com.example.searchimage.model.Image;
import com.example.searchimage.model.SearchImageRespone;
import com.example.searchimage.utils.LogUtil;
import com.example.searchimage.utils.MyUtils;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

public class SearchImgByBaiduActivity extends BaseActivity implements
		OnClickListener {
	protected static final String TAG = SearchImgByBaiduActivity.class.getSimpleName();
	private Context mContext = SearchImgByBaiduActivity.this;
	private EditText searchImg_et;
	private Button searchImg_btn;
	private TextView searchImgResult_tv;
	private ListView imagelistView;
	private CommonAdapter<Image> searchImageAdapter;
	private ArrayList<Image> localImglist = new ArrayList<Image>();
	private ImageDownloadThread imageDownloadThread;
	private int mPageNum = 0;
	private boolean isLoadingImg;
	private int mPreNum = 5;// 默认为5；缓存图片个数，范围是1-60
	private ImageFetcher imageFetcher;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.z_useless_activity_apistore);
		initUI();
		imageFetcher = new ImageFetcherBaiduImp(mContext);
		imageFetcher.setListener(new ImageFetcherListener() {

			@Override
			public void ImageFetcherErro(String responseString) {
				searchImgResult_tv.setText(responseString + "");

			}

			@Override
			public void ImageFetcherSuccess(
					SearchImageRespone searchImageRespone,
					Boolean isCleanListView) {
	
				searchImgResult_tv.setText("返回的图片数是："
						+ searchImageRespone.getReturnNumber() + " 搜索到的图片总数是"
						+ searchImageRespone.getTotalNumber());
				ArrayList<Image> downloadingImages = searchImageRespone
						.getResultArray();
				LogUtil.e(TAG, downloadingImages.size() + "downloadingImages");
				// 成功返回SearchImageRespone对象后，取得每个图片的地址，根据地址去获取图片
				imageDownloadThread.queueThumbnail(downloadingImages,isCleanListView);

			}
		});
		imageDownloadThread = new ImageDownloadThread(new Handler());
		imageDownloadThread.setListener(new Listener() {

			@Override
			public void completedAllImgDownload() {
				isLoadingImg = false;

			}

			@Override
			public void imageDownloaded(Image image, boolean isCleanListView) {
				if (isCleanListView) {
					clearListView();
				}
				localImglist.add(image);
				setAdapter();

			}
		});
		imageDownloadThread.start();
		// getLooper放在start之后，保证线程就绪
		imageDownloadThread.getLooper();
	}

	@Override
	public void onDestroy() {
		imageDownloadThread.quitImageDownloadThread();
		super.onDestroy();
	}

	@Override
	public void initUI() {
		searchImg_et = (EditText) findViewById(R.id.searchImg_et);
		searchImg_btn = (Button) findViewById(R.id.searchImg_btn);
		searchImgResult_tv = (TextView) findViewById(R.id.searchImgResult_tv);
		imagelistView = (ListView) findViewById(R.id.imagelistView);
		searchImg_btn.setOnClickListener(this);
		imagelistView.setOnScrollListener(new PauseOnScrollListener(
				MyApplication.imageLoader, true, true));// 两个分别表示拖动下拉条和滑动过程中暂停加载
	}

	private void showRequestImg(String searchTag, Boolean isCleanView) {
		isLoadingImg = true;
		imageFetcher.searchImg(searchTag, mPageNum, mPreNum,isCleanView);
		mPageNum = mPageNum + mPreNum;

	}

	private void setAdapter() {

		if (localImglist == null) {
			imagelistView.setAdapter(null);
		} else if (localImglist.size() == 1) {
			searchImageAdapter = new CommonAdapter<Image>(mContext,
					localImglist, R.layout.item_image) {

				@Override
				public void convert(ViewHolder holder, Image t, int position) {
					if (position >= localImglist.size() - mPreNum
							&& isLoadingImg == false && position >= mPreNum - 1) {
						showRequestImg(searchImg_et.getText().toString(),false);
					}
					ImageView item_img = holder.getView(R.id.item_img);
					TextView item_text = holder.getView(R.id.item_text);
					item_text.setText(position + "");
					if (localImglist.get(position).getBitmap() != null) {
						item_img.setImageBitmap(localImglist.get(position)
								.getBitmap());
					}

				}

			};
			imagelistView.setAdapter(searchImageAdapter);
		} else {
			searchImageAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchImg_btn:
			if (!MyUtils.isNetworkConnected()) {
				showToast("网络不可用");
				return;
			}
			mPageNum = 0;
			clearListView();
			MyApplication.imageLoader.stop();
			showRequestImg(searchImg_et.getText().toString(),true);
			break;

		default:
			break;
		}

	}

	private void clearListView() {
		if (localImglist.size() > 0) {
			localImglist.clear();
			if (searchImageAdapter != null) {
				searchImageAdapter.notifyDataSetChanged();
			}
		}
	}

	public static void actionStart(Context context, Class<?> activityClass) {
		Intent intent = new Intent(context, activityClass);
		context.startActivity(intent);
	}

}
