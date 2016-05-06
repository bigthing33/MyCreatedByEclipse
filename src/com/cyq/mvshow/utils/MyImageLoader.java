package com.cyq.mvshow.utils;


import android.graphics.Bitmap;
import android.nfc.Tag;
import android.view.View;
import android.widget.ImageView;

import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.R;
import com.cyq.mvshow.model.Gallery;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class MyImageLoader {
	public ImageView mImageView;
	public View mProgress_img;
	public View mReload_tv;
	public StringBuilder mtag;
	private MyImageLoadingListener mImageLoadingListener = new MyImageLoadingListener();


	public void displayImage(String src) {
		MyApplication.imageLoader.displayImage("erro", mImageView,mImageLoadingListener);
	}
	public void displayPicture(String src,StringBuilder tag) {
		mtag=tag;
		MyApplication.imageLoader.displayImage("erro", mImageView,mImageLoadingListener);
	}

	private class MyImageLoadingListener implements ImageLoadingListener {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			if (mImageView!=null) {
				mImageView.setTag(arg0);
			}
			if (mProgress_img!=null) {
				mProgress_img.setVisibility(View.VISIBLE);
			}
			if (mReload_tv!=null) {
				mReload_tv.setVisibility(View.GONE);
			}
			replaceMtag("开始下载");

		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			if (mProgress_img!=null) {
//				mProgress_img.setVisibility(View.GONE);
			}
			if (mReload_tv!=null) {
				mReload_tv.setVisibility(View.VISIBLE);
			}
			replaceMtag("下载失败");
		}

		@Override
		public void onLoadingComplete(String tag, View view, Bitmap bitmap) {
			if (mProgress_img!=null) {
				mProgress_img.setVisibility(View.GONE);
			}
			if (mReload_tv!=null) {
				mReload_tv.setVisibility(View.GONE);
			}
			if (mImageView!=null&&mImageView.getTag().equals(tag)) {
				ImageView img = (ImageView) view;
				img.setImageBitmap(bitmap);
//				img.setImageResource(R.drawable.loaded);
			}
			replaceMtag("下载完成");

		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			if (mProgress_img!=null) {
				mProgress_img.setVisibility(View.GONE);
			}
			if (mReload_tv!=null) {
				mReload_tv.setVisibility(View.GONE);
			}
			if (mtag!=null) {
				replaceMtag("未下载");
			}
		}
	}

	public ImageView getmImageView() {
		return mImageView;
	}

	public void setmImageView(ImageView mImageView) {
		this.mImageView = mImageView;
	}

	public View getmProgress_img() {
		return mProgress_img;
	}

	public void setmProgress_img(View mProgress_img) {
		this.mProgress_img = mProgress_img;
	}

	public View getmReload_tv() {
		return mReload_tv;
	}

	public void setmReload_tv(View mReload_tv) {
		this.mReload_tv = mReload_tv;
	}

	public MyImageLoadingListener getmImageLoadingListener() {
		return mImageLoadingListener;
	}

	public void setmImageLoadingListener(MyImageLoadingListener mImageLoadingListener) {

		this.mImageLoadingListener = mImageLoadingListener;
	}
	public StringBuilder getMtag() {
		return mtag;
	}
	public void replaceMtag(String str) {
		if (this.mtag!=null) {
			this.mtag.replace(0, this.mtag.length(), str);
		}
	}
}
