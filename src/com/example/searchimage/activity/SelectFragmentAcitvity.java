package com.example.searchimage.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.searchimage.R;
import com.example.searchimage.utils.LogUtil;

public abstract class SelectFragmentAcitvity extends FragmentActivity {
	private static final String TAG = SelectFragmentAcitvity.class.getSimpleName();

	protected abstract HashMap<String,Fragment> createFragments();
	private HashMap<String,Fragment> fragmentsHashMap;
	private FragmentManager fm;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_selectfragment);
		fm = getSupportFragmentManager();
		fragmentsHashMap=new HashMap<String, Fragment>();
		addFragments(createFragments());
	}
	protected void addFragment(String key,Fragment value){
		FragmentTransaction transaction = fm.beginTransaction();
		if (value==null) {
			return;
		}
		if (!fragmentsHashMap.containsKey(key)) {
			fragmentsHashMap.put(key, value);
			transaction.add(R.id.fragmentContainer, value).commit();
		}
	}
	protected void hideFragment(String key){
		FragmentTransaction transaction = fm.beginTransaction();
		if (key==null) {
			return;
		}
		if (fragmentsHashMap.containsKey(key)) {
			fragmentsHashMap.remove(key);
			transaction.remove(fragmentsHashMap.get(key)).commit();;
		}
	}
	protected void hiadAllFragment(){
		FragmentTransaction transaction = fm.beginTransaction();
		Iterator<Entry<String, Fragment>> iter=fragmentsHashMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Fragment> entry=(Map.Entry<String, Fragment>) iter.next();
			transaction.hide(entry.getValue());
		}
		transaction.commit();
	}
	protected void addFragments(HashMap<String,Fragment> fragments){
		FragmentTransaction transaction = fm.beginTransaction();
		Iterator<Entry<String, Fragment>> iter=fragments.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Fragment> entry=(Map.Entry<String, Fragment>) iter.next();
			fragmentsHashMap.put(entry.getKey(), entry.getValue());
			transaction.add(R.id.fragmentContainer, entry.getValue());
		}
		transaction.commit();
		
	}
	protected void  selectFragment(String key){
		FragmentTransaction transaction = fm.beginTransaction();
		if (fragmentsHashMap.get(key)==null) {
			LogUtil.e(TAG, "没有这样的fragment");
			return;
		}
		hiadAllFragment();
		transaction.show(fragmentsHashMap.get(key));
		transaction.commit();
	}

}
