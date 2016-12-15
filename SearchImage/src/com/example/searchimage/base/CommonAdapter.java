package com.example.searchimage.base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {

	private Context mContext;
	private List<T> mDatas;
	private int viewId;
	private LayoutConvertViewListener layoutListener;
	
	public CommonAdapter(Context mContext, List<T> mDatas, int viewId) {
		super();
		this.mContext = mContext;
		this.mDatas = mDatas;
		this.viewId = viewId;
	}
	
	public interface LayoutConvertViewListener {
		void onLayout(View layoutView);
	}

	public void setOnLayoutListener(LayoutConvertViewListener layoutListener) {
		this.layoutListener = layoutListener;
	}

	@Override
	public int getCount() {
		return mDatas == null ? 0 : mDatas.size();
	}
	
	

	@Override
	public T getItem(int position) {
		return mDatas == null ? null : mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = ViewHolder.getViewHolder(mContext, convertView, parent, viewId);
		if(layoutListener != null) {
			holder.setOnLayoutListener(layoutListener);
		}
		convert(holder, getItem(position), position);
		return holder.getConvertView();
	}

	public void setDatas(List<T> mDatas) {
		this.mDatas = mDatas;
	}
	
	public List<T> getDatas() {
		return mDatas;
	}

	public void setmDatas(List<T> mDatas) {
		this.mDatas = mDatas;
	}

	public abstract void convert(ViewHolder holder, T t, int position);
	
	public void notifydata(){
		this.notifyDataSetChanged();
	}
	
	public boolean isEmpty(){
		return getCount() == 0;
	}
	
}
