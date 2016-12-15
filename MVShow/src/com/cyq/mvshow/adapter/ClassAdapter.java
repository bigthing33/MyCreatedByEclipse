package com.cyq.mvshow.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.R;
import com.cyq.mvshow.model.Gallery;
import com.cyq.mvshow.model.Galleryclassify;
import com.cyq.mvshow.utils.LogUtil;
import com.cyq.mvshow.utils.MyImageLoader;
import com.cyq.mvshow.utils.MyUrl;

/**
 */
public class ClassAdapter extends BaseGroupAdapter {

    private LayoutInflater mInflater;
    public ArrayList<Galleryclassify> galleryclassifies=new ArrayList<Galleryclassify>();	

	public ClassAdapter(Context context) {
		super(context);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder orderHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_gallery, null);
            orderHolder = new ViewHolder();
            orderHolder.titleView = (TextView) convertView.findViewById(R.id.title);
            orderHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            orderHolder.select_img=(ImageView) convertView.findViewById(R.id.select_img);
            orderHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            orderHolder.reload_tv = (TextView) convertView.findViewById(R.id.reload_tv);
            convertView.setTag(orderHolder);
        } else {
            orderHolder = (ViewHolder) convertView.getTag();
        }
        orderHolder.progressBar.setVisibility(View.GONE);
        orderHolder.reload_tv.setVisibility(View.GONE);
        orderHolder.imageView.setImageDrawable(null);
        Galleryclassify galleryclassify = (Galleryclassify) getItem(position);
//      orderHolder.titleView.setText(gallery.getTitle());
        
        orderHolder.titleView.setText("图片"+position);
        

        return convertView;
    }

    static class ViewHolder {
        TextView titleView;
        ImageView imageView;
        ImageView select_img;
        ProgressBar progressBar;
        TextView reload_tv;
    }

	@Override
	public int getCount() {
		return galleryclassifies.size();
	}

	@Override
	public Object getItem(int position) {
		return galleryclassifies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
