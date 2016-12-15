package com.coolweather.app.service;

import com.coolweather.app.util.HttpCallBackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class AutoUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				updateWeather();
				
			}


		}).start(); 
		AlarmManager manager=(AlarmManager) getSystemService(ALARM_SERVICE);
		int abHour=8*60*60*1000;
		long TriggerAtTime=SystemClock.elapsedRealtime()+abHour;
		Intent i=new Intent(this, AutoUpdateService.class);
		PendingIntent pi=PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, TriggerAtTime, pi);
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	private void updateWeather() {
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
		String weatherCode=prefs.getString("weather_code", "");
		String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
			
			@Override
			public void onFinish(String response) {
				Utility.handleWeatherResponse(AutoUpdateService.this, response);
				
				
			}
			
			@Override
			public void onErro(Exception e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	

}
