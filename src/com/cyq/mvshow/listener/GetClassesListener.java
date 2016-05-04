package com.cyq.mvshow.listener;

import com.cyq.mvshow.model.GetGalleryclassRespone;



public interface GetClassesListener {
	public void success(GetGalleryclassRespone getGalleryclassRespone);
	public void erro(String erroString);

}
