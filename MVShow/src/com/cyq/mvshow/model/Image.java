package com.cyq.mvshow.model;

import android.graphics.Bitmap;

public class Image {
	private String Key;
	private String ObjUrl;
	private String FromUrl;
	private String Desc;
	private String Pictype;
	private Bitmap bitmap;
	private long searchTime;//搜索的时间
	private String searchTag;//搜索的标签
	private String savePath;//保存路径



	@Override
	public String toString() {
		return "Image [Key=" + Key + ", ObjUrl=" + ObjUrl + ", FromUrl="
				+ FromUrl + ", Desc=" + Desc + ", Pictype=" + Pictype
				+ ", bitmap=" + bitmap + ", searchTime=" + searchTime
				+ ", searchTag=" + searchTag + ", savePath=" + savePath + "]";
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



	public long getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(long searchTime) {
		this.searchTime = searchTime;
	}

	public String getSearchTag() {
		return searchTag;
	}

	public void setSearchTag(String searchTag) {
		this.searchTag = searchTag;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	

}
