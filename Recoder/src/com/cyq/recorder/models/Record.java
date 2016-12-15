package com.cyq.recorder.models;


import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.cyq.recorder.utils.DataChange;

public class Record {
	private UUID mId;
	private String mTitle;
	private String mDate;
	private boolean mSolved;
	private Photo mPhoto;
	private String mLinkman;
	
	private static final String JSON_PHOTO="photo";
	private static final String JSON_ID="id";
	private static final String JSON_TITLe="title";
	private static final String JSON_SOLVED="solved";
	private static final String JSON_DATE="date";
	private static final String JSON_LINKMAN="linkman";
	public JSONObject toJSON() throws JSONException{
		JSONObject json=new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLe,mTitle.toString());
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_DATE, mDate.toString());
		json.put(JSON_LINKMAN, mLinkman);
		if(mPhoto!=null){
			json.putOpt(JSON_PHOTO, mPhoto.toJSON());
		}
		if(json.has(JSON_LINKMAN)){
			mLinkman=json.getString(JSON_LINKMAN);
		}
		return json;
	}

	public Record(JSONObject json) throws JSONException{
		mId=UUID.fromString(json.getString(JSON_ID));
		if(json.has(JSON_TITLe)){
			mTitle=json.getString(JSON_TITLe);
		}
		mSolved=json.getBoolean(JSON_SOLVED);
		mDate=json.getString(JSON_DATE);
		if(json.has(JSON_PHOTO)){
				mPhoto=new Photo(json.getJSONObject(JSON_PHOTO));
		}
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mTitle;
	}
	public Record() {
		mId =UUID.randomUUID();
		mDate = DataChange.dateToStrLong(new Date());
	}
	


	public String getDate() {
		return mDate;
	}
	public void setDate(String date) {
		mDate = date;
	}
	public boolean isSolved() {
		return mSolved;
	}
	public void setSolved(boolean solved) {
		mSolved = solved;
	}
	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String title) {
		mTitle = title;
	}
	public UUID getId() {
		return mId;
	}
	



	public Photo getPhoto() {
		return mPhoto;
	}

	public void setPhoto(Photo photo) {
		mPhoto = photo;
	}

	public String getLinkman() {
		return mLinkman;
	}

	public void setLinkman(String linkman) {
		mLinkman = linkman;
	}
	
	
	

}
