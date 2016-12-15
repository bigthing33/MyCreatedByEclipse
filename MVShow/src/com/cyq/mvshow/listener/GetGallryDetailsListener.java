package com.cyq.mvshow.listener;

import com.cyq.mvshow.model.GallryDetailsRespone;

public interface GetGallryDetailsListener {
	public void success(GallryDetailsRespone gallryDetailsRespone, boolean isForHead);
	public void erro(String erroString);

}
