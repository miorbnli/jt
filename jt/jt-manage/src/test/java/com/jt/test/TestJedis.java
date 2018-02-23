package com.jt.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
/**
 * 测试类
 * @author tarena
 *
 */
public class TestJedis {
	//测试redis操作
	@SuppressWarnings("resource")
	//@Test
	public void test01(){
		/**
		 * 创建jedis工具类
		 * 参数说明:
		 * 	host:表示redis的主机好
		 *  port:表示redis的端口号
		 */
		Jedis jedis=new Jedis("192.168.233.139", 6379);
		//向reis插入数据
		jedis.set("tomcat", "tomcat猫");
		System.out.println(jedis.get("tomcat"));
	}
	//测试分片技术
	/**
	 * 参数说明:
	 * 	1.poolConfig:设置redis服务的配置工具类:例如设置最大连接数,最小空间数量
	 * 	2.shards:表示包含redis节点的配置项List集合
	 * 
	 * 思路:
	 * 	现在需要同时管理3台redis节点,首先应该先定义redis池
	 * 在吃中包含了很多redis的节点信息,如果需要操作redis则先从池中获取
	 * 某个节点的连接/通过该连接实现数据的新增和查询
	 * 
	 * 1.定义连接池是需要设定池的大小(池也有默认值)
	 * 2.有那些节点信息
	 */
	//@Test
	public void test02(){
		JedisPoolConfig poolConfig=new JedisPoolConfig();
		poolConfig.setMaxTotal(1000);//最大连接数
		poolConfig.setMinIdle(5);//最小空间数量
		
		//定义redis多个节点信息
		List<JedisShardInfo> list=new ArrayList<>();
		
		//为集合添加参数
		list.add(new JedisShardInfo("192.168.233.139",6379));
		list.add(new JedisShardInfo("192.168.233.139",6380));
		list.add(new JedisShardInfo("192.168.233.139",6381));
		ShardedJedisPool jedisPool=new ShardedJedisPool(poolConfig, list);
		//获取连接操作redis
		ShardedJedis shardedJedis = jedisPool.getResource();
		shardedJedis.set("tom", "tomcat猫");//数据就绪
		System.out.println(shardedJedis.get("tom"));//获取数据
		
		for(int i=1;i<20;i++){
			shardedJedis.set("Num"+i,i+"");//数据在redis中分部不均匀,Hash一致性造成的
		}
		
		jedisPool.close();
		
	}
	//哨兵测试
	@SuppressWarnings("resource")
	//@Test
	public void test03(){
		//2.定义哨兵的集合
		Set<String> sets=new HashSet<>();
		//3.向集合中就加入哨兵节点
		sets.add(new HostAndPort("192.168.233.139", 26379).toString());
		sets.add(new HostAndPort("192.168.233.139", 26380).toString());
		sets.add(new HostAndPort("192.168.233.139", 26381).toString());
		//1.定义哨兵连接池 参数编辑哨兵名称
		JedisSentinelPool sentinelPool=new JedisSentinelPool("mymaster", sets);
		
		//4.插入数据
		Jedis jedis = sentinelPool.getResource();
		
		jedis.set("1709", "哨兵测试");
		System.out.println(jedis.get("1709"));
		
		
	}
}
