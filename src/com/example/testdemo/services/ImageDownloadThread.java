package com.example.testdemo.services;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.example.testdemo.MyApplication;
import com.example.testdemo.model.Image;
import com.example.testdemo.utils.LogUtil;

public class ImageDownloadThread<Handle> extends HandlerThread {
    private static final String TAG = "ImageDownloadThread";
    private static final int MESSAGE_DOWNLOAD = 0;
	Handler mHandler;
	private ArrayList<Image> imageList;
	

	
    Handler mResponseHandler;
    Listener mListener;
    public ImageDownloadThread( Handler mHandler) {
		super(TAG);
		mResponseHandler = mHandler;
	}
    public interface Listener {
        void imageDownloaded(Image image);
        void completedAllImgDownload();
    }
    
    public void setListener(Listener listener) {
        mListener = listener;
    }
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
                    handleRequest();
                }
            }
        };
    }
    private void handleRequest() {
        try {
            if (imageList == null) 
                return;
            //根据url获得图片的字节数组，该字节数组可以生成一个bitmap图片
            Log.e(TAG, "handleRequest");
            for (int i = 0; i < imageList.size(); i++) {
            	final Image image=imageList.get(i);
            	final Bitmap bitmap=MyApplication.imageLoader.loadImageSync(image.getObjUrl());
            	image.setBitmap(bitmap);
            	LogUtil.e("handleRequest", "第"+i+"个图片完成加载了");
                //使用主线程的handler，完成在子线程访问主线程的事情。
                mResponseHandler.post(new Runnable() {
                    public void run() {
                        //在这里调用监听器的方法，此方法是在fragment中被定义的。真正的执行是在定义的地方发生的。
                        mListener.imageDownloaded(image);
                    }
                });

			}
            mResponseHandler.post(new Runnable() {
                public void run() {
                    //在这里调用监听器的方法，此方法是在fragment中被定义的。真正的执行是在定义的地方发生的。
                    mListener.completedAllImgDownload();
                }
            });

        } catch (Exception ioe) {
            Log.e(TAG, "Error downloading image", ioe);
        }
    }
    public void queueThumbnail( ArrayList<Image> images) {
    	imageList=images;
        //发送一个消息，也就是说会调用一次handleMessage
        mHandler
            .obtainMessage(MESSAGE_DOWNLOAD)
            .sendToTarget();
    }

}
