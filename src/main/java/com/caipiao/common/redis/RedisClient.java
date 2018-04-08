package com.caipiao.common.redis;

import java.util.HashMap;
import java.util.Map;

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
		Map<String,String> map = new HashMap<String,String>();
		map.put("a1", "v1");
		ShardedJedis jedis =  getShardedJedis();
		jedis.hmset("abc", map);
		jedis.expire("abc", 10);
		
		for(int i=0;i<100;i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(i==8) {
				jedis.expire("abc", 10);
			}
			System.out.println(i);
			String value = jedis.hget("abc","a1");
			System.out.println(value);
		}
		
		System.out.println("---");
	}
}
