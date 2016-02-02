package com.example.searchimage.utils;

import com.example.searchimage.db.TianGouImageDB;
import com.example.searchimage.listener.Listener;
import com.example.searchimage.listener.ListenerImp;
import com.example.searchimage.model.Gallery;
import com.example.searchimage.model.Picture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogUtils {
	/**
	 * 显示是否收藏图片的对话框
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
	/**
	 * 显示是否收藏相册的对话框
	 * @param activity
	 * @param picture 
	 */
	public static  void showIsSaveGalleryDialog(final Activity activity, final Gallery gallery) {
		final AlertDialog dialog = new AlertDialog.Builder(activity)
		.setTitle("是否收藏该相册？")
		.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				TianGouImageDB.getInstance(activity).saveGallry(gallery);
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
	/**
	 * 显示删除图片的对话框
	 * @param activity
	 * @param picture
	 * @param listenerImp
	 */
	public static  void showIsDeletePictureDialog(final Activity activity, final Picture picture, final ListenerImp listenerImp) {
		final AlertDialog dialog = new AlertDialog.Builder(activity)
		.setTitle("是否收藏该图片？")
		.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				TianGouImageDB.getInstance(activity).deletePicture(picture);
				listenerImp.success("成功");
			}
		})
		.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				listenerImp.erro("");
			}
		})
		.create();
		dialog.show();
	}
	/**
	 * 显示删除相册的对话框
	 * @param activity
	 * @param picture
	 * @param listenerImp
	 */
	public static  void showIsDeleteGalleryDialog(final Activity activity, final Gallery gallery, final ListenerImp listenerImp) {
		final AlertDialog dialog = new AlertDialog.Builder(activity)
		.setTitle("是否收藏该图片？")
		.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				TianGouImageDB.getInstance(activity).deleteGallery(gallery);
				listenerImp.success("成功");
			}
		})
		.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				listenerImp.erro("");
			}
		})
		.create();
		dialog.show();
	}

}
