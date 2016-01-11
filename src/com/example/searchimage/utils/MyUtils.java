package com.example.searchimage.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;
import android.widget.Toast;

import com.example.searchimage.R;

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
	 * 
	 * @param ctx
	 *            Context
	 * @param key
	 *            metaData中的name
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
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(ctx.getPackageName(),
								PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						intData = applicationInfo.metaData.getInt(key);
						if (intData == 0) {
							return string = applicationInfo.metaData
									.getString(key) + "";
						} else {
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

	/**
	 * 根据地址获得bitmap图片
	 * 
	 * @param path
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}

	/**
	 * 保存一张bitmap图片，文件名为当前的时间,返回值为保存的sd卡路径
	 * 
	 * @param bitmap
	 * @param context
	 * @param url 
	 */
	public static String saveBitmapInExternalStorage(Bitmap bitmap,
			Context context, String url) {
		String path = null;
		try {
			if (MyUtils.haveSDCard()) {
				File extStorage = new File(MyConstants.ExternalStorageDirectory_SEARCHIMAGE);// searchImage为SD卡下一个文件夹
				if (!extStorage.exists()) {
					extStorage.mkdirs();
				}
				path = url + ".png";
				File file = new File(extStorage, path);
				FileOutputStream fOut = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);// 压缩图片
				fOut.flush();
				fOut.close();
				// Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
				LogUtil.e(context.getPackageName(), path + "保存成功");
			} else {
				LogUtil.e(context.getPackageName(), path + "保存失败");
				// Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return MyConstants.ExternalStorageDirectory_SEARCHIMAGE+"/" + path;
	}

	/**
	 * 判断是否有SD卡
	 * 
	 * @return
	 */
	public static boolean haveSDCard() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

}
