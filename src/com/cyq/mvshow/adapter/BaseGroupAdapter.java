package com.cyq.mvshow.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 */
abstract class BaseGroupAdapter<T> extends BaseAdapter {

    public BaseGroupAdapter(Context context) {
        super();
    }

    ArrayList<T> group = null;

    @Override
    public int getCount() {
        return (group == null) ? 0 : group.size();
    }

    @Override
    public Object getItem(int position) {
        return group.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (group == null) ? true : group.isEmpty();
    }

    public void setGroup(ArrayList<T> g) {
        group = g;
        notifyDataSetChanged();
    }

}
