package com.example.searchimage.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.searchimage.MyApplication;
import com.example.searchimage.model.Gallery;
import com.example.searchimage.model.Image;
import com.example.searchimage.model.Picture;

public class TianGouImageDB {
	private static TianGouImageDB imageDB;
	private DatabaseHelper dbHeleper;
	/**
	 * 将构造方法私有化
	 */
	private TianGouImageDB(Context context) {
		dbHeleper= DatabaseHelper.getInstance(context);;
	}
	
	/**
	 * 获取HistoryDB实例
	 */
	public synchronized static TianGouImageDB getInstance(Context context ){
		if (imageDB==null) {
			imageDB=new TianGouImageDB(context);
		}
		return imageDB;
	}
	
	/**
	 * 将图片存储到数据库中
	 */
	public void savePicture(Picture picture){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		if(picture!=null){
			Cursor cursor =db.query(DatabaseHelper.PICTURE_TABLENAME, null, "src = ? ", new String[]{String.valueOf(picture.getSrc())}, null, null, "id"+" desc");
			if(cursor.moveToFirst()){
				//如果存在就删除掉
				do{
					db.delete(DatabaseHelper.PICTURE_TABLENAME, "src = ? ", new String[]{String.valueOf(picture.getSrc())});
				}while (cursor.moveToNext());
			
			}
				ContentValues values=new ContentValues();
				values.put("gallery", picture.getGallery());
				values.put("pictureId", picture.getId());
				values.put("src", picture.getSrc());
				values.put("time", System.currentTimeMillis()+"");
				db.insert(DatabaseHelper.PICTURE_TABLENAME, null, values);
				
		}
		db.close();
	}
	/**
	 * 将相册存储到数据库中
	 */
	public void saveGallry(Gallery gallery){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		if(gallery!=null){
			Cursor cursor =db.query(DatabaseHelper.GALLRY_TABLENAME, null, "gallryId = ? ", new String[]{String.valueOf(gallery.getId())}, null, null, "id"+" desc");
			if(cursor.moveToFirst()){
				//如果存在就删除掉
				do{
					db.delete(DatabaseHelper.GALLRY_TABLENAME, "gallryId = ? ", new String[]{String.valueOf(gallery.getId())});
				}while (cursor.moveToNext());
			}
				ContentValues values=new ContentValues();
				values.put("gallryId", gallery.getId());
				values.put("galleryclass", gallery.getGalleryclass());
				values.put("title", gallery.getTitle());
				values.put("img", gallery.getImg());
				values.put("count", gallery.getCount());
				values.put("rcount", gallery.getRcount());
				values.put("fcount", gallery.getFcount());
				values.put("size", gallery.getSize());
				values.put("time", System.currentTimeMillis()+"");
				db.insert(DatabaseHelper.GALLRY_TABLENAME, null, values);
		}
		db.close();
	}
	/**
	 * 将图片从数据库中删除
	 */
	public void deletePicture(Picture picture){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		if(picture!=null){
			Cursor cursor =db.query(DatabaseHelper.PICTURE_TABLENAME, null, "src = ? ", new String[]{String.valueOf(picture.getSrc())}, null, null, "id"+" desc");
			if (cursor.moveToFirst()) {
				// 如果存在就删除掉
				do {
					db.delete(DatabaseHelper.PICTURE_TABLENAME, "src = ? ",new String[] { String.valueOf(picture.getSrc()) });
				} while (cursor.moveToNext());
				Toast.makeText(MyApplication.getcContext(), "删除成功",Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MyApplication.getcContext(), "无效图片",Toast.LENGTH_SHORT).show();
			}
		}
		db.close();
	}
	/**
	 * 将相册从数据库中删除
	 */
	public void deleteGallery(Gallery gallery){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		if(gallery!=null){
			Cursor cursor =db.query(DatabaseHelper.GALLRY_TABLENAME, null, "gallryId = ? ", new String[]{String.valueOf(gallery.getId())}, null, null, "id"+" desc");
			if (cursor.moveToFirst()) {
				// 如果存在就删除掉
				do {
					db.delete(DatabaseHelper.GALLRY_TABLENAME, "gallryId = ? ",new String[] { String.valueOf(gallery.getId()) });
				} while (cursor.moveToNext());
				Toast.makeText(MyApplication.getcContext(), "删除成功",Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MyApplication.getcContext(), "无效相册",Toast.LENGTH_SHORT).show();
			}
		}
		db.close();
	}
	
