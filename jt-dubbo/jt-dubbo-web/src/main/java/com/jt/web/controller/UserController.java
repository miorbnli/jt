package com.jt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.dubbo.pojo.User;
import com.jt.dubbo.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	//登录和注册跳转
	@RequestMapping("/{param}")
	public String module(@PathVariable("param")String param){
		return param;
	}
	//注册用户
	//URL:http://www.jt.com/service/user/doRegister
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user){
		String username=null;
		try {
			username=userService.saveUser(user);
			//用户名不为空
			if(!StringUtils.isEmpty(username)){
				return SysResult.oK(username);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "注册失败",user.getUsername());
	}
	//URL:http://www.jt.com/service/user/doLogin?r=0.8823535176609414
	//通过login.jsp检查username和pasword是否正确
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(String username,String password,HttpServletRequest request,HttpServletResponse response){
		//判断用户名和密码是否为null
		if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
			return SysResult.build(201, "用户名或密码不能为空!");
		}
		try {
			String ticket=userService.findUserByUP(username,password);
			//ticket不能为空,则写入cokie
			if(!StringUtils.isEmpty(ticket)){
				//cookie的名称必须为JT_TICKET
				CookieUtils.setCookie(request, response, "JT_TICKET",ticket,60*30);
				return SysResult.oK(ticket);
			}
			return SysResult.build(201,"用户名或密码错误!");
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201,"用户名或密码错误!");
		}
	}
	
	//http://www.jt.com/user/logout.html
	//用户登出操作
	//1.先从cookie中获取缓存ticket信息
	//2.删除redis
	//3.删除cookie
	//4.跳转页面
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		String ticket = CookieUtils.getCookieValue(request, "JT_TICKET");
		jedisCluster.del(ticket); 
		CookieUtils.deleteCookie(request, response, "JT_TICKET");
		return "redirect:/index.html";
	}
}
