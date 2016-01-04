package com.example.testdemo.base;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Volley访问网络的请求管理类
 * @author liuyk@lzt.com.cn
 *
 */
public class RequestManager {
	private static RequestQueue mRequestQueue;
	public static final String TAG = "RequestManager";
	
	private RequestManager() {
	}

	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
	}
	
	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}
	
	public static void addRequest(Request<?> request, Object tag) {
        /*if (tag != null) {
            request.setTag(tag);
        }*/
        mRequestQueue.add(request);
    }
	
	public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
	
}
