package com.cyq.mvshow.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cyq.mvshow.model.Image;
import com.cyq.mvshow.model.SearchImageRespone;

import android.content.Context;



public class ImageFetcher {
	private static final String TAG = ImageFetcher.class.getName();
	private static SearchImageRespone searchImageRespone;

	public static SearchImageRespone handleImageResponse(Context context,
			String response) {
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
