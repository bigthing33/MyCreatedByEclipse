package com.example.searchimage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.searchimage.model.Image;

public class ImageDB {
	private static ImageDB imageDB;
	private DatabaseHelper dbHeleper;
	/**
	 * 将构造方法私有化
	 */
	private ImageDB(Context context) {
		dbHeleper= DatabaseHelper.getInstance(context);;
	}
	
	/**
	 * 获取HistoryDB实例
	 */
	public synchronized static ImageDB getInstance(Context context ){
		if (imageDB==null) {
			imageDB=new ImageDB(context);
		}
		return imageDB;
	}
	
	/**
	 * 将图片存储到数据库中
	 */
	public void saveImage(Image image){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		if(image!=null){
			Cursor cursor =db.query(DatabaseHelper.IMAGE_TABLENAME, null, "Key = ? And ObjUrl = ?", new String[]{String.valueOf(image.getKey()),image.getObjUrl()}, null, null, "id"+" desc");
			if(cursor.moveToFirst()){
				//如果存在就删除掉
//				do{
//					db.delete("Searchhistory", "sort = ? And name = ?", new String[]{String.valueOf(image.getSort()),image.getName()});
//				}while (cursor.moveToNext());
			
			}else{
				ContentValues values=new ContentValues();
				values.put("Desc", image.getDesc());
				values.put("FromUrl", image.getFromUrl());
				values.put("Key", image.getKey());
				values.put("ObjUrl", image.getObjUrl());
				values.put("Pictype", image.getPictype());
				values.put("searchTag", image.getSearchTag());
				values.put("searchTime", image.getSearchTime());
				values.put("savePath", image.getSavePath());
				db.insert(DatabaseHelper.IMAGE_TABLENAME, null, values);
				
			}
		}
		db.close();
	}
	/**
	 * 根据key和ObjUrl的值从数据库中取出图片对象
	 * @return
	 */
	public Image loadImage(Image image){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.IMAGE_TABLENAME, null, "Key = ? And ObjUrl = ?", new String[]{image.getKey(),image.getObjUrl()}, null, null, "id"+" desc");
		if(cursor.moveToFirst()){
			//注释的原因是我确定了只会有一张图片
//			do{
//				image.setKey(cursor.getString(cursor.getColumnIndex("Key")));
//			}while (cursor.moveToNext());
//			image.setDesc(cursor.getString(cursor.getColumnIndex("Desc")));
//			image.setFromUrl(cursor.getString(cursor.getColumnIndex("FromUrl")));
//			image.setKey(cursor.getString(cursor.getColumnIndex("Key")));
//			image.setObjUrl(cursor.getString(cursor.getColumnIndex("ObjUrl")));
//			image.setPictype(cursor.getString(cursor.getColumnIndex("Pictype")));
//			image.setSearchTag(cursor.getString(cursor.getColumnIndex("searchTag")));
//			image.setSearchTime(cursor.getString(cursor.getColumnIndex("searchTime")));
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

}
