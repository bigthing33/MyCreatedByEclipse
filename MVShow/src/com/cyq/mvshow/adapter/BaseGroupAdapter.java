package com.cyq.mvshow.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

abstract class BaseGroupAdapter<T> extends BaseAdapter {
	protected Context mContext;

	public BaseGroupAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

}
