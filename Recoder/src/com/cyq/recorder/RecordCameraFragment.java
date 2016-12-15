package com.cyq.recorder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RecordCameraFragment extends Fragment {
	private static final String TAG="RecordCameraFragment";
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private View mProgressContainer;
	public static final String EXTRA_PHOTO_FILENAME="com.bignerdranch.android.recordintent.photo_fiename";
	
	private Camera.ShutterCallback mShutterCallback=new Camera.ShutterCallback() {
		
		@Override
		public void onShutter() {
			//显示进度
			mProgressContainer.setVisibility(View.VISIBLE);
			
		}
	};
	
	private Camera.PictureCallback mJpegCallback=new Camera.PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			//create a filename
			String filename=UUID.randomUUID().toString()+".jpg";
			//save the jpeg data to disk
			FileOutputStream os=null;
			boolean success=true;
			try {
				os=getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
				os.write(data);
			} catch (Exception e) {
				Log.e(TAG, "error writing to file"+filename,e);
				success=false;
			}finally{
				try {
					if(os!=null){
						os.close();
					}
				} catch (IOException e) {
					Log.e(TAG, "error closing file"+filename, e);
					success=false;
				}
			}
			if(success){
				Log.i(TAG, "jpeg saved at"+filename);
				Intent i= new Intent();
				i.putExtra(EXTRA_PHOTO_FILENAME, filename);
				getActivity().setResult(Activity.RESULT_OK, i);
			}else{
				getActivity().setResult(Activity.RESULT_CANCELED);
			}
			getActivity().finish();
			
		}
	};
	
	@Override
	@SuppressWarnings(value = { "deprecation" })
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_record_camera, container,false);
		mProgressContainer =v.findViewById(R.id.Record_camera_progressContainer);
		mProgressContainer.setVisibility(View.INVISIBLE);
		Button takePictureButton=(Button) v.findViewById(R.id.record_camera_takePictureButton);
		takePictureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//getActivity().finish();
				if(mCamera!=null){
					mCamera.takePicture(mShutterCallback, null, mJpegCallback);
				}
			}
		});
		mSurfaceView=(SurfaceView) v.findViewById(R.id.record_camera_surfaceView);
		SurfaceHolder holder=mSurfaceView.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(new Callback() {
			

			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// 告诉相机去使用surface作为自己的预览区域
				try {
					if(mCamera!=null){
						mCamera.setPreviewDisplay(holder);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "error setting up preview diaplay", e);
				}
				
			}
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if(mCamera!=null){
					mCamera.stopPreview();
				}				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				//这个surface的size 已经改变了，请更新size
				Camera.Parameters parameters=mCamera.getParameters();
				Size s=getBestSupportSize(parameters.getSupportedPreviewSizes(),width,height);
				parameters.setPreviewSize(s.width, s.height);
				s=getBestSupportSize(parameters.getSupportedPreviewSizes(), width, height);
				parameters.setPictureSize(s.width, s.height);
				mCamera.setParameters(parameters);
				try {
					mCamera.startPreview();
				} catch (Exception e) {
					Log.e(TAG,"could not start preview",e);
					mCamera.release();
					mCamera=null;
					
				}
			}
		});
		return v;
	}
	
	/**
	 * 找出设备的最佳尺寸
	 */
	private Size getBestSupportSize(List<Size> sizes,int width,int heigt){
		Size bestSize =sizes.get(0);
		int largesArea=bestSize.width*bestSize.height;
		for(Size s:sizes){
			int area=s.width*s.height;
					if(area>largesArea){
						bestSize=s;
						largesArea=area;
					}
			
		}
		return bestSize;
	}
	
	@TargetApi(9)
	@Override
	public void onResume() {
		super.onResume();
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD){
			mCamera=Camera.open();
		}
	}
	@Override
	public void onPause() {
		super.onPause();
		if(mCamera!=null){
			mCamera.release();
			mCamera=null;
		}
	}
	
	
	

}
