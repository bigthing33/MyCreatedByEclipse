package com.example.testdemo.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;

import android.R.integer;

public class HttpUtil {
	static int mfinished;
	protected static final String TAG = HttpUtil.class.getSimpleName();

	public static void sendHttpRequest(final String address,final HttpCallBackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection=null;
				try {
					URL url=new URL(address);
					connection=(HttpURLConnection) url.openConnection();
					connection.setRequestProperty("Accept-Encoding", "identity"); // 添加这行代码
					connection.setRequestMethod("GET");
					connection.setReadTimeout(8000);
					int length = -1;//下载内容的长度
					if (connection.getResponseCode() == HttpStatus.SC_OK) {
						length = connection.getContentLength();
						LogUtil.e(TAG, "length"+length);
					}
					if (length <= 0) {
						return;
					}
					InputStream in = connection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuilder response=new StringBuilder();
					String line;
					while((line=reader.readLine())!=null){
						response.append(line);
						mfinished=mfinished+line.getBytes().length;
						listener.onProgress(mfinished);
					}
					if(listener!=null){
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
					if(listener!=null){
						listener.onErro(e);
					}
					e.printStackTrace();
				}finally{
					if(connection!=null){
						connection.disconnect();
					}
				}
				
			}
		}).start();
	}
}
