package com.example.searchimage.utils;

import android.widget.ImageView;

import com.example.searchimage.MyApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class MyImageLoader {
	public static void displayImage(String uri,ImageView imageView){
        MyApplication.imageLoader.displayImage(uri, imageView);
//		imageView.setImageResource(R.drawable.sample);
	}
	public final static DisplayImageOptions getLargeDisplayOptions() {
//		if(largeDisplayOptions != null) return largeDisplayOptions;
		return new DisplayImageOptions.Builder()
//				.showImageForEmptyUri(R.drawable.large_default) // 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.drawable.large_default) // 设置图片加载或解码过程中发生错误显示的图片
//		        .showImageOnLoading(R.drawable.large_default) // 创建配置过得DisplayImageOption对象
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true).build(); // 设置下载的图片是否缓存在SD卡中
//		return largeDisplayOptions;
	}

}
