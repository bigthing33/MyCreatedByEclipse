package com.example.searchimage;

import java.io.File;

import android.app.Application;
import android.content.Context;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.example.searchimage.imageutils.imp.ImageFetcherTianGouImp;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class MyApplication extends Application {
	public static Context context;
	public static ImageLoader imageLoader;
	public static ImageFetcherTianGouImp imageFetcherTianGouImp;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		// TODO 您的其他初始化流程
		context = getApplicationContext();
		/**
		 * 无用的imageloader的初始化
		 */
		// DisplayImageOptions options = new DisplayImageOptions.Builder()
		// // .showImageOnLoading(R.drawable.ic_empty)
		// // 设置图片在下载期间显示的图片
		// // .showImageForEmptyUri(R.drawable.ic_empty)
		// // 设置图片Uri为空或是错误的时候显示的图片
		// // .showImageOnFail(R.drawable.ic_empty)
		// // 设置图片加载/解码过程中错误时候显示的图片
		// .cacheInMemory(true)
		// // 设置下载的图片是否缓存在内存中
		// .cacheOnDisk(true)
		// // 设置下载的图片是否缓存在SD卡中
		// .displayer(new FadeInBitmapDisplayer(100))
		// .considerExifParams(true)
		// .displayer(new FadeInBitmapDisplayer(100))// 图片加载好后渐入的动画时间
		// .displayer(new RoundedBitmapDisplayer(1)).build();
		// ImageLoaderConfiguration config = new
		// ImageLoaderConfiguration.Builder(
		// this)
		// .defaultDisplayImageOptions(options)
		// .threadPoolSize(3)
		// .threadPriority(Thread.NORM_PRIORITY - 2)
		// .memoryCacheSize(4 * 1024 * 1024)
		// .imageDownloader(
		// new BaseImageDownloader(this, 10 * 1000, 30 * 1000))
		// .writeDebugLogs().build();
		// // imageLoader.init(config);
		/**
		 * 默认的imageLoader初始化
		 */
		// imageLoader.init(ImageLoaderConfiguration
		// .createDefault(getApplicationContext()));
		// imageLoader = ImageLoader.getInstance();
		/**
		 * 自定义的imageLoader初始化
		 */
		// File cacheDir =StorageUtils.getOwnCacheDirectory(this,
		// "imageloader/Cache");
		// ImageLoaderConfiguration config = new ImageLoaderConfiguration
		// .Builder(this)
		//
		// .memoryCacheExtraOptions(480, 800) // maxwidth, max
		// height，即保存的每个缓存文件的最大长宽
		// .threadPoolSize(3)//线程池内加载的数量
		// .threadPriority(Thread.NORM_PRIORITY -2)
		// .denyCacheImageMultipleSizesInMemory()
		// .memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) // You
		// can pass your own memory cache implementation/你可以通过自己的内存缓存实现
		// .memoryCacheSize(2 * 1024 * 1024)
		// .discCacheSize(50 * 1024 * 1024)
		// .discCacheFileNameGenerator(new
		// Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
		// .tasksProcessingOrder(QueueProcessingType.LIFO)
		// .diskCacheFileCount(100)
		// .discCache(new UnlimitedDiskCache(cacheDir))
		// .defaultDisplayImageOptions(getDefaultDisplayOption())
		// .imageDownloader(new BaseImageDownloader(this,5 * 1000, 30 * 1000))
		// // connectTimeout (5 s), readTimeout (30 s)超时时间
		// .writeDebugLogs() // Remove for releaseapp
		// .build();//开始构建

		imageLoader = initImageLoader();
		// 天狗搜图的初始化
		imageFetcherTianGouImp = new ImageFetcherTianGouImp();
		/*
		 * apiStore的初始化
		 */
		ApiStoreSDK.init(this, "c80011105e9e420b1b5062e5ce435bf5");
		super.onCreate();
	}

	private final static ImageLoader initImageLoader() {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"imageloader/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(480, 800)
				// maxwidth, max height，即保存的每个缓存文件的最大长宽
				.defaultDisplayImageOptions(getDefaultDisplayOption())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(20 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).diskCacheFileCount(500)
				.imageDownloader(new BaseImageDownloader(context))
				.writeDebugLogs()
				// Remove for releaseapp
				.discCache(new UnlimitedDiskCache(cacheDir))
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
		return imageLoader;
	}

	private final static DisplayImageOptions getDefaultDisplayOption() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		  .showImageOnLoading(R.drawable.loading)
//		  设置图片在下载期间显示的图片
//		  .showImageForEmptyUri(R.drawable.ic_empty)
//		  设置图片Uri为空或是错误的时候显示的图片
//		  .showImageOnFail(R.drawable.ic_empty)
//		  设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				// .showImageOnLoading(R.drawable.empty_photo)
				.displayer(new FadeInBitmapDisplayer(500))// 图片加载好后渐入的动画时间
				.build(); // 创建配置过得DisplayImageOption对象
		return options;
	}

	public static Context getcContext() {
		return context;
	}

}
