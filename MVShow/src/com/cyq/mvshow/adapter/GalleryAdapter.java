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
import com.cyq.mvshow.utils.LogUtil;
import com.cyq.mvshow.utils.MyImageLoader;
import com.cyq.mvshow.utils.MyUrl;

/**
 */
public class GalleryAdapter extends BaseGroupAdapter {

    private LayoutInflater mInflater;
	private boolean isCollectModel;
	public ArrayList<Gallery> selectGalleries = new ArrayList<Gallery>();
	public ArrayList<Gallery> localGalleries = new ArrayList<Gallery>();
	public ArrayList<StringBuilder> tags = new ArrayList<StringBuilder>();
	public ArrayList<StringBuilder> selectTags = new ArrayList<StringBuilder>();
	

    public boolean isCollectModel() {
		return isCollectModel;
	}

	public void setCollectModel(boolean isCollectModel) {
		this.isCollectModel = isCollectModel;
	}

	public GalleryAdapter(Context context) {
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
        Gallery gallery = (Gallery) getItem(position);
//      orderHolder.titleView.setText(gallery.getTitle());
        
        orderHolder.titleView.setText("图片");
        if (tags.size()>position&&!tags.get(position).toString().equals("下载失败")) {
        	//如果包含了下载失败说明下载过一次而且失败了，这时候图片无需重新加载
        	MyImageLoader myImageLoader=new MyImageLoader();
         	myImageLoader.setmImageView(orderHolder.imageView);
        	myImageLoader.setmProgress_img(orderHolder.progressBar);
        	myImageLoader.setmReload_tv(orderHolder.reload_tv);
        	myImageLoader.displayPicture(MyUrl.TIANGOU_SERVICE+ gallery.getImg(), tags.get(position));
		}
        
        if (isCollectModel) {
        	orderHolder.select_img.setVisibility(View.VISIBLE);
        	if (selectGalleries.contains(gallery)) {
        		orderHolder.select_img.setImageResource(R.drawable.pr_selected);
			}else{
				orderHolder.select_img.setImageResource(R.drawable.pr_unselected);
			}
		}else{
			orderHolder.select_img.setVisibility(View.GONE);
		}

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
		return localGalleries.size();
	}

	@Override
	public Object getItem(int position) {
		return localGalleries.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
