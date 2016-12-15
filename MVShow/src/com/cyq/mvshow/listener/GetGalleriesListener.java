package com.cyq.mvshow.listener;

import com.cyq.mvshow.model.GetGalleriesRespone;



public interface GetGalleriesListener {
	public void success(GetGalleriesRespone getGalleryListRespone,boolean isForHead);
	public void erro(String erroString);

}
