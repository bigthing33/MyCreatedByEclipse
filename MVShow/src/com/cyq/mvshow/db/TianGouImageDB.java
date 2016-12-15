package com.cyq.mvshow.db;

import java.util.ArrayList;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.listener.GetGallryDetailsListener;
import com.cyq.mvshow.model.Gallery;
import com.cyq.mvshow.model.GallryDetailsRespone;
import com.cyq.mvshow.model.Image;
import com.cyq.mvshow.model.Picture;
import com.cyq.mvshow.utils.LogUtil;

public class TianGouImageDB {
	private static final String TAG = "TianGouImageDB";
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
	 * 根据id将相册相册存储到数据库中,包括相册中的图片
	 */
	public void saveGallryDetailsRespone(int gallryId){
		MyApplication.imageFetcherTianGouImp.getImageDetailsByID(gallryId, new GetGallryDetailsListener() {
			
			@Override
			public void success(GallryDetailsRespone gallryDetailsRespone,boolean isForHead) {
				saveGallryDetailsRespone(gallryDetailsRespone);
			}
			
			@Override
			public void erro(String erroString) {
				// TODO Auto-generated method stub
				
			}
		},false);
	}
	/**
	 * 将相册存储到数据库中,包括相册中的图片
	 */
	public void saveGallryDetailsRespone(GallryDetailsRespone gallryDetailsRespone){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		if(gallryDetailsRespone!=null){
			Cursor cursor =db.query(DatabaseHelper.GALLRY_TABLENAME, null, "gallryId = ? ", new String[]{String.valueOf(gallryDetailsRespone.getId())}, null, null, "id"+" desc");
			if(cursor.moveToFirst()){
				//如果存在就删除掉
				do{
					db.delete(DatabaseHelper.GALLRY_TABLENAME, "gallryId = ? ", new String[]{String.valueOf(gallryDetailsRespone.getId())});
				}while (cursor.moveToNext());
			}
				ContentValues values=new ContentValues();
				values.put("gallryId", gallryDetailsRespone.getId());
				values.put("galleryclass", gallryDetailsRespone.getGalleryclass());
				values.put("title", gallryDetailsRespone.getTitle());
				values.put("img", gallryDetailsRespone.getImg());
				values.put("count", gallryDetailsRespone.getCount());
				values.put("rcount", gallryDetailsRespone.getRcount());
				values.put("fcount", gallryDetailsRespone.getFcount());
				values.put("size", gallryDetailsRespone.getSize());
				values.put("time", System.currentTimeMillis()+"");
				db.insert(DatabaseHelper.GALLRY_TABLENAME, null, values);
				LogUtil.i(TAG, "相册存储到数据库中,相册id:"+gallryDetailsRespone.getId());
		}
		db.close();
		//保存图片
		if (gallryDetailsRespone.getList()!=null&&gallryDetailsRespone.getList().size()>0) {
			for (Picture picture : gallryDetailsRespone.getList()) {
				savePicture(picture);
			}
		}
	}
	/**
	 * 加载所有收藏的相册,包括图片
	 * @return
	 */
	public ArrayList<GallryDetailsRespone> loadGallryDetailsRespone(){
		ArrayList<GallryDetailsRespone> galleries=new ArrayList<GallryDetailsRespone>();
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.GALLRY_TABLENAME, null, null, null, null, null, "time"+" desc");
		if(cursor.moveToFirst()){
			do {
				GallryDetailsRespone gallryDetailsRespone=new GallryDetailsRespone();
				gallryDetailsRespone.setId(cursor.getInt(cursor.getColumnIndex("gallryId")));
				gallryDetailsRespone.setGalleryclass(cursor.getInt(cursor.getColumnIndex("galleryclass")));
				gallryDetailsRespone.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				gallryDetailsRespone.setImg(cursor.getString(cursor.getColumnIndex("img")));
				gallryDetailsRespone.setCount(cursor.getInt(cursor.getColumnIndex("count")));
				gallryDetailsRespone.setFcount(cursor.getInt(cursor.getColumnIndex("rcount")));
				gallryDetailsRespone.setRcount(cursor.getInt(cursor.getColumnIndex("fcount")));
				gallryDetailsRespone.setSize(cursor.getInt(cursor.getColumnIndex("size")));
				gallryDetailsRespone.setList(loadPictures(cursor.getColumnIndex("gallryId"),db));
				galleries.add(gallryDetailsRespone);
			} while (cursor.moveToNext());
		}
		if (cursor!=null) {
			cursor.close();
		}
		db.close();
		return galleries;
	}
	/**
	 * 根据相册Id加载收藏的相册,包括图片
	 * @return
	 */
	public GallryDetailsRespone loadGallryDetailsRespone(int gallryId){
		GallryDetailsRespone gallryDetailsRespone = null;
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.GALLRY_TABLENAME, null, "gallryId = ? ", new String[]{String.valueOf(gallryId)}, null, null, "time"+" desc");
		if(cursor.moveToFirst()){
			    GallryDetailsRespone gallryDetails=new GallryDetailsRespone();
			    gallryDetails.setId(cursor.getInt(cursor.getColumnIndex("gallryId")));
			    gallryDetails.setGalleryclass(cursor.getInt(cursor.getColumnIndex("galleryclass")));
			    gallryDetails.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			    gallryDetails.setImg(cursor.getString(cursor.getColumnIndex("img")));
			    gallryDetails.setCount(cursor.getInt(cursor.getColumnIndex("count")));
			    gallryDetails.setFcount(cursor.getInt(cursor.getColumnIndex("rcount")));
			    gallryDetails.setRcount(cursor.getInt(cursor.getColumnIndex("fcount")));
			    gallryDetails.setSize(cursor.getInt(cursor.getColumnIndex("size")));
			    gallryDetails.setList(loadPictures(gallryId,db));
			    gallryDetailsRespone=gallryDetails;
		}
		if (cursor!=null) {
			cursor.close();
		}
		db.close();
		return gallryDetailsRespone;
	}
