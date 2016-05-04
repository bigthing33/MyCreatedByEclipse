package com.cyq.mvshow.model;

import java.util.ArrayList;

public class GallryDetailsRespone {
	private boolean status;
	private int count;
	private int fcount;
	private int galleryclass;
	private int id;
	private long time;
	private String img;
	private String rcount;
	private String size;
	private String title;
	private String url;
	private ArrayList<Picture> list;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getFcount() {
		return fcount;
	}
	public void setFcount(int fcount) {
		this.fcount = fcount;
	}
	public int getGalleryclass() {
		return galleryclass;
	}
	public void setGalleryclass(int galleryclass) {
		this.galleryclass = galleryclass;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getRcount() {
		return rcount;
	}
	public void setRcount(String rcount) {
		this.rcount = rcount;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ArrayList<Picture> getList() {
		return list;
	}
	public void setList(ArrayList<Picture> list) {
		this.list = list;
	}
	

}
