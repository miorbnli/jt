package com.jt.dubbo.service;

import com.jt.dubbo.pojo.User;

public interface UserService {
	//校验数据是否存在
	Boolean findCheckUser(String param, Integer type);
	//用户新新增
	String saveUser(User user);
	//根据密码和用户用户名进行登录
	String findUserByUP(String username, String password);

}
