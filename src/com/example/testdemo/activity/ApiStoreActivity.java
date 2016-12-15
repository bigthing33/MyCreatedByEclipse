package com.example.testdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.testdemo.R;
import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.utils.MyUrl;

public class ApiStoreActivity extends BaseActivity implements OnClickListener {
	protected static final String TAG = ApiStoreActivity.class.getSimpleName();
	private Context mContext = ApiStoreActivity.this;
	private EditText searchImg_et;
	private Button searchImg_btn;
	private Button searchImgTiangouClassfy_btn;
	private Button searchImgTiangouList_btn;
	private Button searchImgTiangouAtlasDetails_btn;
	private Button searchImgTiangouAtlasNews_btn;
	private TextView searchImgResult_tv;
	private int mPageNum = 0;
	private int mPreNum = 5;// 默认为5；缓存图片个数，范围是1-60

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apistore);
		initUI();
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
		searchImgTiangouClassfy_btn = (Button) findViewById(R.id.searchImgTiangouClassfy_btn);
		searchImgResult_tv = (TextView) findViewById(R.id.searchImgResult_tv);
		searchImgTiangouList_btn = (Button) findViewById(R.id.searchImgTiangouList_btn);
		searchImgTiangouAtlasDetails_btn = (Button) findViewById(R.id.searchImgTiangouAtlasDetails_btn);
		searchImgTiangouAtlasNews_btn = (Button) findViewById(R.id.searchImgTiangouAtlasNews_btn);
		searchImg_btn.setOnClickListener(this);
		searchImgTiangouClassfy_btn.setOnClickListener(this);
		searchImgTiangouList_btn.setOnClickListener(this);
		searchImgTiangouAtlasDetails_btn.setOnClickListener(this);
		searchImgTiangouAtlasNews_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchImg_btn:
			searchImgByBaidu(searchImg_et.getText().toString(), mPageNum);

			break;
		case R.id.searchImgTiangouClassfy_btn:
			searchClassifyTiangou();

			break;
		case R.id.searchImgTiangouList_btn:
			searchListTiangou(0, 1, 20);

			break;
		case R.id.searchImgTiangouAtlasDetails_btn:
			searchAtlasDetailsTiangou(10);
			
			break;
		case R.id.searchImgTiangouAtlasNews_btn:
			searchAtlasNewsTiangou(0,1,1);
			
			break;

		default:
			break;
		}

	}

	/**
	 * 搜素天狗最新图库
	 * @param id 最新图库id
	 * @param rows 条数
	 * @param classify 图库分类id
	 */
	private void searchAtlasNewsTiangou(int id, int rows, int classify) {
		Parameters para = new Parameters();
		para.put("id", id + "");//long	当前最新的图库关键词id
//		para.put("classify", classify + "");// 图库分类id
		para.put("rows", rows + "");// 返回的图片数量
		requestGetImgs(para, MyUrl.SEARCH_TIANGOU_AtlasNews);
		
	}

	/**
	 * 搜索天狗图库详情
	 * @param id 图库id
	 */
	private void searchAtlasDetailsTiangou(int id) {
		Parameters para = new Parameters();
		para.put("id", id + "");
		requestGetImgs(para, MyUrl.SEARCH_TIANGOU_AtlasDetails);
		
	}

	/**
	 * 搜索天狗图片列表
	 * 
	 * @param id
	 *            图片分类id
	 * @param page
	 *            页数
	 * @param rows
	 *            条数
	 */
	private void searchListTiangou(int id, int page, int rows) {

		Parameters para = new Parameters();
		para.put("id", id + "");
		para.put("page", page + "");// 返回的页数
		para.put("rows", rows + "");// 返回的图片数量
		requestGetImgs(para, MyUrl.SEARCH_TIANGOU_LIST);
	}

	/**
	 * 天狗搜图--搜类别
	 */
	private void searchClassifyTiangou() {
		Parameters para = new Parameters();
		requestGetImgs(para, MyUrl.SEARCH_TIANGOU_CLASSIFY);
	}

	/**
	 * 参数名 类型 必填 参数位置 描述 默认值 word string 是 urlParam 查询词 花朵 pn string 否 urlParam
	 * 请求返回起始页号，范围0-1000 0 rn string 否 urlParam 请求返回结果数，范围1-60 60 ie string 是
	 * urlParam 查询词编码类型，可选utf-8, gbk utf-8
	 * 
	 * @param searchText
	 */

	private void searchImgByBaidu(String searchText, int pageNum) {
		if (TextUtils.isEmpty(searchText)) {
			showToast("搜索的文本不能为空");
			return;
		}
		mPageNum = pageNum + mPreNum;
		Parameters para = new Parameters();
		para.put("word", searchText);
		para.put("ie", "utf-8");
		para.put("rn", mPreNum + "");// 返回的图片数量
		para.put("pn", pageNum + "");// 需要从第几张图片开始返回
		// 天气搜索：： http://apis.baidu.com/heweather/weather/free
		requestGetImgs(para, MyUrl.SEARCH_IMAGE);

	}

	private void requestGetImgs(Parameters para, String url) {
		ApiStoreSDK.execute(url, ApiStoreSDK.GET, para, new ApiCallBack() {
			@Override
			public void onSuccess(int status, String responseString) {
				Log.e("sdkdemo", "onSuccess");
				Log.e("sdkdemo", responseString);
				searchImgResult_tv.setText(responseString);
			}

			@Override
			public void onComplete() {
				Log.e("sdkdemo", "onComplete");
			}

			@Override
			public void onError(int status, String responseString, Exception e) {
				Log.e("sdkdemo", "onError, status: " + status);
				Log.e("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));
				searchImgResult_tv.setText(e.toString());
			}

		});
	}

	public static void actionStart(Context context, Class<?> activityClass) {
		Intent intent = new Intent(context, activityClass);
		context.startActivity(intent);
	}

}
