package com.jyb.config;

import javax.annotation.Nullable;

import org.springframework.data.redis.serializer.SerializationException;

public interface  RedisSerializer<T> {
	 
	@Nullable
	byte[] serialize(@Nullable T t) throws SerializationException;
 
	@Nullable
	T deserialize(@Nullable byte[] bytes) throws SerializationException;
}
