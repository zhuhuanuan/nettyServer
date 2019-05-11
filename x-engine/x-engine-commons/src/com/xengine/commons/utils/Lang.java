package com.xengine.commons.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

/**
 * 常用的方法工具类
 * @author 朱华煖
 */
public final class Lang {

	private Lang() {

	}

	/**
	 * 判断一个对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return ((String) obj).trim().equals("");
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		return false;
	}

	/**
	 * 获取CPU核数
	 * 
	 * @return
	 */
	public static int getCpuCores() {
		return Runtime.getRuntime().availableProcessors();
	}

	/**
	 * 从[min,max)随机一个数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max) {
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	/**
	 * 生成异常信息
	 * 
	 * @param format
	 * @param args
	 * @return
	 */
	public static RuntimeException makeThrow(String format, Object... args) {
		return new RuntimeException(String.format(format, args));
	}

}
