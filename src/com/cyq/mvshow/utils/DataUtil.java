package com.cyq.mvshow.utils;

import java.util.Random;

import android.R.integer;

/**
 * 数据工具类
 * @author cyq
 *
 */
public class DataUtil {
	// 随机生成一个0到1000的int类型的数
	public static int getRandomInt(int max) {
		Random random=new Random();
		int randomNum=0;
		while (randomNum==0) {
			randomNum=random.nextInt(max);
			
		}
		return randomNum;

	}
}
