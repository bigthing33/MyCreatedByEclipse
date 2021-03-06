package com.cyq.mvshow.utils;

import android.os.Environment;

public class MyConstants {
	public static final String VERSION_NAME = "VERSION_NAME";
	public static final String VERSION_CODE = "VERSION_CODE";
	/**
	 * Unknown network class
	 */
	public static final int NETWORK_CLASS_UNKNOWN = 0;

	/**
	 * wifi net work
	 */
	public static final int NETWORK_WIFI = 1;

	/**
	 * "2G" networks
	 */
	public static final int NETWORK_CLASS_2_G = 2;

	/**
	 * "3G" networks
	 */
	public static final int NETWORK_CLASS_3_G = 3;

	/**
	 * "4G" networks
	 */
	public static final int NETWORK_CLASS_4_G = 4;

    public static final String CHAPTER_2_PATH = Environment
            .getExternalStorageDirectory().getPath()
            + "/singwhatiwanna/chapter_2/";

    public static final String CACHE_FILE_PATH = CHAPTER_2_PATH + "usercache";

    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVICE = 1;
    
    public static final String REMOTE_ACTION = "com.ryg.chapter.action_REMOTE";
    public static final String EXTRA_REMOTE_VIEWS = "extra_remoteViews";
    public static final String ExternalStorageDirectory_SEARCHIMAGE = Environment
			.getExternalStorageDirectory().getPath() + "/searchImage";
    //每次加载的图片个数
	public static final int PAGE_SIZE = 12;
	//最新图片的标签
	public static final String NEWS_IMAGE = "NEWS_IMAGE";
	public static final String CLASSIES_IMAGE = "CLASSIES_IMAGE";
	public static final String RANDOME_IMAGE = "RANDOME_IMAGE";
	public static final String COLLECT_IMAGE = "COLLECT_IMAGE";





}
