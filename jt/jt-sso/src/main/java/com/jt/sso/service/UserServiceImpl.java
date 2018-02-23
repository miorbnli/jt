package com.jt.sso.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private JedisCluster jedisCluster;
	private static ObjectMapper objetcMapper=new ObjectMapper();
	@Autowired
	private UserMapper userMapper;
	//验证数据
	@Override
	public Boolean findCheckUser(String param, Integer type) {
		String cloumn=null;
		switch (type) {
		case 1:cloumn="username";break;
		case 2:cloumn="phone";break;
		case 3:cloumn="email";break;	
		}
		int count=userMapper.findCheckUser(param,cloumn);

		return count==1;
	}
	//用户新增
	@Override
	public String saveUser(User user) {
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		//将密码进行加密处理
		String md5password=DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5password);
		//修改bug,填补邮箱为null的现象
		user.setEmail(user.getPhone());
		//执行insert操作
		userMapper.insert(user);
		return user.getUsername();
	}
	/**
	 * 1.验证用户名和密码是否正确
	 * 2.需要将用户的铭文加密为密文
	 * 3.如果用户信息校验通过,准备redis缓存数据
	 * 4.生成tickeyt="JT_TICKET_" + System.currentTimeMillis()+username;
	 * 5.将user转换为JSON数据
	 * 6.将ticket和JSON存入redis
	 * 7.返回ticket
	 * 
	 */
	@Override
	public String findUserByUP(String username, String password) {
		//用户登录密码加密
		String md5password=DigestUtils.md5Hex(password);
		User user = userMapper.selectUserByUP(username,md5password);//与数据库进行比较
		//System.out.println("user="+user);
		if(user!=null){
			//获取令牌ticket存入用做key存入redis以及返回前台cookie存储
			String key=DigestUtils.md5Hex("JT_TICKET_" + System.currentTimeMillis()+username);
			try {
				//转换为JSON方便存入redis
				String jedisJSON = objetcMapper.writeValueAsString(user);
				jedisCluster.set(key, jedisJSON);
				return key;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
}
