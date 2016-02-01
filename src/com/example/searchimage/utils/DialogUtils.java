package com.example.searchimage.utils;

import com.example.searchimage.db.TianGouImageDB;
import com.example.searchimage.model.Picture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogUtils {
	/**
	 * 显示是否跳转到登录页面的Dialog
	 * @param activity
	 * @param picture 
	 */
	public static  void showIsSavePictureDialog(final Activity activity, final Picture picture) {
		final AlertDialog dialog = new AlertDialog.Builder(activity)
		.setTitle("是否收藏该图片？")
		.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				TianGouImageDB.getInstance(activity).savePicture(picture);
			}
		})
		.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		})
		.create();
		dialog.show();
	}

}
