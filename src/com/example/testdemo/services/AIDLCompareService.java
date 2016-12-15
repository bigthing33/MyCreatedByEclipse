package com.example.testdemo.services;

import com.example.testdemo.aidl.ICompare;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class AIDLCompareService extends Service {
	private Binder myBinder=new ICompare.Stub() {
		
		@Override
		public int compare(int x, int y) throws RemoteException {
			return Math.max(x, y)+1;
		}

		@Override
		public int compare2(String string, String string2) throws RemoteException {
			return Math.max(Integer.valueOf(string),Integer.valueOf(string2))+1;
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
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


}
