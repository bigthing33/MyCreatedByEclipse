package com.example.searchimage.imageutils;

import com.example.searchimage.MyApplication;
import com.example.searchimage.utils.MyUtils;

import android.graphics.Bitmap;

public class ImageCacheByDisk implements ImageCache {

	/**
	 * 缓存图片到SD卡，并且返回保存的路径，是整个路径
	 */
	@Override
	public String put(Bitmap bitmap, String url) {
		return MyUtils.saveBitmapInExternalStorage(bitmap,MyApplication.context,url);
	}

	@Override
	public Bitmap get(String path) {
		return MyUtils.convertToBitmap(path, 500, 500);
	}

}
