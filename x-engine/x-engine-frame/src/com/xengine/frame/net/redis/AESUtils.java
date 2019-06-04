package com.xengine.frame.net.redis;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.UUID;

/**
 *  AES对称加密
 * @author 朱华煖
 *
 */
public class AESUtils {

	 /** 
     * 密钥算法 
     *
     * */  
    public static final String KEY_ALGORITHM="AES";  
      
    /** 
     * 加密/解密算法/工作模式/填充方式 
     *  
     * JAVA 支持PKCS5PADDING填充方式 
     * Bouncy castle支持PKCS7Padding填充方式 
     * */  
    public static final String CIPHER_ALGORITHM="AES/CBC/PKCS5Padding";  
    
    //使用CBC模式，需要一个向量iv，可增加加密算法的强度
    public static final IvParameterSpec IV = new IvParameterSpec("0102030405060708".getBytes());

    /**
     * 加密
     * @param secret 密钥
     * @param value 待加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String secret, String value) {
        SecretKeySpec keySpec = getKey(secret);
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, IV);
            byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
            return parseByte2HexStr(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     * @param secret 密钥
     * @param value 待解密字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String secret, String value) {
    	SecretKeySpec keySpec = getKey(secret);
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, IV);
            byte[] encrypted1 = parseHexStr2Byte(value);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成加密的密钥，保证长度为16位
     * @param secret 用户的密钥
     * @return 生成的密钥
     */
    private static SecretKeySpec getKey(String secret) {
        byte[] bytes;
		try {
			bytes = secret.getBytes("UTF-8");
			return new SecretKeySpec(Arrays.copyOf(bytes, 16), KEY_ALGORITHM);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
    }

	/**
	 * 将二进制转换成16进制
	 *
	 * @param buf
	 * @return
	 */
	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 *
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	public static void main(String[] args) {
		String key = UUID.randomUUID().toString().toUpperCase();
		System.out.println(key);
	}
}
