package com.example.testdemo.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class MyUtils {

	public static String getProcessName(Context cxt, int pid) {
		ActivityManager am = (ActivityManager) cxt
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
		if (runningApps == null) {
			return null;
		}
		for (RunningAppProcessInfo procInfo : runningApps) {
			if (procInfo.pid == pid) {
				return procInfo.processName;
			}
		}
		return null;
	}

	public static void close(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得屏幕的属性
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenMetrics(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm;
	}
	/**
	 * 获取屏幕的宽度和高度
	 * 
	 * @param v
	 * @return 返回2个长度的数组，0表示宽度，1表示高度
	 */
	public static int[] getScreenInfo(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;// 宽度
		int height = dm.heightPixels;
		return new int[] { width, height };
	}
	

	public static void executeInThread(Runnable runnable) {
		new Thread(runnable).start();
	}

}
