package com.cyq.recorder.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo {
	private static final String JOSN_FILENAME="filename";
	private String mFilename;
	public Photo(String filename) {
		super();
		mFilename = filename;
	}
	public Photo(JSONObject json) throws JSONException {
		super();
		mFilename = json.getString(JOSN_FILENAME);
	}
	public Photo() {
		// TODO Auto-generated constructor stub
	}
	public JSONObject toJSON() throws JSONException{
		JSONObject json=new JSONObject();
		json.put(JOSN_FILENAME, mFilename);
		return json;
	}
	public String getFilename() {
		return mFilename;
	}
	public void setFilename(String filename) {
		mFilename = filename;
	}
	

}
