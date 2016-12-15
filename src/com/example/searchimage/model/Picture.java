package com.example.searchimage.model;

import java.io.Serializable;

public class Picture implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int gallery;
	private int id;
	private String src;
	public int getGallery() {
		return gallery;
	}
	public void setGallery(int gallery) {
		this.gallery = gallery;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	

}
