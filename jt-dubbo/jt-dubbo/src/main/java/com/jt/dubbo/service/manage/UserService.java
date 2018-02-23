package com.jt.dubbo.service.manage;

import java.util.List;

import com.jt.dubbo.pojo.User;


public interface UserService {
	//定义service层的接口文件
	List<User> findAll();
}
