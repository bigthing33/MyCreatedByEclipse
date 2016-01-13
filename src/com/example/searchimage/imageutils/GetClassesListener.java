package com.example.searchimage.imageutils;

import com.example.searchimage.model.GetGalleryclassRespone;

public interface GetClassesListener {
	public void success(GetGalleryclassRespone getGalleryclassRespone);
	public void erro(String erroString);

}
