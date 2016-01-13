package com.example.searchimage;

import android.app.Application;
import android.content.Context;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.example.searchimage.imageutils.imp.ImageFetcherTianGouImp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class MyApplication extends Application {
	public static Context context;
	public static ImageLoader imageLoader;
	public static ImageFetcherTianGouImp imageFetcherTianGouImp;

	@Override
	public void onCreate() {
		// TODO 您的其他初始化流程
		context = getApplicationContext();
		/**
		 * imageloader的初始化
		 */
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		// .showImageOnLoading(R.drawable.ic_empty)
		// 设置图片在下载期间显示的图片
		// .showImageForEmptyUri(R.drawable.ic_empty)
		// 设置图片Uri为空或是错误的时候显示的图片
		// .showImageOnFail(R.drawable.ic_empty)
		// 设置图片加载/解码过程中错误时候显示的图片
		.cacheInMemory(true)
		// 设置下载的图片是否缓存在内存中
		.cacheOnDisk(true)
		// 设置下载的图片是否缓存在SD卡中
		.displayer(new FadeInBitmapDisplayer(100))
		.considerExifParams(true)
		.displayer(new FadeInBitmapDisplayer(100))// 图片加载好后渐入的动画时间
		.displayer(new RoundedBitmapDisplayer(1)).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		        this)
		.defaultDisplayImageOptions(options)
		        .threadPoolSize(3)
		        .threadPriority(Thread.NORM_PRIORITY - 2)
		        .memoryCacheSize(4 * 1024 * 1024)
		        .imageDownloader(
		                new BaseImageDownloader(this, 10 * 1000, 30 * 1000))
		        .writeDebugLogs().build();
		 
		imageLoader = ImageLoader.getInstance();
//		imageLoader.init(config);
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(getApplicationContext()));
//		天狗搜图的初始化
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
