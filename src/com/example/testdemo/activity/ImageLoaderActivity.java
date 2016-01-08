package com.example.testdemo.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.testdemo.R;
import com.example.testdemo.R.id;
import com.example.testdemo.R.layout;
import com.example.testdemo.base.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
		imageLoader.init(ImageLoaderConfiguration.createDefault(ImageLoaderActivity.this));
		imageLoader.displayImage("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png", sample_img);

	}

}
