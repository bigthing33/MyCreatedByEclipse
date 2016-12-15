package com.cyq.mvshow.model;

import java.util.concurrent.CopyOnWriteArrayList;

public class GetGalleriesRespone {
	private boolean status;
	private int total;
	
	private CopyOnWriteArrayList<Gallery> tngou;
	
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
	public CopyOnWriteArrayList<Gallery> getTngou() {
		return tngou;
	}
	public void setTngou(CopyOnWriteArrayList<Gallery> tngou) {
		this.tngou = tngou;
	}
	

}
