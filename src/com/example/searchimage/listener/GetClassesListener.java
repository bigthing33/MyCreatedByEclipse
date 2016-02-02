package com.example.searchimage.listener;

import com.example.searchimage.model.GetGalleryclassRespone;

public interface GetClassesListener {
	public void success(GetGalleryclassRespone getGalleryclassRespone);
	public void erro(String erroString);

}
