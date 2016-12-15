package com.example.searchimage.utils;

public class MyUrl extends MyConstants {
	/*
	 * 百度apistore的服务头
	 */
	public static final String BAIDU_SERVICE = "http://apis.baidu.com/";
	/*
	 * 天狗开放阅图的服务头
	 */
	public static final String TIANGOU_SERVICE = "http://tnfs.tngou.net/image";
//	public static final String TIANGOU_SERVICE = "http://tnfs.tngou.net/img";
	/*
	 * 百度文本搜图
	 */
	
	public static final String SEARCH_IMAGE = BAIDU_SERVICE+"image_search/search/search";
	/*
	 * 天狗开放阅图的图片分类
	 */
	public static final String SEARCH_TIANGOU_CLASSIFY = BAIDU_SERVICE+"tngou/gallery/classify";
	/*
	 * 天狗开放阅图的图片类别下的列表
	 */
	public static final String SEARCH_TIANGOU_LIST = BAIDU_SERVICE+"tngou/gallery/list";
	/*
	 * 天狗开放阅图的图库详情
	 */
	public static final String SEARCH_TIANGOU_AtlasDetails = BAIDU_SERVICE+"tngou/gallery/show";
	/*
	 * 天狗开放阅图的最新图库
	 */
	public static final String SEARCH_TIANGOU_AtlasNews = BAIDU_SERVICE+"tngou/gallery/news";

}
