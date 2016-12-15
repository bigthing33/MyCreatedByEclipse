package com.example.testdemo.base;


import java.lang.reflect.ParameterizedType;

import android.app.Dialog;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.example.testdemo.utils.MyUtils;

/**
 * 使用GsonRequest封装的异步请求类
 * 
 * @author liuyk@lzt.com.cn
 * @param <T>
 *            网络请求返回的数据对象
 */
public abstract class AsyncGsonRequest<T> {

	public static final String TAG = "AsyncGsonRequest";

	private int mMethod;
	private String mUrl;
	private Object mRequestObj;
	private Object mTag;
	private ProgressBar mProgressBar;
	private Dialog mDialog;
	private Class<?> mClass;
	
	/**
	 * 网络请求返回对象的回调方法，该方法可以直接在UI线程中调用
	 * 
	 * @param t
	 *            经过gson转换后返回的数据对象
	 */
	protected abstract void onPostExecute(T t);
	
	/**
	 * 网络请求异常回调的的方法
	 * 
	 * @param e
	 */
	protected abstract void onPostError(VolleyError e);
	
	/**
	 * 使用GsonRequest异步请求网络
	 * 
	 * @param method
	 *            Method.POST/Method.GET
	 * @param url
	 *            请求网络的绝对路径
	 * @param requestObj
	 *            往服务器发送的json对象
	 * @param tag
	 *            设置的网络请求Request的tag，用来取消请求时使用，一般传入Activity对象
	 */
	public AsyncGsonRequest(int method, String url, Object requestObj, Object tag) {
		mMethod = method;
		mUrl = url;
		mRequestObj = requestObj;
		mTag = tag;
	}
	
	/**
	 * 使用GsonRequest异步请求网络，以POST方式提交
	 * 
	 * @param url
	 *            请求网络的绝对路径
	 * @param requestObj
	 *            往服务器发送的json对象
	 * @param tag
	 *            设置的网络请求Request的tag，用来取消请求时使用，一般传入Activity对象
	 */
	public AsyncGsonRequest(String url, Object requestObj, Object tag) {
		this(Method.POST, url, requestObj, tag);
	}
	
	private Class<?> getClz() {
		if (mClass == null) {
			mClass = ((Class<?>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return mClass;
	}
	
	/**
	 * 开始执行网络请求
	 */
	public void execute() {
		ErrorListener errorListener = new ErrorListener();
		final GsonRequest<T> request = new GsonRequest<T>(mMethod, mUrl, mRequestObj, getClz() , new SuccessListener<T>(), errorListener);
		showProgress();
		if(!MyUtils.isNetworkConnected()) {//没有设置网络连接
			errorListener.onErrorResponse(new NetNotSetConnectError("没有网络连接"));
			return;
		}
//		request.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		RequestManager.addRequest(request, mTag);
	}
	
	/**
	 * 设置网络访问过程中的loading控件
	 * @param progressBar
	 */
	public AsyncGsonRequest<T> progess(ProgressBar progressBar) {
		mProgressBar = progressBar;
		return this;
	}
	
	/**
	 * 设置网络访问过程中的loading控件
	 * @param progressBar
	 */
	public AsyncGsonRequest<T> progess(Dialog dialog) {
		mDialog = dialog;
		return this;
	}
	
	@SuppressWarnings({"hiding","unchecked"})
	private final class SuccessListener<Object> implements Response.Listener<Object> {
		@Override
		public void onResponse(Object t) {
			onPostExecute((T)t);
			cancelProgress();
		}
	}
	
	private final class ErrorListener implements Response.ErrorListener {
		@Override
		public void onErrorResponse(VolleyError volleyError) {
			onPostError(volleyError);
			cancelProgress();
		}
	}
	
	private void showProgress() {
		if(mProgressBar != null) {
			mProgressBar.setVisibility(View.VISIBLE);
		}
		if(mDialog != null) {
			mDialog.show();
		}
	}
	
	private void cancelProgress() {
		if(mProgressBar != null) {
			mProgressBar.setVisibility(View.GONE);
		}
		if(mDialog != null) {
			if(mDialog.isShowing()) {
				mDialog.dismiss();
			}
			mDialog = null;
		}
	}
}
