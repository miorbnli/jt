package com.jt.jsoup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.jsoup.mapper.UserMapper;
import com.jt.jsoup.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Override
	public List<User> findAll() {
		
		return userMapper.findAll();
	}
	
}
