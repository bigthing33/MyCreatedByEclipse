package com.cyq.mvshow.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.R;
import com.cyq.mvshow.model.Picture;

/**
 */
public class PictureAdapter extends BaseGroupAdapter {

    private LayoutInflater mInflater;
	private boolean isCollectModel;
	public ArrayList<Picture> selectPictures = new ArrayList<Picture>();
	

    public boolean isCollectModel() {
		return isCollectModel;
	}

	public void setCollectModel(boolean isCollectModel) {
		this.isCollectModel = isCollectModel;
	}

	public PictureAdapter(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder orderHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_content_area, null);
            orderHolder = new ViewHolder();
            orderHolder.titleView = (TextView) convertView.findViewById(R.id.title);
            orderHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            orderHolder.select_img=(ImageView) convertView.findViewById(R.id.select_img);
            convertView.setTag(orderHolder);
        } else {
            orderHolder = (ViewHolder) convertView.getTag();
        }


        Picture picture = (Picture) getItem(position);
//        orderHolder.titleView.setText(gallery.getTitle());
//        MyApplication.imageLoader.getInstance().displayImage(MyUrl.TIANGOU_SERVICE+ gallery.getImg(), orderHolder.imageView);
        orderHolder.titleView.setText("图片");
        if (orderHolder.imageView.getDrawable()==null) {
        	MyApplication.imageLoader.getInstance().displayImage("erro", orderHolder.imageView);
		}
        if (isCollectModel) {
        	orderHolder.select_img.setVisibility(View.VISIBLE);
        	if (selectPictures.contains(picture)) {
        		orderHolder.select_img.setImageResource(R.drawable.pr_selected);
			}else{
				orderHolder.select_img.setImageResource(R.drawable.pr_unselected);
			}
		}else{
			orderHolder.select_img.setVisibility(View.GONE);
		}

        return convertView;
    }

    @Override
    public void setGroup(ArrayList g) {
        super.setGroup(g);
    }

    static class ViewHolder {
        TextView titleView;
        ImageView imageView;
        ImageView select_img;
    }

}
