package com.example.searchimage.imageutils;

import android.graphics.Bitmap;

public interface ImageCache {
	public String put(Bitmap  bitmap,String url);
	public Bitmap get(String url);

}
