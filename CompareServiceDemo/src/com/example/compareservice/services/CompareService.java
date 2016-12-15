package com.example.compareservice.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class CompareService extends Service {
	private MyBinder myBinder;

	@Override
	public void onCreate() {
		super.onCreate();
		myBinder=new MyBinder();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return myBinder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	public class MyBinder extends Binder{
		public int compare(int integer1,int integer2){
			return Math.max(integer1,integer2);
		}

		public int compare(String string, String string2) {
			return Math.max(Integer.valueOf(string),Integer.valueOf(string2));
			
			
		}
		
	}

}
