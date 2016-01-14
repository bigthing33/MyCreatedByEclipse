package com.example.searchimage.utils;

import com.example.searchimage.MyApplication;
import com.example.searchimage.model.GetGalleryclassRespone;
import com.google.gson.Gson;

/**
 * 存储信息管理类
 *
 */
public class SharedPreferencesManager {

	private static SharedPreferencesUtils instanse = SharedPreferencesUtils
			.getInstanse(MyApplication.context);
	/**
	 * 缓存图片类别的数据
	 * @param json
	 */
	public static void saveClassies(String json) {
		instanse.setContents("GetGalleryclassRespone", json);
	}
	/**
	 * 获取图片类别的 的数据
	 * @param json
	 */
	public static GetGalleryclassRespone getClassies() {
		GetGalleryclassRespone cacheDatas = null;
		if (instanse.getContents("GetGalleryclassRespone", null)!=null) {
			 cacheDatas = new Gson().fromJson(instanse.getContents("GetGalleryclassRespone", null),
					 GetGalleryclassRespone.class);
		}
		return cacheDatas;
	}
	
}
