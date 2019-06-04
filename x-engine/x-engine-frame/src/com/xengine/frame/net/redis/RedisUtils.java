package com.xengine.frame.net.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * redis工具
 * @author 朱华煖
 */
public class RedisUtils {
	
	/**
	 * 序列化
	 * @param o
	 * @return
	 */
    public static String encode(Object o) {
        if (o instanceof String) {
            return (String) o;
        } else if (o instanceof Integer) {
            return o.toString();
        } else if (o instanceof Long) {
            return o.toString();
        } else {
            return JSON.toJSONString(o, SerializerFeature.WriteClassName);
        }
    }
    
    /**
     * 序列化
     * @param s 
     * @param cls
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T decode(String s, Class<T> cls) {
        if (s == null) {
            return null;
        }

        if (cls == String.class) {
            return (T) s;
        } else if (cls == Integer.class || cls == int.class) {
            return (T) Integer.valueOf(s);
        } else if (cls == Long.class || cls == long.class) {
            return (T) Long.valueOf(s);
        } else {
            return JSON.parseObject(s, cls);
        }
    }
    
}
