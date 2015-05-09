package com.cyq.SmartChat.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.net.Uri;
import android.util.Log;

public class HttpUtil {
	public static void sendHttpRequest(final String request,final HttpCallBackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection=null;
				try {
					Log.i("chat", request);
					URL url=new URL(request);
					/*String urlspc=Uri.parse("http://www.tuling123.com/openapi/api").buildUpon()
							.appendQueryParameter("key", "1794a5cd37dd2190565ff0e806287269")
							.appendQueryParameter("info", "hello").build().toString();
					Log.i("chat", urlspc);
					URL url=new URL(urlspc);*/
					connection=(HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setReadTimeout(8000);
					connection.setReadTimeout(8000);
					connection.connect();
					InputStream in = connection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuilder response=new StringBuilder();
					String line;
					while((line=reader.readLine())!=null){
						response.append(line);
					}
					if(listener!=null){
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
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
