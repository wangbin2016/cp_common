package com.caipiao.common.redis;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class RedisConfig {
	private JedisPoolConfig config;
	private ShardedJedisPool shardPool;
	private Properties properties;

	public RedisConfig() {
		properties = new Properties();
		System.out.println("初始化redis");
		InputStream in = RedisConfig.class.getClassLoader().getResourceAsStream("redis.properties");
		if (in != null) {
			try {
				properties.load(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		initShardedJedisPool();
		System.out.println("----------------------------初始化redis 完成----------------------------");
	}

	public ShardedJedisPool getShardedJedisPool() {
		return this.shardPool;
	}

	private JedisPoolConfig getPoolConfig() {
		if (config == null) {
			config = new JedisPoolConfig();
			// 最大连接数
			config.setMaxTotal(Integer.valueOf(properties.getProperty("redis.pool.maxTotal")).intValue());
			// 最大空闲连接数
			config.setMaxIdle(Integer.valueOf(properties.getProperty("redis.pool.maxIdle")).intValue());
			// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
			config.setMaxWaitMillis(Long.valueOf(properties.getProperty("redis.pool.maxWaitMillis")).longValue());
			// 在获取连接的时候检查有效性, 默认false
			config.setTestOnBorrow(Boolean.valueOf(properties.getProperty("redis.pool.testOnBorrow")).booleanValue());
			// 在获取返回结果的时候检查有效性, 默认false
			config.setTestOnReturn(Boolean.valueOf(properties.getProperty("redis.pool.testOnReturn")).booleanValue());
		}
		return config;

	}

	private void initShardedJedisPool() {
		if (shardPool == null) {
			// 创建多个redis共享服务
			String password = properties.getProperty("password");
			String redis1Ip = properties.getProperty("redis1.ip");
			int redis1Port = Integer.valueOf(properties.getProperty("redis1.port"));
			JedisShardInfo jedisShardInfo1 = new JedisShardInfo(redis1Ip, redis1Port);
			jedisShardInfo1.setPassword(password);
			String redis2Ip = properties.getProperty("redis2.ip");
			int redis2Port = Integer.valueOf(properties.getProperty("redis2.port"));
			JedisShardInfo jedisShardInfo2 = new JedisShardInfo(redis2Ip, redis2Port);
			jedisShardInfo2.setPassword(password);
			List<JedisShardInfo> serverlist = new LinkedList<JedisShardInfo>();
			serverlist.add(jedisShardInfo1);
			serverlist.add(jedisShardInfo2);
			// 初始化连接池
			shardPool = new ShardedJedisPool(getPoolConfig(), serverlist);
		}
	}

}
