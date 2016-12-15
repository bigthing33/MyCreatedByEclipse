package com.example.searchimage.imageutils.imp;

import com.example.searchimage.MyApplication;
import com.example.searchimage.db.ImageDB;
import com.example.searchimage.imageutils.ImageCache;
import com.example.searchimage.utils.MyUtils;

import android.graphics.Bitmap;

public class ImageCacheByDisk implements ImageCache {
	private ImageDB imageDB=ImageDB.getInstance(MyApplication.context);

	/**
	 * 缓存图片到SD卡，并且返回保存的路径，是整个路径
	 */
	@Override
	public String put(Bitmap bitmap, String url) {
		return MyUtils.saveBitmapInExternalStorage(bitmap,MyApplication.context,url);
	}

	@Override
	public Bitmap get(String url) {
		
		String path=imageDB.loadImageByUrl(url);
		return MyUtils.convertToBitmap(path, 500, 500);
	}

}
