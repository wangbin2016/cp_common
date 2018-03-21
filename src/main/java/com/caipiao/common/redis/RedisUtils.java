package com.caipiao.common.redis;

import org.springframework.util.SerializationUtils;

import redis.clients.jedis.ShardedJedis;

public class RedisUtils {

	public static ShardedJedis getSharderJedis() {
		return RedisClient.getShardedJedis();
	}
	
	public static<T> void putObject(String key,T t){
		byte[] b = SerializationUtils.serialize(t);
		ShardedJedis sharderJedis = getSharderJedis();
		sharderJedis.set(key.getBytes(), b);
		sharderJedis.close();
	}
	
	public static<T> T getObject(String key){
		ShardedJedis sharderJedis = getSharderJedis();
		@SuppressWarnings("unchecked")
		T t = (T) SerializationUtils.deserialize(sharderJedis.get(key.getBytes()));
		sharderJedis.close();
		return t;
	}
}
