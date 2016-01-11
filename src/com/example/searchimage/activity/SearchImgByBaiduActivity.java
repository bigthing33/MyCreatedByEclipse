package com.example.searchimage.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.searchimage.MyApplication;
import com.example.searchimage.R;
import com.example.searchimage.base.BaseActivity;
import com.example.searchimage.base.CommonAdapter;
import com.example.searchimage.base.ViewHolder;
import com.example.searchimage.model.Image;
import com.example.searchimage.model.SearchImageRespone;
import com.example.searchimage.services.ImageDownloadThread;
import com.example.searchimage.services.ImageDownloadThread.Listener;
import com.example.searchimage.utils.ImageFetcher;
import com.example.searchimage.utils.LogUtil;
import com.example.searchimage.utils.MyUrl;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

public class SearchImgByBaiduActivity extends BaseActivity implements OnClickListener {
	protected static final String TAG = SearchImgByBaiduActivity.class.getSimpleName();
	private Context mContext = SearchImgByBaiduActivity.this;
	private EditText searchImg_et;
	private Button searchImg_btn;
	private TextView searchImgResult_tv;
	private ImageView searchImgResult_imgae;
	private ListView imagelistView;
	private CommonAdapter<Image> searchImageAdapter;
	private ArrayList<Image> localImglist = new ArrayList<>();
	private ImageDownloadThread<ImageView> imageDownloadThread;
	private int mPageNum=0;
	private boolean isLoadingImg;
	private int mPreNum=5;//默认为5；缓存图片个数，范围是1-60

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apistore);
		initUI();
		imageDownloadThread = new ImageDownloadThread<>(new Handler());
		imageDownloadThread.setListener(new Listener() {

			@Override
			public void imageDownloaded(Image image) {
				
				localImglist.add(image);
				setAdapter();
			}

			@Override
			public void completedAllImgDownload() {
				isLoadingImg=false;
				
			}
		});
		imageDownloadThread.start();
		// getLooper放在start之后，保证线程就绪
		imageDownloadThread.getLooper();
	}

	private void setAdapter() {
	
		if (localImglist.size() == 0) {
			imagelistView.setAdapter(null);
		} else if (localImglist.size()==1) {
			searchImageAdapter=new CommonAdapter<Image>(mContext, localImglist, R.layout.item_image) {

				@Override
				public void convert(
						ViewHolder holder, Image t,
						int position) {
					if (position>=localImglist.size()-mPreNum&&isLoadingImg==false&&position>=mPreNum-1) {
						searchImg(searchImg_et.getText().toString(),mPageNum);
					}
					ImageView item_img=holder.getView(R.id.item_img);
					TextView item_text=holder.getView(R.id.item_text);
					item_text.setText(position+"");
					if (localImglist.get(position).getBitmap() != null) {
						item_img.setImageBitmap(localImglist.get(position).getBitmap());
					}
					
				}
			};
			imagelistView.setAdapter(searchImageAdapter);
		}else {
			searchImageAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void initUI() {
		searchImg_et = (EditText) findViewById(R.id.searchImg_et);
		searchImg_btn = (Button) findViewById(R.id.searchImg_btn);
		searchImgResult_imgae = (ImageView) findViewById(R.id.searchImgResult_imgae);
		searchImgResult_tv = (TextView) findViewById(R.id.searchImgResult_tv);
		imagelistView = (ListView) findViewById(R.id.imagelistView);
		searchImg_btn.setOnClickListener(this);
		imagelistView.setOnScrollListener(new PauseOnScrollListener(
				MyApplication.imageLoader, true, true));// 两个分别表示拖动下拉条和滑动过程中暂停加载
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchImg_btn:
			searchImg(searchImg_et.getText().toString(),mPageNum);

			break;

		default:
			break;
		}
		// TODO Auto-generated method stub

	}

	/**
	 * 参数名 类型 必填 参数位置 描述 默认值 word string 是 urlParam 查询词 花朵 pn string 否 urlParam
	 * 请求返回起始页号，范围0-1000 0 rn string 否 urlParam 请求返回结果数，范围1-60 60 ie string 是
	 * urlParam 查询词编码类型，可选utf-8, gbk utf-8
	 * 
	 * @param searchText
	 */

	private void searchImg(String searchText,int pageNum) {
		if (TextUtils.isEmpty(searchText)) {
			showToast("搜索的文本不能为空");
			return;
		}
		isLoadingImg=true;
		mPageNum=pageNum+mPreNum;
		Parameters para = new Parameters();
		para.put("word", searchText);
		para.put("ie", "utf-8");
		para.put("rn", mPreNum+"");//返回的图片数量
		para.put("pn", pageNum+"");//需要从第几张图片开始返回
		// 天气搜索：： http://apis.baidu.com/heweather/weather/free
		ApiStoreSDK.execute(MyUrl.SEARCH_IMAGE, ApiStoreSDK.GET, para,
				new ApiCallBack() {
					@Override
					public void onSuccess(int status, String responseString) {
						Log.e("sdkdemo", "onSuccess");
						Log.e("sdkdemo", responseString);
						SearchImageRespone searchImageRespone = ImageFetcher
								.handleImageResponse(mContext, responseString);
						searchImgResult_tv.setText("返回的图片数是："
								+ searchImageRespone.getReturnNumber()
								+ " 搜索到的图片总数是"
								+ searchImageRespone.getTotalNumber());
						String imageurl = searchImageRespone.getResultArray()
								.get(1).getObjUrl();
						MyApplication.imageLoader.displayImage(imageurl,
								searchImgResult_imgae);
						ArrayList<Image> downloadingImages = searchImageRespone.getResultArray();
						LogUtil.e(TAG, downloadingImages.size()+"downloadingImages");
						imageDownloadThread.queueThumbnail(downloadingImages);
					}



					@Override
					public void onComplete() {
						Log.e("sdkdemo", "onComplete");
					}

					@Override
					public void onError(int status, String responseString,
							Exception e) {
						Log.e("sdkdemo", "onError, status: " + status);
						Log.e("sdkdemo","errMsg: " + (e == null ? "" : e.getMessage()));
						searchImgResult_tv.setText(e.toString());
					}

				});

	}

	public static void actionStart(Context context, Class<?> activityClass) {
		Intent intent = new Intent(context, activityClass);
		context.startActivity(intent);
	}

}
