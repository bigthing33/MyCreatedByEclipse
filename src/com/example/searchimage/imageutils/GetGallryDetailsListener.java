package com.example.searchimage.imageutils;

import com.example.searchimage.model.GallryDetailsRespone;


public interface GetGallryDetailsListener {
	public void success(GallryDetailsRespone gallryDetailsRespone);
	public void erro(String erroString);

}
