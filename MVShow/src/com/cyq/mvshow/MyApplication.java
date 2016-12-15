package com.cyq.mvshow;

import java.io.File;

import android.app.Application;
import android.content.Context;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.cyq.mvshow.imageutils.ImageFetcherTianGouImp;
import com.cyq.mvshow.model.Set;
import com.cyq.mvshow.utils.MyImageLoader;
import com.cyq.mvshow.utils.SharedPreferencesManager;

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
	public static Set set;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		// TODO 您的其他初始化流程
		context = getApplicationContext();
		/*
		 * 图片下载器的初始化
		 */
		imageLoader = initImageLoader();
		// 天狗搜图的初始化
		imageFetcherTianGouImp = new ImageFetcherTianGouImp();
		/*
		 * apiStore的初始化
		 */
		ApiStoreSDK.init(this, "c80011105e9e420b1b5062e5ce435bf5");
		/*
		 * 设置的初始化
		 */
		set=SharedPreferencesManager.getSet();
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
				.memoryCache(new UsingFreqLimitedMemoryCache(20 * 1024 * 1024))//你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.diskCacheFileCount(500)
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
				.showImageOnLoading(R.drawable.bg_white)
				// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.no_network)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.no_network)
				// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.build(); // 创建配置过得DisplayImageOption对象
		return options;
	}

	public static Context getcContext() {
		return context;
	}

}
