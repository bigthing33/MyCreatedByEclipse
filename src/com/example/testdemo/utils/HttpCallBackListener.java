package com.example.testdemo.utils;

public interface HttpCallBackListener {
	void onFinish(String response);
	void onErro(Exception e);

}
