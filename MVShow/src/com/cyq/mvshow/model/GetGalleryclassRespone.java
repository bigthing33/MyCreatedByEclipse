package com.cyq.mvshow.model;

import java.util.ArrayList;

public class GetGalleryclassRespone {
	private boolean status;
	private ArrayList<Galleryclassify> tngou;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public ArrayList<Galleryclassify> getTngou() {
		return tngou;
	}
	public void setTngou(ArrayList<Galleryclassify> tngou) {
		this.tngou = tngou;
	}
	

}
