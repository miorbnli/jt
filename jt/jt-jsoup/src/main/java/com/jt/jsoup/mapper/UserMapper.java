package com.jt.jsoup.mapper;

import java.util.List;

import com.jt.common.mapper.SysMapper;
import com.jt.jsoup.pojo.User;

public interface UserMapper extends SysMapper<User>{

	List<User> findAll();
}
