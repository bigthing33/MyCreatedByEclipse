package com.example.testdemo.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.testdemo.R;
import com.example.testdemo.base.BaseActivity;

public class WebViewActivity extends BaseActivity implements OnClickListener {
	private final String TAG = WebViewActivity.class.getSimpleName();
	private WebView myWebView;
	private Context mContext;
	private Button callJs;
	private Button callJsToCallJava;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		mContext = WebViewActivity.this;
		myWebView = (WebView) findViewById(R.id.webview);
		setWebView();
		initUI();

		
	}
    private void setWebView() {
    	WebSettings webSettings = myWebView.getSettings();
    	/*
		 * 互联网用：myWebView.loadUrl("http://www.google.com");
		 * 本地文件用：myWebView.loadUrl("file:///android_asset/XX.html");
		 * 本地文件存放在：assets 文件中 // 载入这个html页面 String htmlString =
		 * "<h1>Title</h1><p>This is HTML text<br /><i>Formatted in italics</i><br />Anothor Line</p>"
		 * ; myWebView.loadData(htmlString, "text/html", "utf-8");
		 * 
		 * 5.如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点。
		 * 
		 * webview.requestFocusFromTouch();
		 */
/*		//所有都在webView中打开网页，不会使用浏览器打开网页了
		 myWebView.setWebViewClient(new WebViewClient());
		 myWebView.loadUrl("http://www.baidu.com");
        //控制打开网页的地方
		myWebView.setWebViewClient(new MyWebViewClient());
		myWebView.loadUrl("http://www.baidu.com");*/
/*    	//优先使用缓存：
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//不使用缓存：
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);*/
    	/**
    	 * webview与JavaScript之间的交互
    	 */
    	
    	//使能使用JavaScript
		webSettings.setJavaScriptEnabled(true);
		//addJavascriptInterface，使得js能够调用java代码
		myWebView.addJavascriptInterface(new JsInteration(), "control");
		myWebView.setWebChromeClient(new WebChromeClient() {
		});
		myWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				//当page也加载完后
				super.onPageFinished(view, url);
//				javaUseJavaScript(myWebView);
//				testEvaluateJavascript( myWebView);
			}

		});
		webSettings.setDefaultTextEncodingName("utf-8");//设置加载网页的编码
		myWebView
		.loadUrl("file:///android_asset/js_java_interaction.html");
		
	}
/**
 * 调用JavaScript方法获得返回值
 * @param myWebView2
 */
	@SuppressLint("NewApi")
	protected void testEvaluateJavascript(WebView myWebView2) {
		myWebView.evaluateJavascript("getReturn(\""+"参数1"+"\")", new ValueCallback<String>() {
			
			@Override
			public void onReceiveValue(String value) {
				Log.i(TAG, "onReceiveValue value=" + value);
			}
		});
	}
	/**
	 * java调用js方法
	 * 
	 * @param webView
	 */
	private void javaUseJavaScript(WebView webView) {
		String call = "javascript:sayHello()";

		// call = "javascript:alertMessage(\"" + "content" + "\")";
		//
		// call = "javascript:toastMessage(\"" + "content" + "\")";
		//
		// call = "javascript:sumToJava(1,2)";
		webView.loadUrl(call);
	}
    /**
     * 实现js调用java的具体代码
     * @author cyq
     *
     */
    public class JsInteration {
        

		@JavascriptInterface
        public void toastMessage(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
        
        @JavascriptInterface
        public void onSumResult(int result) {
            Log.i(TAG, "onSumResult result=" + result);
        }
        @JavascriptInterface
        public void showToastl(String result) {
        	showToast(result);
        	Log.i(TAG, "onSumResult result=" + result);
        }
    }
	/**
     * 按键响应，在WebView中查看网页时，按返回键的时候按浏览历史退回,如果不做此项处理则整个WebView返回退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack())
        {
        	/*
        	 * 　canGoBack() 方法在网页可以后退时返回true。
        	 * 　类似的，canGoForward()方法可以检查是否有可以前进的历史记录。
        	 */
			// 这个是前进
			// myWebView.goForward();
			// 返回键退回
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up
        // to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
	private class MyWebViewClient extends WebViewClient
    {
		

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.e(TAG, url+"  getHost:"+Uri.parse(url).getHost());
			if (Uri.parse(url).getHost().equals("m.baidu.com")) {
             // This is my web site, so do not override; let my WebView load
             // the page
              return false;
			}
            // Otherwise, the link is not for a page on my site, so launch
            // another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
		}
    }
	/**
	 * clear the cache before time numDays  
	 * 删除保存于手机上的缓存.
	 */
	private int clearCacheFolder(File dir, long numDays) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			//如果路径不为null且是文件夹
			for (File child : dir.listFiles()) {
				if (child.isDirectory()) {
					//如果是文件夹则递归调用删除文件的方法
					deletedFiles += clearCacheFolder(child, numDays);
				}
				if (child.lastModified() < numDays) {
					//如果文件最后改动的时间小于numDays就是要删除掉早于时间numDays的文件
					if (child.delete()) {
						deletedFiles++;
					}
				}
			}
		}
		return deletedFiles;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void initUI() {
		callJs=(Button) findViewById(R.id.callJs);
		callJs.setOnClickListener(this);
		callJsToCallJava=(Button) findViewById(R.id.callJsToCallJava);
		callJsToCallJava.setOnClickListener(this);
		

	}
	public static void actionStart(Context context,Class<?> activityClass) {
		Intent intent = new Intent(context, activityClass);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
	}
//	private void clearWebViewCache(){
//		
//		File file = CacheManager.getCacheFileBaseDir(); //获得缓存的文件
//		   if (file != null && file.exists() && file.isDirectory()) { 
//		    for (File item : file.listFiles()) { 
//		     item.delete(); 
//		    } 
//		    file.delete(); 
//		   } 
//		   
//		   mContext.deleteDatabase("webview.db"); 
//		   mContext.deleteDatabase("webviewCache.db");
//		
//	}
	@Override
	public void onClick(View v) {
		String call;
		switch (v.getId()) {
		case R.id.callJs:
			 call = "javascript:alertMessage(\"" + "你好java" + "\")";
			myWebView.loadUrl(call);
			break;
		case R.id.callJsToCallJava:
			 call = "javascript:toastMessage(\"" + "我先到了js又回到了java，请问我还是原来的我吗？" + "\")";
			myWebView.loadUrl(call);
			break;

		default:
			break;
		}
		
	}

}
