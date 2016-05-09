package com.cyq.mvshow.utils;


import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.model.GetGalleriesRespone;
import com.cyq.mvshow.model.GetGalleryclassRespone;
import com.cyq.mvshow.model.Set;
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
		instanse.setContents("Classies", json);
	}
	/**
	 * 获取图片类别的 的数据
	 * @param json
	 */
	public static GetGalleryclassRespone getClassies() {
		GetGalleryclassRespone cacheDatas = null;
		if (instanse.getContents("Classies", null)!=null) {
			 cacheDatas = new Gson().fromJson(instanse.getContents("Classies", null),
					 GetGalleryclassRespone.class);
		}
		return cacheDatas;
	}
	/**
	 * 缓存最新图片数据
	 * @param json
	 */
	public static void saveNews(String json) {
		instanse.setContents("News", json);
	}
	/**
	 * 获取最新图片数据
	 * @param json
	 */
	public static GetGalleriesRespone getNews() {
		GetGalleriesRespone cacheDatas = null;
		if (instanse.getContents("News", null)!=null) {
			 cacheDatas = new Gson().fromJson(instanse.getContents("News", null),
					 GetGalleriesRespone.class);
		}
		return cacheDatas;
	}
	/**
	 * 缓存设置
	 * @param json
	 */
	public static void saveSet(Set set) {
		String json=new Gson().toJson(set);
		instanse.setContents("Set", json);
	}
	/**
	 * 获取设置
	 * @param json
	 */
	public static Set getSet() {
		Set set = null;
		if (instanse.getContents("Set", null)!=null) {
			set = new Gson().fromJson(instanse.getContents("Set", null),Set.class);
		}else{
			set=new Set(0, "相册间轮播");
			
		}
		return set;
	}
	
}
