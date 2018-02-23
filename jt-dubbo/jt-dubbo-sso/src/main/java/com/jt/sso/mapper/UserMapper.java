package com.jt.sso.mapper;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.dubbo.pojo.User;


public interface UserMapper extends SysMapper<User>{
	//查询数据是否存在
	int findCheckUser(@Param("param")String param, @Param("cloumn")String cloumn);
	//验证用户登录
	User selectUserByUP(@Param("username")String username, @Param("password")String password);

}
