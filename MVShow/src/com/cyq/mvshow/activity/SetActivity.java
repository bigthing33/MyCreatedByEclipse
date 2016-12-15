package com.cyq.mvshow.activity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.cyq.mvshow.MyApplication;
import com.cyq.mvshow.R;
import com.cyq.mvshow.model.Set;
import com.cyq.mvshow.utils.SharedPreferencesManager;
//物理伤害
/**
 * 天雷斩
 * 物攻*1.2-物防
 * 降低对手5%灵力，降低自己5%防御
 */
/**
 * 乱神斩
 * 物攻*1.0-物防
 * 降低对手10%物攻，降低自己10%灵力
 */
/**
 * 怒雷
 * 物攻*1.0-物防*0.5
 */
/**
 * 天诛地灭
 *增强攻击力一回，虚弱两回
 *物攻*1.5-物防
 */
/**
 * 雷霆万钧
 *（物攻*1.2-物防）秒系数
 */
//法术伤害
/**
 * 急风雷
 *技能等级*2.5+双方灵力差值+物攻*30
 */
/*
 * 天火
 * 技能等级*2.0+方法灵力差+物攻*30
 */
//封系，一定几率
/**
 * 百万神兵
 *单封技能，封物理
 *同水平封住4回合
 */
/**
 * 镇妖 
 * 单封技能，封法术，
 * 同水平4回合
 */
/**
 * 错乱 
 * 一定几率，一定回合内使对方攻击出现错乱
 */
//固定伤害的两个技能
/**
 * 五雷轰顶
 *减少25%气血，最高只会减少技能等级*50，失败只减少5%
 */
/**
 * 契约束缚，
 * 一定几率令对手一定回合内不敢轻易使用法术和物理否则减少气血
 * 同水平4回合
 */
//辅助技能
/**
 * 天神护体
 * 战斗中临时提高自己的法术防御力，只能对自己使用
 *增加自己的灵力，每级大概1.25，持续4-6回合
 */
/***
 * 天神护法
 *保护指定队友
 *很掉血
 */
/**
 * 知己知彼
 * 对目标使用可以得知其气血，魔法，防御，速度情况技能等级小于对方人物等级20级则无法使用。
 *
 */
public class SetActivity extends BaseActivity {
	private RadioGroup mPlayTimeSetRadioGroup;
	private RadioGroup mPlayTypeSetRadioGroup;
	private RadioButton manuallyPlay, autoPlay5, autoPlay10, autoPlay15;
	private RadioButton innerPlay, outPlay;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		/*
		 * 轮播时间单选框的初始化
		 */
		mPlayTimeSetRadioGroup = (RadioGroup) findViewById(R.id.mPlayTimeSetRadioGroup);
		manuallyPlay = (RadioButton) findViewById(R.id.manuallyPlay);
		autoPlay5 = (RadioButton) findViewById(R.id.autoPlay5);
		autoPlay10 = (RadioButton) findViewById(R.id.autoPlay10);
		autoPlay15 = (RadioButton) findViewById(R.id.autoPlay15);
		mPlayTimeSetRadioGroup.setOnCheckedChangeListener(playTimeSetRadiogpchange);
		if (MyApplication.set.getPlayTime()==0) {
			manuallyPlay.setChecked(true);
		}else if (MyApplication.set.getPlayTime()==5) {
			autoPlay5.setChecked(true);
		}else if (MyApplication.set.getPlayTime()==10) {
			autoPlay10.setChecked(true);
		} else if (MyApplication.set.getPlayTime()==15) {
			autoPlay15.setChecked(true);
		}
		/*
		 * 轮播类型单选框的初始化
		 */
		mPlayTypeSetRadioGroup = (RadioGroup) findViewById(R.id.mPlayTypeSetRadioGroup);
		innerPlay = (RadioButton) findViewById(R.id.innerPlay);
		outPlay = (RadioButton) findViewById(R.id.outPlay);
		mPlayTypeSetRadioGroup.setOnCheckedChangeListener(playTypeSetRadiogpchange);
		if (MyApplication.set.getPlayType().equals("相册内轮播")) {
			innerPlay.setChecked(true);
		}else{
			outPlay.setChecked(true);
			
		}
	}

	private RadioGroup.OnCheckedChangeListener playTimeSetRadiogpchange = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId==manuallyPlay.getId()) {
				MyApplication.set.setPlayTime(0);
			}else if (checkedId==autoPlay5.getId()) {
				MyApplication.set.setPlayTime(5);
			}else if (checkedId==autoPlay10.getId()) {
				MyApplication.set.setPlayTime(10);
			}else if (checkedId==autoPlay15.getId()) {
				MyApplication.set.setPlayTime(15);
			}
			SharedPreferencesManager.saveSet(MyApplication.set);
			
		}
	};
	private RadioGroup.OnCheckedChangeListener playTypeSetRadiogpchange = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId==innerPlay.getId()) {
				MyApplication.set.setPlayType("相册内轮播");
			}else  {
				MyApplication.set.setPlayType("相册间轮播");
			}
			SharedPreferencesManager.saveSet(MyApplication.set);
		}
	};

}
