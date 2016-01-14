package com.example.searchimage.imageutils;

import com.example.searchimage.model.GetGalleriesRespone;

public interface GetGalleriesListener {
	public void success(GetGalleriesRespone getGalleryListRespone);
	public void erro(String erroString);

}
