package com.example.searchimage.listener;

import com.example.searchimage.model.GetGalleriesRespone;

public interface GetGalleriesListener {
	public void success(GetGalleriesRespone getGalleryListRespone,boolean isForHead);
	public void erro(String erroString);

}
