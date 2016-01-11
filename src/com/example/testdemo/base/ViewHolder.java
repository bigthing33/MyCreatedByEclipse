package com.example.testdemo.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testdemo.base.CommonAdapter.LayoutConvertViewListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ViewHolder {

	private SparseArray<View> mViews;
	private int viewResouceId;
	private View convertView;
	private LayoutConvertViewListener layoutListener;
	
	public void setOnLayoutListener(LayoutConvertViewListener layoutListener) {
		this.layoutListener = layoutListener;
	}

	public ViewHolder(Context mContext, ViewGroup parent, int viewResouceId) {
		super();
		this.viewResouceId = viewResouceId;
		this.mViews = new SparseArray<View>();
		this.convertView = LayoutInflater.from(mContext).inflate(viewResouceId, parent, false);
		if (layoutListener != null) {
			layoutListener.onLayout(convertView);
		}
	}
 
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View v = mViews.get(viewId);
		if (v != null) {
			return (T) v;
		}
		View view = convertView.findViewById(viewId);
		mViews.put(viewId, view);
		return (T) view;
	}

	public static final ViewHolder getViewHolder(Context context,
			View convertView, ViewGroup parent, int viewResouceId) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder(context, parent, viewResouceId);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		return viewHolder;
	}

	public int getViewResouceId() {
		return viewResouceId;
	}

	public View getConvertView() {
		convertView.setTag(this);
		return convertView;
	}

	public ViewHolder setText(int viewId, CharSequence text) {
		((TextView) getView(viewId)).setText(text);
		return this;
	}

	public ViewHolder setText(int viewId, CharSequence text, int color) {
		((TextView) getView(viewId)).setText(text);
		((TextView) getView(viewId)).setTextColor(color);
		return this;
	}
	
	public ViewHolder hide(int viewId) {
		 getView(viewId).setVisibility(View.GONE);
		return this;
	}
	
	public ViewHolder show(int viewId) {
		getView(viewId).setVisibility(View.VISIBLE);
		return this;
	}

	public ViewHolder setImageResource(int viewId, int resourceId) {
		((ImageView) getView(viewId)).setImageResource(resourceId);
		return this;
	}

	public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
		((ImageView) getView(viewId)).setImageBitmap(bm);
		return this;
	}

	public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
		((ImageView) getView(viewId)).setImageDrawable(drawable);
		return this;
	}

	//TODO 加载网络图片
	public ViewHolder setImageUrl(int viewId, String url, DisplayImageOptions imageOptions) {
		ImageLoader.getInstance().displayImage(url, (ImageView) getView(viewId), imageOptions);
		return this;
	}

}
