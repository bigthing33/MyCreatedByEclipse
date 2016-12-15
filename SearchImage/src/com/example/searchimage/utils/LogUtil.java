package com.example.searchimage.utils;

import android.util.Log;

public class LogUtil {
	public static final int LEVEL = 1;

	public static final int VERBOSE = 1;

	public static final int DEBUG = 2;

	public static final int INFO = 3;

	public static final int WARN = 4;

	public static final int ERROR = 5;

	public static final int NOTHING = 6;

	public static void v(String tag, String msg, Throwable t) {
		if (LEVEL <= VERBOSE)
			Log.v(tag, createNewLogMessage(msg), t);
	}
	public static void d(String tag, String msg, Throwable t) {
		if (LEVEL <= DEBUG)
			Log.d(tag, createNewLogMessage(msg), t);
	}
	public static void i(String tag, String msg, Throwable t) {
		if (LEVEL <= INFO)
			Log.i(tag, createNewLogMessage(msg), t);
	}
	public static void w(String tag, String msg, Throwable t) {
		if (LEVEL <= WARN)
			Log.w(tag, createNewLogMessage(msg), t);
	}
	public static void e(String tag, String msg, Throwable t) {
		if (LEVEL <= WARN)
			Log.e(tag, createNewLogMessage(msg), t);
	}
	public static void v(String tag, String msg) {
		if (LEVEL <= VERBOSE)
			Log.v(tag, createNewLogMessage(msg));
	}
	public static void d(String tag, String msg) {
		if (LEVEL <= DEBUG)
			Log.d(tag, createNewLogMessage(msg));
	}
	public static void i(String tag, String msg) {
		if (LEVEL <= INFO)
			Log.i(tag, createNewLogMessage(msg));
	}
	public static void w(String tag, String msg) {
		if (LEVEL <= WARN)
			Log.w(tag, createNewLogMessage(msg));
	}
	public static void e(String tag, String msg) {
		if (LEVEL <= WARN)
			Log.e(tag, createNewLogMessage(msg));
	}

	private static String createNewLogMessage(String paramString) {
		String str = getFunctionInfo();
		if (str == null)
			return paramString;
		return str + "\r\n" + paramString;
	}

	private static String getFunctionInfo() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(LogUtil.class.getName())) {
				continue;
			}
			return "[ " + Thread.currentThread().getName() + ":"
					+ st.getClassName() + "." + "" + st.getMethodName() + "():"
					+ st.getLineNumber() + " ]";
		}
		return null;
	}

}
