package com.cyq.SmartChat.Utils;

public interface HttpCallBackListener {
	void onFinish(String response);
	void onErro(Exception e);

}