//--------------------------------------------------------以下是单个Gallery---------------------------------------------
	
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
				LogUtil.i(TAG, "相册存储到数据库中,相册id:"+gallery.getId());
		}
		db.close();
	}
	/**
	 * 将相册从数据库中删除
	 */
	public void deleteGallery(Gallery gallery){
		deleteGallery(gallery.getId());
	}
	/**
	 * 根据ID将相册从数据库中删除
	 */
	public void deleteGallery(int galleryId){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		if(galleryId!=0){
			Cursor cursor =db.query(DatabaseHelper.GALLRY_TABLENAME, null, "gallryId = ? ", new String[]{String.valueOf(galleryId)}, null, null, "id"+" desc");
			if (cursor.moveToFirst()) {
				// 如果存在就删除掉
				db.delete(DatabaseHelper.GALLRY_TABLENAME, "gallryId = ? ",new String[] { String.valueOf(galleryId) });
				LogUtil.i(TAG, "取消了收藏,相册id:"+galleryId);
			} else {
				LogUtil.w(TAG, "数据库没有该相册,相册id:"+galleryId);
			}
		}
		db.close();
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
	 * 判断数据库是否包含传入的相册
	 * @param galleryId 相册ID
	 */
	public Boolean isContainGallery(int galleryId){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		if(galleryId!=0){
			Cursor cursor =db.query(DatabaseHelper.GALLRY_TABLENAME, null, "gallryId = ? ", new String[]{String.valueOf(galleryId)}, null, null, "id"+" desc");
			if (cursor.moveToFirst()) {
				// 如果存
				return true;
			}
			return false;
		}
		db.close();
		return false;
	}
//	----------------------------------------------------------图片---------------------------------------------------------------
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
				LogUtil.i(TAG, "图片删除成功,图片id:"+picture.getId());
			} else {
				LogUtil.w(TAG, "数据库没有该图片,图片id:"+picture.getId());
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
	 * 根据相册ID加载收藏的图片
	 * @return
	 */
	public ArrayList<Picture> loadPictures(int galleryId,SQLiteDatabase db){
		ArrayList<Picture> pictures=new ArrayList<Picture>();
		Cursor cursor = db.query(DatabaseHelper.PICTURE_TABLENAME, null, "gallery = ? ", new String[]{String.valueOf(galleryId)}, null, null, "time"+" desc");
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
		return pictures;
	}
	/*
	 * --------------------------------分割线，上面是天狗开放阅图的dao操作------------------------
	 */
	
	
	
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

}
