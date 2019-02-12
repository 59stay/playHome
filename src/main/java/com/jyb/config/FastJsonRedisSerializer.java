package com.jyb.config;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
/**
 * 使用FastJson实现序列化
 * @author jyb
 * 参考地址 https://blog.csdn.net/m0_37592952/article/details/82659686
 * @param <T>
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T>  {
	 public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	 
	    private Class<T> clazz;
	 
	    public FastJsonRedisSerializer(Class<T> clazz) {
	        super();
	        this.clazz = clazz;
	    }
	 
	    @Override
	    public byte[] serialize(T t) throws SerializationException {
	        if (t == null) {
	            return new byte[0];
	        }
	        return JSON.toJSONString(t,SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
	    }
	 
	    @Override
	    public T deserialize(byte[] bytes) throws SerializationException {
	        if (bytes == null || bytes.length <= 0) {
	            return null;
	        }
	        String str = new String(bytes, DEFAULT_CHARSET);
	        return (T) JSON.parseObject(str, clazz);
	    }
	 
}
