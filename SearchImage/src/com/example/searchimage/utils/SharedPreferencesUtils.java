package com.example.searchimage.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {

	private static SharedPreferences sharedPreferences;
	private static SharedPreferencesUtils instance;
	private static final String SETTING = "settings";

	public SharedPreferencesUtils(Context context) {
		sharedPreferences = context.getSharedPreferences(SETTING,
				Context.MODE_PRIVATE);
	}

	public synchronized static SharedPreferencesUtils getInstanse(
			Context context) {
		if (instance == null) {
			instance = new SharedPreferencesUtils(
					context.getApplicationContext());
		}
		return instance;
	}

	public String getContents(String key, String value) {
		String cityCurrent = sharedPreferences.getString(key, value);
		return cityCurrent;
	}

	public int getContents(String key, int value) {
		int cityCurrent = sharedPreferences.getInt(key, value);
		return cityCurrent;
	}

	public void setContents(String key, String value) {
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void setContents(String key, int value) {
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public void setBoolean(String key, boolean value) {
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getBoolean(String key, boolean value) {
		boolean truth = sharedPreferences.getBoolean(key, value);
		return truth;
	}
	
	
	
}
