package com.example.searchimage;

import android.app.Application;
import android.content.Context;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.example.searchimage.imageutils.imp.ImageFetcherTianGouImp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends Application {
	public static Context context;
	public static ImageLoader imageLoader;
	public static ImageFetcherTianGouImp imageFetcherTianGouImp;

	@Override
	public void onCreate() {
		// TODO 您的其他初始化流程
		/**
		 * imageloader的初始化
		 */
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(getApplicationContext()));
		context = getApplicationContext();
		//天狗搜图的初始化
		imageFetcherTianGouImp=new ImageFetcherTianGouImp();
		/*
		 * apiStore的初始化
		 */
		ApiStoreSDK.init(this, "c80011105e9e420b1b5062e5ce435bf5");
		super.onCreate();
	}

	public static Context getcContext() {
		return context;
	}

}
