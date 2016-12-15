package com.cyq.SmartChat.model;

public class Msg {
	public static final int TYPE_RECEVIED=0;
	public static final int TYPE_SEND=1;
	private String content;
	private int type;
	public Msg(String content, int type) {
		super();
		this.content = content;
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	

}
