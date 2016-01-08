package com.example.testdemo.activity;

import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.testdemo.MyApplication;
import com.example.testdemo.R;
import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.model.SearchImageRespone;
import com.example.testdemo.utils.ImageFetcher;
import com.example.testdemo.utils.MyUrl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ApiStoreActivity extends BaseActivity implements OnClickListener {
	private Context mContext=ApiStoreActivity.this;
	private EditText searchImg_et;
	private Button searchImg_btn;
	private TextView searchImgResult_tv;
	private ImageView searchImgResult_imgae;
	

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
		searchImg_et=(EditText) findViewById(R.id.searchImg_et);
		searchImg_btn=(Button) findViewById(R.id.searchImg_btn);
		searchImgResult_imgae=(ImageView) findViewById(R.id.searchImgResult_imgae);
		searchImgResult_tv=(TextView) findViewById(R.id.searchImgResult_tv);
		searchImg_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchImg_btn:
			searchImg(searchImg_et.getText().toString());
			
			break;

		default:
			break;
		}
		// TODO Auto-generated method stub
		
	}

	/**
	 * 参数名 类型 必填 参数位置 描述 默认值
	 *  word string 是 urlParam 查询词 花朵 
	 *  pn string 否 urlParam 请求返回起始页号，范围0-2000 0 
	 *  rn string 否 urlParam 请求返回结果数，范围1-60 60 
	 *  ie string 是 urlParam 查询词编码类型，可选utf-8, gbk utf-8
	 * 
	 * @param searchText
	 */

	private void searchImg(String searchText) {
		if (TextUtils.isEmpty(searchText) ) {
			showToast("搜索的文本不能为空");
			return;
		}
		Parameters para = new Parameters();
		para.put("word", searchText);
		para.put("ie", "utf-8");
		//天气搜索：：    http://apis.baidu.com/heweather/weather/free
		ApiStoreSDK.execute(MyUrl.SEARCH_IMAGE, 
				ApiStoreSDK.GET,
				para, 
				new ApiCallBack() {
			        @Override
			        public void onSuccess(int status, String responseString) {
			            Log.e("sdkdemo", "onSuccess");
			            Log.e("sdkdemo", responseString);
			            SearchImageRespone searchImageRespone = ImageFetcher.handleImageResponse(mContext, responseString);
			            searchImgResult_tv.setText("返回的图片数是："+searchImageRespone.getReturnNumber()+" 搜索到的图片总数是"+searchImageRespone.getTotalNumber());
			            String imageurl=searchImageRespone.getResultArray().get(1).getObjUrl();
			            MyApplication.imageLoader.displayImage(imageurl,searchImgResult_imgae);
			        }
			    

					@Override
			        public void onComplete() {
			             Log.e("sdkdemo", "onComplete");
			        }
			    
			        @Override
			        public void onError(int status, String responseString, Exception e) {
			            Log.e("sdkdemo", "onError, status: " + status);
			            Log.e("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));
			            searchImgResult_tv.setText(responseString+":::::::::::::::::"+e.toString());
			        }  

			});

	}
	public static void actionStart(Context context,Class<?> activityClass) {
		Intent intent = new Intent(context, activityClass);
		context.startActivity(intent);
	}

}

