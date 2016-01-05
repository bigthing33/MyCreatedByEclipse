package com.example.testdemo.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.example.testdemo.R;

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
	 * 获取版本名称
	 */
	public static String getAppVersionName(Context context) {
		String versionName = null;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	/**
	 * 获得屏幕的属性
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenMetrics(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
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

	/**
	 * 给ListView添加动画
	 */
	public static void addAnimationToListView(Context context, ListView listView) {
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.anim_item);
		LayoutAnimationController controller = new LayoutAnimationController(
				animation);
		controller.setDelay(0.5f);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		listView.setLayoutAnimation(controller);

	}
	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public static boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) new Application()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	/**
	 * 获取清单文件中的metaData中的值
	 * @param ctx Context
	 * @param key metaData中的name
	 * @return
	 */
	 public static String getAppMetaData(Context ctx, String key) {  
	        if (ctx == null || TextUtils.isEmpty(key)) {  
	            return "erro for metaData";  
	        }  
	        int intData = -1;  
	        String string = null;
	        try {  
	            PackageManager packageManager = ctx.getPackageManager();  
	            if (packageManager != null) {  
	                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);  
	                if (applicationInfo != null) {  
	                    if (applicationInfo.metaData != null) {  
	                        intData = applicationInfo.metaData.getInt(key); 
	                        if (intData==0) {
	                        	 return string = applicationInfo.metaData.getString(key)+"";
							}else{
								return string = String.valueOf(intData);
								
							}
	                    }  
	                }  
	  
	  
	            }  
	        } catch (PackageManager.NameNotFoundException e) {  
	            e.printStackTrace();  
	        }
			return string;  
	  
	  
	    }

}
