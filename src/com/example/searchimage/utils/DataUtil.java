package com.example.searchimage.utils;

import java.util.Random;

/**
 * 数据工具类
 * @author cyq
 *
 */
public class DataUtil {
	// 随机生成一个0到1000的int类型的数
	public static int getRandomInt() {
		Random random=new Random();
		return random.nextInt(1000);

	}
}
