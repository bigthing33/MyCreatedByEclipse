package com.cyq.mvshow.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.cyq.mvshow.MyApplication;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class MyImageLoader {
	public ImageView mImageView;
	public View mProgress_img;
	public View mReload_tv;
	private MyImageLoadingListener mImageLoadingListener = new MyImageLoadingListener();


	public void displayImage(String uri) {
		MyApplication.imageLoader.displayImage("erro", mImageView,mImageLoadingListener);
//		MyApplication.imageLoader.displayImage(uri, mImageView,mImageLoadingListener);
	}

	private class MyImageLoadingListener implements ImageLoadingListener {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			mImageView.setTag(arg0);
			mProgress_img.setVisibility(View.VISIBLE);
			mReload_tv.setVisibility(View.GONE);

		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			mProgress_img.setVisibility(View.GONE);
			mReload_tv.setVisibility(View.VISIBLE);

		}

		@Override
		public void onLoadingComplete(String tag, View view, Bitmap bitmap) {
			mProgress_img.setVisibility(View.GONE);
			mReload_tv.setVisibility(View.GONE);
			if (mImageView.getTag().equals(tag)) {
				ImageView img = (ImageView) view;
				img.setImageBitmap(bitmap);
//				img.setImageResource(R.drawable.loaded);
			}

		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			mReload_tv.setVisibility(View.GONE);
			mProgress_img.setVisibility(View.GONE);
		}
	}

/*	public final static DisplayImageOptions getLargeDisplayOptions() {
		// if(largeDisplayOptions != null) return largeDisplayOptions;
		return new DisplayImageOptions.Builder()
		// .showImageForEmptyUri(R.drawable.large_default) //
		// 设置图片Uri为空或是错误的时候显示的图片
		// .showImageOnFail(R.drawable.large_default) // 设置图片加载或解码过程中发生错误显示的图片
		// .showImageOnLoading(R.drawable.large_default) //
		// 创建配置过得DisplayImageOption对象
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true).build(); // 设置下载的图片是否缓存在SD卡中
		// return largeDisplayOptions;
	}
*/}
