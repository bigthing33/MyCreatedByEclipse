package com.example.searchimage.listener;


public interface Listener {
	public void start(Object object);
	public void success(Object object);
	public void erro(Object object);
	public void complete(Object object);
	public void cancel(Object object);

}
