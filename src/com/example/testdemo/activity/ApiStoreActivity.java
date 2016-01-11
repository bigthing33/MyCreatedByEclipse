package com.example.testdemo.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.testdemo.MyApplication;
import com.example.testdemo.R;
import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.base.CommonAdapter;
import com.example.testdemo.model.Image;
import com.example.testdemo.model.SearchImageRespone;
import com.example.testdemo.services.ImageDownloadThread;
import com.example.testdemo.services.ImageDownloadThread.Listener;
import com.example.testdemo.utils.ImageFetcher;
import com.example.testdemo.utils.LogUtil;
import com.example.testdemo.utils.MyUrl;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

public class ApiStoreActivity extends BaseActivity implements OnClickListener {
	protected static final String TAG = ApiStoreActivity.class.getSimpleName();
	private Context mContext = ApiStoreActivity.this;
	private EditText searchImg_et;
	private Button searchImg_btn;
	private TextView searchImgResult_tv;
	private ImageView searchImgResult_imgae;
	private ListView imagelistView;
	private CommonAdapter<Image> searchImageAdapter;
	private ArrayList<Image> localImglist = new ArrayList<>();
	private ImageDownloadThread<ImageView> imageDownloadThread;
	private int mPageNum=0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apistore);
		initUI();
		imageDownloadThread = new ImageDownloadThread<>(new Handler());
		imageDownloadThread.setListener(new Listener() {

			@Override
			public void imageDownloaded(ArrayList<Image> imageList) {
				LogUtil.e(TAG, "imageDownloaded");
				setAdapter(imageList);
			}
		});
		imageDownloadThread.start();
		// getLooper放在start之后，保证线程就绪
		imageDownloadThread.getLooper();
	}

	private void setAdapter(ArrayList<Image> imageList) {
		for (Image image : imageList) {
			localImglist.add(image);
		}
		if (localImglist.size() == 0) {
			imagelistView.setAdapter(null);
		} else if (localImglist.size()==10) {
			searchImageAdapter=new CommonAdapter<Image>(mContext, localImglist, R.layout.item_image) {

				@Override
				public void convert(
						com.example.testdemo.base.ViewHolder holder, Image t,
						int position) {
					if (position>=localImglist.size()-5&&position/10==mPageNum/10-1) {
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
		mPageNum=pageNum+10;
		Parameters para = new Parameters();
		para.put("word", searchText);
		para.put("ie", "utf-8");
		para.put("rn", "10");
		para.put("pn", pageNum+"");
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
