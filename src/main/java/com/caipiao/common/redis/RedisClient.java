package com.caipiao.common.redis;

import redis.clients.jedis.ShardedJedis;

public class RedisClient {
	private volatile static RedisConfig redisConfig;
	
	public static ShardedJedis getShardedJedis() {
		if(redisConfig == null) {
			init();
		}		
		return redisConfig.getShardedJedisPool().getResource();
	}
	
	private synchronized static void init() {
		if(redisConfig == null)
			redisConfig = new RedisConfig();
	}
	
	public static void main(String[] args) {
		ShardedJedis jedis =  getShardedJedis();
		jedis.set("test1", "value1");
		jedis.close();
		
		ShardedJedis jedis2 =  getShardedJedis();
		String value = jedis2.get("test1");
		jedis2.close();
		System.out.println(value);
	}
}
