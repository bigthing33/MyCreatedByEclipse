package com.example.testdemo.base;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.example.testdemo.utils.LogUtil;
import com.example.testdemo.utils.MyUtils;
import com.google.gson.Gson;

/**
 * 使用gson封装的volley请求对象
 * @author liuyk@lzt.com.cn
 *
 * @param <T>
 * 要将返回数据转换的json对象
 */
public class GsonRequest<T> extends JsonRequest<T> {

	private static Gson gson;
	private Class<?> mClass;
	private final static String TAG = "GsonRequest";
	private String mUrl;
	
	/**
	 * 构造GsonRequest
	 * 
	 * @param method
	 *            HTTP请求方法，POST、GET等
	 * @param url
	 *            网络请求的绝对地址
	 * @param requestBody
	 *            提交服务的json字符串
	 * @param clazz
	 *            返回数据的对象类型
	 * @param listener
	 *            网络请求成功的监听对象，回调方法中获取返回的数据
	 * @param errorListener
	 *            网络请求异常的监听对象
	 */
	public GsonRequest(int method, String url, String requestBody, Class<?> clazz, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, requestBody, listener, errorListener);
		mUrl = url;
		mClass = clazz;
		LogUtil.d(TAG, createLogStr(true, url, requestBody));
	}
	
	/**
	 * 构造GsonRequest
	 * 
	 * @param method
	 *            HTTP请求方法，POST、GET等
	 * @param url
	 *            网络请求的绝对地址
	 * @param requestBody
	 *            提交服务的对象，会被转换为json字符串提交到服务
	 * @param clazz
	 *            返回数据的对象类型
	 * @param listener
	 *            网络请求成功的监听对象，回调方法中获取返回的数据
	 * @param errorListener
	 *            网络请求异常的监听对象
	 */
	public GsonRequest(int method, String url, Object requestObject, Class<?> clazz, Listener<T> listener, ErrorListener errorListener) {
		this(method, url, getRequestBody(requestObject), clazz, listener, errorListener);
	}
	
	private static String getRequestBody(Object requestObject) {
		if(requestObject instanceof String) {
			return (String) requestObject;
		}
		return getGson().toJson(requestObject);
	}
	
	/**
	 * 请求网络结果的数据处理和分发
	 */
	@SuppressWarnings({"unchecked"})
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		if(200 == response.statusCode) {
			try {
				String json = new String(response.data,
						HttpHeaderParser.parseCharset(response.headers));
				LogUtil.d(TAG, createLogStr(false, mUrl, json));
				return (Response<T>) Response.success(getGson().fromJson(json, mClass),
						HttpHeaderParser.parseCacheHeaders(response));
			} catch (Exception e) {
				return Response.error(new ParseError(e));
			}
		} else {
			LogUtil.e(TAG, createLogStr(false, mUrl, "response.statusCode=" + response.statusCode));
			return Response.error(new ParseError(response));
		}
	}

	private static Gson getGson() {
		if(gson == null) gson = new Gson();
		return gson;
	}
	
	private String createLogStr(boolean isReq, String url, String requestBody) {
		StringBuffer sbBuffer = new StringBuffer(isReq ? "request[" : "response[");
		sbBuffer.append(url).append("]--->").append(requestBody);
		return sbBuffer.toString();
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("systype", "0");
		headerMap.put("version", getAppVersionName());
		return headerMap;
	}
	
	private static String appVersionName;

	public static String getAppVersionName() {
		if(TextUtils.isEmpty(appVersionName)) {
			appVersionName = MyUtils.getAppVersionName(new Application());
		}
		return appVersionName;
	}
	
}
