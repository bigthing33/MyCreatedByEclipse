package com.example.searchimage.model;

import android.graphics.Bitmap;

public class Image {
	private String Key;
	private String ObjUrl;
	private String FromUrl;
	private String Desc;
	private String Pictype;
	private Bitmap bitmap;

	@Override
	public String toString() {
		return "Image [Key=" + Key + ", ObjUrl=" + ObjUrl + ", FromUrl="
				+ FromUrl + ", Desc=" + Desc + ", Pictype=" + Pictype + "]";
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getObjUrl() {
		return ObjUrl;
	}

	public void setObjUrl(String objUrl) {
		ObjUrl = objUrl;
	}

	public String getFromUrl() {
		return FromUrl;
	}

	public void setFromUrl(String fromUrl) {
		FromUrl = fromUrl;
	}

	public String getDesc() {
		return Desc;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public String getPictype() {
		return Pictype;
	}

	public void setPictype(String pictype) {
		Pictype = pictype;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
