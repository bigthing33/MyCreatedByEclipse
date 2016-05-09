package com.cyq.mvshow.activity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.cyq.mvshow.R;
import com.cyq.mvshow.model.Set;
import com.cyq.mvshow.utils.SharedPreferencesManager;

public class SetActivity extends BaseActivity {
	private RadioGroup mPlayTimeSetRadioGroup;
	private RadioGroup mPlayTypeSetRadioGroup;
	private RadioButton manuallyPlay, autoPlay5, autoPlay10, autoPlay15;
	private RadioButton innerPlay, outPlay;
	private Set set;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		set=SharedPreferencesManager.getSet();
		/*
		 * 轮播时间单选框的初始化
		 */
		mPlayTimeSetRadioGroup = (RadioGroup) findViewById(R.id.mPlayTimeSetRadioGroup);
		manuallyPlay = (RadioButton) findViewById(R.id.manuallyPlay);
		autoPlay5 = (RadioButton) findViewById(R.id.autoPlay5);
		autoPlay10 = (RadioButton) findViewById(R.id.autoPlay10);
		autoPlay15 = (RadioButton) findViewById(R.id.autoPlay15);
		mPlayTimeSetRadioGroup.setOnCheckedChangeListener(playTimeSetRadiogpchange);
		if (set.getPlayTime()==0) {
			manuallyPlay.setChecked(true);
		}else if (set.getPlayTime()==5) {
			autoPlay5.setChecked(true);
		}else if (set.getPlayTime()==10) {
			autoPlay10.setChecked(true);
		} else if (set.getPlayTime()==15) {
			autoPlay15.setChecked(true);
		}
		/*
		 * 轮播类型单选框的初始化
		 */
		mPlayTypeSetRadioGroup = (RadioGroup) findViewById(R.id.mPlayTypeSetRadioGroup);
		innerPlay = (RadioButton) findViewById(R.id.innerPlay);
		outPlay = (RadioButton) findViewById(R.id.outPlay);
		mPlayTypeSetRadioGroup.setOnCheckedChangeListener(playTypeSetRadiogpchange);
		if (set.getPlayType().equals("相册内轮播")) {
			innerPlay.setChecked(true);
		}else{
			outPlay.setChecked(true);
			
		}
	}

	private RadioGroup.OnCheckedChangeListener playTimeSetRadiogpchange = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId==manuallyPlay.getId()) {
				set.setPlayTime(0);
			}else if (checkedId==autoPlay5.getId()) {
				set.setPlayTime(5);
			}else if (checkedId==autoPlay10.getId()) {
				set.setPlayTime(10);
			}else if (checkedId==autoPlay15.getId()) {
				set.setPlayTime(15);
			}
			SharedPreferencesManager.saveSet(set);
			
		}
	};
	private RadioGroup.OnCheckedChangeListener playTypeSetRadiogpchange = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId==innerPlay.getId()) {
				set.setPlayType("相册内轮播");
			}else  {
				set.setPlayType("相册间轮播");
			}
			SharedPreferencesManager.saveSet(set);
		}
	};

}
