package com.example.searchimage.imageutils;

import com.example.searchimage.model.GallryDetailsRespone;


public interface GetGallryDetailsListener {
	public void success(GallryDetailsRespone gallryDetailsRespone, boolean isForHead);
	public void erro(String erroString);

}
