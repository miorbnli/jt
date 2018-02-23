package com.jt.web.util;

import com.jt.dubbo.pojo.User;

public class UserThreadLocal {
	/**
	 * 1.初始化threadLocal对象
	 * 	T泛型的种类:
	 * 		1.直接写对象User,那么这个threadLocal只能存放一个值User对象
	 * 		2.如果想存入多个数据使用Map
	 */
	private static ThreadLocal<User> userTreadLocal=new ThreadLocal<User>();
	
	//存入ThreadLocal的方法
	public static void setUser(User user){
		userTreadLocal.set(user);
	}
	public static User getUser(){
		return userTreadLocal.get();
	}
}
