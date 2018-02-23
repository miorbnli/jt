package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;


@Service
public class RedisService {
	@Autowired(required=false)//哨兵redis
	private JedisSentinelPool sentinelPool;
	public void set(String key,String value){
		Jedis jedis = sentinelPool.getResource();
		jedis.set(key, value);
		sentinelPool.returnResourceObject(jedis);
	}
	public String get(String key){
		Jedis jedis = sentinelPool.getResource();
		String value = jedis.get(key);
		jedis.close();
		return value;
	}

	/*@Autowired//分片redis
	private ShardedJedisPool jedisPool;
	//定义set方法
	public void set(String key, String value){
		//通过jedis获取对象
		ShardedJedis shardedJedis = jedisPool.getResource();
		//通过jedis操作数据
		shardedJedis.set(key, value);
		//将连接还会池中
		jedisPool.returnResourceObject(shardedJedis);
	}

	//定义get方法
	public String get(String key){
		ShardedJedis shardedJedis = jedisPool.getResource();
		String value = shardedJedis.get(key);
		//将连接还会池中
		jedisPool.returnResourceObject(shardedJedis);
		return value;
	}*/
}
