package com.cyq.mvshow.imageutils;

import android.graphics.Bitmap;

public interface ImageCache {
	public String put(Bitmap  bitmap,String url);
	public Bitmap get(String url);

}
