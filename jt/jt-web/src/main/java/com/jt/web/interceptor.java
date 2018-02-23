package com.jt.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.util.CookieUtils;
import com.jt.web.pojo.User;
import com.jt.web.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;
/**
 * 拦截器
 * @author tarena
 *
 */
public class interceptor implements HandlerInterceptor{
	@Autowired
	private JedisCluster jedisCluster;
	private static ObjectMapper objectMapper=new ObjectMapper();
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获取Cookie
		String ticket = CookieUtils.getCookieValue(request, "JT_TICKET");
		//2.判断cookie中是否有值
		if(!StringUtils.isEmpty(ticket)){
			//3.如果ticket不为null,通过缓存查询用户信息
			String userJSON = jedisCluster.get(ticket);
			/**
			 * 4.判断缓存数据是否为null
			 * 原因:浏览器一致保存着cookie,redis中有缓存策略
			 * 可能会删除过期的key
			 */
			if(!StringUtils.isEmpty(userJSON) ){
				//5.获取user数据
				User user = objectMapper.readValue(userJSON, User.class);
				//6.User对象如何存储,才能在Car中获取user信息
				UserThreadLocal.setUser(user);
				
				return true;
			}
			
		}
		
		//用户没有登录进行页面跳转
		response.sendRedirect("/user/login.html");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
