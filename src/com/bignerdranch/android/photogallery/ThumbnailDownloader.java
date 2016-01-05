package com.bignerdranch.android.photogallery;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

public class ThumbnailDownloader<Handle> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    
    Handler mHandler;
    //请求集合，
    /**
     * 一个方便的方法就是利用Collections类的静态的synchronizedMap()方法，它创建一个线程安全的Map对象，并把它作为一个封装的对象来返回。这个对象的方法可以让你同步访问潜在的HashMap
     */
    Map<Handle,String> requestMap = 
            Collections.synchronizedMap(new HashMap<Handle,String>());
    //存放来自主线程的Handler，该线程的looper是主线程的loopre，所以该handler的handler
    Handler mResponseHandler;
    Listener<Handle> mListener;
    //定义一个监听接口，这个接口在fragment中被定义在PhotoGalleryFragment调用，这样，在这ThumbnailDownloader发生的事就能够通知到PhotoGalleryFragment里了。
   //比如这边的
    public interface Listener<Handle> {
        void onThumbnailDownloaded(Handle handle, Bitmap thumbnail);
    }
    
    public void setListener(Listener<Handle> listener) {
        mListener = listener;
    }

    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }
    
    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
		/**
		 * 当创建HandlerThread时，系统会自动给这个线程配置一个looper，当配置完成时会调用onLooperPrepared方法。
		 * 它发生在第一次检查消息队列之前。 在这个方法里可以给mHandler实例化，重写handleMessage方法
		 */
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    @SuppressWarnings("unchecked")
                    Handle handle = (Handle)msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(handle));
                    handleRequest(handle);
                }
            }
        };
    }
/**
 * 根据传入的handler找到请求的url，获取字节码，生成图片。
 * @param handle
 */
    private void handleRequest(final Handle handle) {
        try {
        	//从请求体中获取url，根据key值，我想不明白为什么key值要用Handle
            final String url = requestMap.get(handle);
            if (url == null) 
                return;
            //根据url获得图片的字节数组，该字节数组可以生成一个bitmap图片
            byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
            //根据字节数组生成一个bitmap图片
            final Bitmap bitmap = BitmapFactory
                    .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            //使用主线程的handler，完成在子线程访问主线程的事情。
            //这个post等同于将send和handlermessage一起处理了，因为是用的主线程的Handler，所以其内容其实是主线程的looper执行的。即在主线程中更新UI
            mResponseHandler.post(new Runnable() {
                public void run() {
                    if (requestMap.get(handle) != url)
                        return;
                    //先移除这次请求的url，防止多次请求
                    requestMap.remove(handle);
                    //在这里调用监听器的方法，此方法是在fragment中被定义的。真正的执行是在定义的地方发生的。
                    mListener.onThumbnailDownloaded(handle, bitmap);
                }
            });
        } catch (IOException ioe) {
            Log.e(TAG, "Error downloading image", ioe);
        }
    }
    
    public void queueThumbnail(Handle handle, String url) {
    	//每调用一次这个方法，就将传来的参数放入到入请求集合中
        requestMap.put(handle, url);
        //发送一个消息，也就是说会调用一次handleMessage
        mHandler
            .obtainMessage(MESSAGE_DOWNLOAD, handle)
            .sendToTarget();
    }
    
    public void clearQueue() {
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }
}
