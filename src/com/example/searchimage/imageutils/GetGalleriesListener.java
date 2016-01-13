package com.example.searchimage.imageutils;

import com.example.searchimage.model.GetGalleryListRespone;

public interface GetGalleriesListener {
	public void success(GetGalleryListRespone getGalleryListRespone);
	public void erro(String erroString);

}
