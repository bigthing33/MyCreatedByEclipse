package com.example.searchimage.model;

import java.util.ArrayList;

public class GetGalleryListRespone {
	private boolean status;
	private int total;
	
	private ArrayList<Gallery> tngou;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public ArrayList<Gallery> getTngou() {
		return tngou;
	}
	public void setTngou(ArrayList<Gallery> tngou) {
		this.tngou = tngou;
	}
	

}
