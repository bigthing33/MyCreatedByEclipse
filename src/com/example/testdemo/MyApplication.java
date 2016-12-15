package com.example.testdemo;

import android.app.Application;
import android.content.Context;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.example.testdemo.activity.ApiStoreActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends Application {
	public static Context context;
	public static ImageLoader imageLoader;

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
