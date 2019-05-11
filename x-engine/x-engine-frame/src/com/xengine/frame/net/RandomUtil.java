package com.xengine.frame.net;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

	public static Random getRandom(){
		return ThreadLocalRandom.current();
	}

	/**
	 * 
	 * @param middle
	 *            中间值
	 * @param min
	 *            最小值，可包含
	 * @param max
	 *            最大值，可包含
	 * @param period
	 *            正态跨度
	 * @return
	 */
	public static int gaussianRandom(int middle, int min, int max, int period) {

		double g = getRandom().nextGaussian();
		 
		int res = middle;
		res = (int) Math.round(middle + g * period);

		if (res < min) {
			res = min;
		} else if (res > max) {
			res = max;
		}
		return res;
	}
	
	/**
	 * 随机范围值
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomInt(int min,int max){
		int abs = Math.abs(max - min) + 1;
		return (min + getRandom().nextInt(abs));
	}
	public static long getRandomLong(long min,long max){
		return Math.round((Math.random()*(max-min))+min);
	}
	
	public static void main(String[] args) {
//		System.out.println(count);
		for(int i = 0 ;i <= 10 ;i++) {
			System.out.println(getRandomInt(0, 1));
		}
	}
	
	
}
