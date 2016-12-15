package com.example.searchimage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "searchimage.db";
	public static final String IMAGE_TABLENAME = "Image";
	public static final String PICTURE_TABLENAME = "picture";
	public static final String GALLRY_TABLENAME = "gallery";
	private static final int VERSION = 1;
	private static DatabaseHelper mDatabaseHelper = null;// 静态的引用

	private DatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);// <包>/databases/
	}

	/**
	 * 获得这个类的对象,单例模式确保只有一个对象会被产生
	 */
	public static DatabaseHelper getInstance(Context context) {
		if (mDatabaseHelper == null) {
			mDatabaseHelper = new DatabaseHelper(context);
		}
		return mDatabaseHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);
	}

	private void createTables(SQLiteDatabase db) {
		db.execSQL("create table if not exists Searchhistory ("+ "id integer primary key autoincrement ," + "name text,"+ "sort integer)");
		db.execSQL("create table if not exists "+IMAGE_TABLENAME+"(id integer primary key autoincrement, Key text, ObjUrl text,FromUrl text,Desc text,Pictype text,searchTime text,searchTag text,savePath text)");
		db.execSQL("create table if not exists "+PICTURE_TABLENAME+"(id integer primary key autoincrement, gallery integer, pictureId integer,src text,time text)");
		db.execSQL("create table if not exists "+GALLRY_TABLENAME+"(id integer primary key autoincrement, gallryId integer,galleryclass integer,title text,img text,count integer,rcount integer,fcount integer,size integer,time text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		createTables(db);
	}

}
