package com.cyq.mvshow.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cyq.mvshow.model.SearchHistory;



public class HistoryDB {
	private static HistoryDB historyDB;
	private DatabaseHelper dbHeleper;
	/**
	 * 将构造方法私有化
	 */
	private HistoryDB(Context context) {
		dbHeleper= DatabaseHelper.getInstance(context);;
	}
	
	/**
	 * 获取HistoryDB实例
	 */
	public synchronized static HistoryDB getInstance(Context context ){
		if (historyDB==null) {
			historyDB=new HistoryDB(context);
		}
		return historyDB;
	}
	
	/**
	 * 将搜索历史实例存储到数据库中
	 */
	public void saveHistory(SearchHistory searchHistory){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		if(searchHistory!=null){
			Cursor cursor =db.query("Searchhistory", null, "sort = ? And name = ?", new String[]{String.valueOf(searchHistory.getSort()),searchHistory.getName()}, null, null, "id"+" desc");
			if(cursor.moveToFirst()){
				do{
					db.delete("Searchhistory", "sort = ? And name = ?", new String[]{String.valueOf(searchHistory.getSort()),searchHistory.getName()});
				}while (cursor.moveToNext());
			
			}
			ContentValues values=new ContentValues();
			values.put("name", searchHistory.getName());
			values.put("sort", searchHistory.getSort());
			db.insert("Searchhistory", null, values);
		}
		db.close();
	}
	/**
	 * 从数据库中取出搜索记录
	 * @param sort 为1时取出所有视频搜索记录，为2时取出所有鉴客搜索记录
	 * @return
	 */
	public List<SearchHistory> loadSearchHistory(int sort){
		List<SearchHistory> slist=new ArrayList<SearchHistory>();
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		Cursor cursor = db.query("Searchhistory", null, "sort = ?", new String[]{String.valueOf(sort)}, null, null, "id"+" desc");
		if(cursor.moveToFirst()){
			do{
				SearchHistory searchHistory=new SearchHistory();
				searchHistory.setName(cursor.getString(cursor.getColumnIndex("name")));
				slist.add(searchHistory);
			}while (cursor.moveToNext());
		}
		if (cursor!=null) {
			cursor.close();
		}
		db.close();
		return slist;
		
	}
	/**
	 * 删除历史记录
	 * @param sort 为1时删除视频搜索记录，为2时删除鉴客搜索记录
	 */
	public void deleteSearchHistory(int sort){
		SQLiteDatabase db = dbHeleper.getWritableDatabase();
		db.delete("Searchhistory", "sort = ?", new String[]{String.valueOf(sort)});
		db.close();
	}

}
