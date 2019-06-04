package com.xengine.frame.net.redis;

/**
 * 密码加密和解密工具
 * @author 朱华煖
 *
 */
public class PassWordTool {
	static String redisKey = "B3E1A8D5-0871-48CA-9C03-9A487A769206";
	static String mqKey = "FD18D57E-FB7D-4156-AEE4-161E4CD6F8DE";
	
	/**
	 * 
	 * <p>Title: encodeRedisPs</p> 
	 * 
	 * <p>Description: 加密Redis 密码</p>  
	 *
	 * @param password
	 * @return
	 */
	public  static String encodeRedisPs(String password) {
		String pass = AESUtils.encrypt(redisKey, password);
		return pass;
	}
	
	/**
	 * 
	 * <p>Title: decodeRedisPs</p> 
	 * 
	 * <p>Description:解密Redis密码 </p>  
	 *
	 * @param password
	 * @return
	 */
	public  static String decodeRedisPs(String password) {
		String pass = AESUtils.decrypt(redisKey, password);
		return pass;
	}
	
	/**
	 * 
	 * <p>Title: encodeMQPs</p> 
	 * 
	 * <p>Description: 加密MQ 密码</p>  
	 *
	 * @param password
	 * @return
	 */
	public  static String encodeMQPs(String password) {
		String pass = AESUtils.encrypt(mqKey, password);
		return pass;
	}
	
	/**
	 * 
	 * <p>Title: decodeMQPs</p> 
	 * 
	 * <p>Description:解密MQ密码 </p>  
	 *
	 * @param password
	 * @return
	 */
	public  static String decodeMQPs(String password) {
		String pass = AESUtils.decrypt(mqKey, password);
		return pass;
	}
	
	public static void main(String[] args) {
		System.out.println(encodeMQPs("admin@1024"));
		System.out.println(encodeRedisPs("123456"));
	}

}
