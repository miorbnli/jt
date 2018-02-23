package com.jt.manage.mapper;

import java.util.List;

import com.jt.dubbo.pojo.User;

public interface UserMapper {
	//查询全部用户信息
	List<User> findAll();
}
