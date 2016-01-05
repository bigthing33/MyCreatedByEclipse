package com.example.testdemo;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.testdemo.base.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageLoaderActivity extends BaseActivity {
	private ImageView sample_img;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void initUI() {
		sample_img = (ImageView) findViewById(R.id.sample_img);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage("http://img.blog.csdn.net/20140212171808656?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvaHVhY3VpbGFpZmE=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast", sample_img);

	}

}
