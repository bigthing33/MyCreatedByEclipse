package com.example.searchimage.imageutils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.searchimage.MyApplication;
import com.example.searchimage.model.Image;
import com.example.searchimage.model.SearchImageRespone;
import com.example.searchimage.utils.LogUtil;
import com.example.searchimage.utils.MyUrl;

public class ImageFetcherImp implements ImageFetcher {
	private static final String TAG = ImageFetcherImp.class.getName();
	private  SearchImageRespone searchImageRespone;
	private Context mContext;
	private ImageFetcherListener mListener;
	
	public ImageFetcherImp(Context mContext) {
		super();
		this.mContext = mContext;
	}
	public void setListener(ImageFetcherListener imageFetcherListener){
		this.mListener=imageFetcherListener;
	}

	/**
	 * 参数名 类型 必填 参数位置 描述 默认值 word string 是 urlParam 查询词 花朵 pn string 否 urlParam
	 * 请求返回起始页号，范围0-1000 0 rn string 否 urlParam 请求返回结果数，范围1-60 60 ie string 是
	 * urlParam 查询词编码类型，可选utf-8, gbk utf-8
	 * 
	 * @param searchText
	 */

	public void searchImg(final String searchTag,int pageNum,int mPreNum) {
		if (TextUtils.isEmpty(searchTag)) {
			Toast.makeText(mContext, "搜索的文本不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		Parameters para = new Parameters();
		para.put("word", searchTag);
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
						 searchImageRespone = handleImageResponse(responseString,searchTag);
						 mListener.ImageFetcherSuccess(searchImageRespone);
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
						mListener.ImageFetcherErro(responseString);
					}
				});

	}

	@SuppressWarnings("finally")
	public  SearchImageRespone handleImageResponse(
			String response, String searchTag) {
		try {
			JSONObject jsonObjiect = new JSONObject(response);
			JSONObject status = jsonObjiect.getJSONObject("status");
			if (!status.getString("code").equals("0")) {
				// 如果返回码不正确则直接返回，不执行下面的操作
				LogUtil.e(TAG, status.getString("msg"));
				return null;
			}

			JSONObject data = jsonObjiect.getJSONObject("data");
			searchImageRespone = new SearchImageRespone();
			searchImageRespone.setReturnNumber(data.getInt("ReturnNumber"));
			searchImageRespone.setTotalNumber(data.getInt("TotalNumber"));
			JSONArray resultArray = data.getJSONArray("ResultArray");
			ArrayList<Image> images = new ArrayList<Image>();
			
			JSONObject imgaeJsonObject;
			for (int i = 0; i < resultArray.length(); i++) {
				Image image = new Image();
				imgaeJsonObject = resultArray.getJSONObject(i);
				image.setKey(imgaeJsonObject.getString("Key"));
				image.setObjUrl(imgaeJsonObject.getString("ObjUrl"));
				image.setFromUrl(imgaeJsonObject.getString("FromUrl"));
				image.setPictype(imgaeJsonObject.getString("Pictype"));
				image.setDesc(imgaeJsonObject.getString("Desc"));
				image.setSearchTag(searchTag);
				image.setSearchTime(System.currentTimeMillis());
				images.add(image);
			}
			searchImageRespone.setResultArray(images);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return searchImageRespone;
		}
	}

}
