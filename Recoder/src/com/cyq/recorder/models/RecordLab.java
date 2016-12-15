package com.cyq.recorder.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONException;

import com.cyq.recorder.json.RecordIntentJSONSerializer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class RecordLab {
	private static RecordLab sRecordLab;
	private Context mAppContext;
	private ArrayList<Record> mRecords;
	
	private static final String TAG="RecordLab";
	private static final String FILENAME="record.json";
	private RecordIntentJSONSerializer mSerializer;
	
	public boolean saveRecords(){
		try {
           Log.e(TAG,mRecords.toString());
			mSerializer.saveRecords(mRecords);
			Log.e("stream",Environment.getExternalStorageDirectory().getAbsolutePath() );
			Log.e(TAG,"success saving records:");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG,"error saving records:",e);
			return false;
		} 
	}
	
	public void addRecord(Record c){
		mRecords.add(c);
	}
	public void deleteRecord(Record c){
		mRecords.remove(c);
	}

	public RecordLab(Context appContext) {
		mAppContext = appContext;
		//mRecords=new ArrayList<Record>();
		mSerializer=new RecordIntentJSONSerializer(mAppContext, FILENAME);
		try {
			mRecords=mSerializer.loadRecords();
			Log.e(TAG, "success loading");
			
		} catch (Exception e) {
			mRecords=new ArrayList<Record>();
			Log.e(TAG, "erro loading");
		} 
	}
	public ArrayList<Record> getRecords() {
		return mRecords;
	}
	public Record getRecord(UUID id) {
		for(Record c: mRecords){
			if(c.getId().equals(id))
				return c;
		}
		return null;
	}
	public static RecordLab get(Context c){
		if(sRecordLab==null){
			sRecordLab=new RecordLab(c.getApplicationContext());
		}
		return sRecordLab;
	}

}
