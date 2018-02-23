package com.jt.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.manage.mapper.UserMapper;
import com.jt.manage.pojo.User;
@Service
public class UserServiceImpl implements UserService {
	@Autowired//byName 通过bean的Id注入
	//byType通过Class注入
	//指定ID注入 
	//@Qualifier(value="lyj")
	/**
	 * Mapper交口不能直接实例化,其实spring通过Mybaits为其创建代理对象进行的注入
	 */
	private UserMapper userMapper;
	@Override
	public List<User> findAll() {
		return userMapper.findAll();
	}

}
