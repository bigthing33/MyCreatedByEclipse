package com.cyq.mvshow.model;

public class Set {
	//轮播时间，单位秒
	private int playTime;
	//轮播类型
	private String playType;
	
	public Set(int playTime, String playType) {
		super();
		this.playTime = playTime;
		this.playType = playType;
	}
	public int getPlayTime() {
		return playTime;
	}
	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	
	
	
	

}