	/**
	 * 加载所有收藏的图片
	 * @return
	 */
	public ArrayList<Picture> loadPictures(){
		ArrayList<Picture> pictures=new ArrayList<Picture>();
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.PICTURE_TABLENAME, null, null, null, null, null, "time"+" desc");
		if(cursor.moveToFirst()){
			do {
				Picture picture=new Picture();
				picture.setGallery(cursor.getInt(cursor.getColumnIndex("gallery")));
				picture.setId(cursor.getInt(cursor.getColumnIndex("pictureId")));
				picture.setSrc(cursor.getString(cursor.getColumnIndex("src")));
				pictures.add(picture);
			} while (cursor.moveToNext());
		}
		if (cursor!=null) {
			cursor.close();
		}
		db.close();
		return pictures;
	}
	
	/**
	 * 加载所有收藏的相册
	 * @return
	 */
	public ArrayList<Gallery> loadGalleries(){
		ArrayList<Gallery> galleries=new ArrayList<Gallery>();
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.GALLRY_TABLENAME, null, null, null, null, null, "time"+" desc");
		if(cursor.moveToFirst()){
			do {
				Gallery gallery=new Gallery();
				gallery.setId(cursor.getInt(cursor.getColumnIndex("gallryId")));
				gallery.setGalleryclass(cursor.getInt(cursor.getColumnIndex("galleryclass")));
				gallery.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				gallery.setImg(cursor.getString(cursor.getColumnIndex("img")));
				gallery.setCount(cursor.getInt(cursor.getColumnIndex("count")));
				gallery.setFcount(cursor.getInt(cursor.getColumnIndex("rcount")));
				gallery.setRcount(cursor.getInt(cursor.getColumnIndex("fcount")));
				gallery.setSize(cursor.getInt(cursor.getColumnIndex("size")));
				galleries.add(gallery);
			} while (cursor.moveToNext());
		}
		if (cursor!=null) {
			cursor.close();
		}
		db.close();
		return galleries;
	}
	/**
	 * 根据key和ObjUrl的值从数据库中取出图片对象(只取第一个)
	 * @return
	 */
	public Image loadImage(Image image){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.IMAGE_TABLENAME, null, "Key = ? And ObjUrl = ?", new String[]{image.getKey(),image.getObjUrl()}, null, null, "id"+" desc");
		if(cursor.moveToFirst()){
			image.setSavePath(cursor.getString(cursor.getColumnIndex("savePath")));
		}
		if (cursor!=null) {
			cursor.close();
		}
		db.close();
		return image;
	}
	/**
	 * 根据url获取数据库中的image的path
	 * @param image
	 * @return
	 */
	public String loadImageByUrl(String url){
		String path = null;
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.IMAGE_TABLENAME, null, "ObjUrl = ? ", new String[]{url}, null, null, "id"+" desc");
		if(cursor.moveToFirst()){

			path=cursor.getString(cursor.getColumnIndex("savePath"));
		}
		if (cursor!=null) {
			cursor.close();
		}
		db.close();
		
		return path;
	}
	/**
	 * 删除历史记录 TODO
	 * @param sort 为1时删除视频搜索记录，为2时删除鉴客搜索记录
	 */
	public void deleteSearchHistory(int sort){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		db.delete("Searchhistory", "sort = ?", new String[]{String.valueOf(sort)});
		db.close();
	}
	/*
	 * --------------------------------分割线，下面是天狗开放阅图的dao操作------------------------
	 */
	
	

}
