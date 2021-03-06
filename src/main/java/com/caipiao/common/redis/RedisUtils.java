package com.caipiao.common.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.SerializationUtils;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

/**
 * redis 工具类
 * 
 * @author wangb
 *
 */
@Slf4j
public class RedisUtils {
	public static int SESSION_DEAD_TIME = 1800; // session 失效时间 30分钟

	public static ShardedJedis getSharderJedis() {
		return RedisClient.getShardedJedis();
	}

	public static <T> void putObject(String key, T t) {
		byte[] b = SerializationUtils.serialize(t);
		ShardedJedis sharderJedis = getSharderJedis();
		sharderJedis.set(key.getBytes(), b);
		sharderJedis.close();
	}

	public static <T> T getObject(String key) {
		ShardedJedis sharderJedis = getSharderJedis();
		@SuppressWarnings("unchecked")
		T t = (T) SerializationUtils.deserialize(sharderJedis.get(key.getBytes()));
		sharderJedis.close();
		return t;
	}

	public static <T> void setList(String key, List<T> value) {
		ShardedJedis sharderJedis = getSharderJedis();
		setList(key, value, sharderJedis);
		sharderJedis.close();
	}

	public static <T> void setList(String key, List<T> value, ShardedJedis sharderJedis) {
		for (int i = 0; i < value.size(); i++) {
			T t = value.get(i);
			byte[] obj = SerializationUtils.serialize(t);
			sharderJedis.rpush(key.getBytes(), obj);
		}
	}

	public static <T> List<T> getList(String key) {
		ShardedJedis sharderJedis = getSharderJedis();
		List<T> tList = getList(key, sharderJedis);
		sharderJedis.close();
		return tList;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(String key, ShardedJedis sharderJedis) {
		List<T> tList = new ArrayList<T>();
		byte[] keys = key.getBytes();
		long length = sharderJedis.llen(keys);
		List<byte[]> list = sharderJedis.lrange(keys, 0, length);
		for (byte[] obj : list) {
			T t = (T) SerializationUtils.deserialize(obj);
			tList.add(t);
		}
		return tList;
	}

	public static void setSession(String sessionKey, String key, Object value) {
		ShardedJedis sharderJedis = getSharderJedis();
		putMap(sessionKey, key, value, sharderJedis);
		sharderJedis.expire(sessionKey.getBytes(), SESSION_DEAD_TIME);
		sharderJedis.close();
	}

	public static void removeKey(String key) {
		ShardedJedis sharderJedis = getSharderJedis();
		Long len = sharderJedis.del(key.getBytes());
		if (len > 0) {
			log.info("删除缓存  " + key + " 成功");
		}
	}

	public static void cleanSession(String sessionKey) {
		removeKey(sessionKey);
	}

	public static <T> T getSession(String sessionKey, String key) {
		ShardedJedis sharderJedis = getSharderJedis();
		T t = getMap(sessionKey, key, sharderJedis);
		sharderJedis.expire(sessionKey.getBytes(), SESSION_DEAD_TIME);
		sharderJedis.close();
		return t;
	}

	public static Map<String, Object> getSession(String sessionKey) {
		ShardedJedis sharderJedis = getSharderJedis();
		Map<String, Object> map = getMapAll(sessionKey, sharderJedis);
		sharderJedis.expire(sessionKey.getBytes(), SESSION_DEAD_TIME);
		sharderJedis.close();
		return map;
	}

	public static void putMap(String mapKey, String key, Object value, ShardedJedis sharderJedis) {
		boolean isNull = sharderJedis == null;
		if (isNull) {
			sharderJedis = getSharderJedis();
		}
		try {
			sharderJedis.hset(mapKey.getBytes(), key.getBytes(), SerializationUtils.serialize(value));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (isNull)
				sharderJedis.close();
		}
	}

	public static void putMap(String mapKey, Map<String, Object> map, ShardedJedis sharderJedis) {
		boolean isNull = sharderJedis == null;
		if (isNull) {
			sharderJedis = getSharderJedis();
		}
		try {
			Map<byte[], byte[]> tempMap = new HashMap<byte[], byte[]>();
			if (map != null) {
				for (Entry<String, Object> entry : map.entrySet()) {
					byte[] key = entry.getKey().getBytes();
					byte[] value = SerializationUtils.serialize(entry.getValue());
					tempMap.put(key, value);
				}
			}
			sharderJedis.hmset(mapKey.getBytes(), tempMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (isNull)
				sharderJedis.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getMap(String mapKey, String valueKey, ShardedJedis sharderJedis) {
		boolean isNull = sharderJedis == null;
		if (isNull) {
			sharderJedis = getSharderJedis();
		}
		try {
			byte[] value = sharderJedis.hget(mapKey.getBytes(), valueKey.getBytes());
			T t = (T) SerializationUtils.deserialize(value);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (isNull)
				sharderJedis.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> getMapAll(String mapKey, ShardedJedis sharderJedis) {
		boolean isNull = sharderJedis == null;
		if (isNull) {
			sharderJedis = getSharderJedis();
		}
		try {
			Map<byte[], byte[]> map = sharderJedis.hgetAll(mapKey.getBytes());
			Map<String, T> tempMap = new HashMap<String, T>();
			for (Entry<byte[], byte[]> entry : map.entrySet()) {
				tempMap.put(new String(entry.getKey()), (T) SerializationUtils.deserialize(entry.getValue()));
			}
			return tempMap;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (isNull)
				sharderJedis.close();
		}
		return null;
	}
}
