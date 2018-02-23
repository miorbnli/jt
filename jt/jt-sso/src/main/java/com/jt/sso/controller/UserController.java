package com.jt.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userservice;
	@Autowired
	private JedisCluster jedisCluster;
	
	// URL:http://sso.jt.com/user/check/uuuuuu/1?r=0.48566311963215214&callback=jsonp1517466173965&_=1517466181360
	/**数据校验,type:1为用户名校验,2为phone校验 ,3为邮箱校验*/
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkUser(@PathVariable("param")String param,@PathVariable("type")Integer type,String callback){
		
		try {
			//根据传递的参数判断数据是否存在
			Boolean result=userservice.findCheckUser(param,type);
			//return SysResult.oK(result);
			//返回JSOP的数据
			MappingJacksonValue jacksonValue = new MappingJacksonValue(SysResult.oK(result));
			jacksonValue .setJsonpFunction(callback);
			return jacksonValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//http://sso.jt.com/user/register
	/**用户注册*/
	@RequestMapping("/register")
	@ResponseBody
	public SysResult saveUser(User user){
		try {
			String username=userservice.saveUser(user);
			return SysResult.oK(username);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "用户注册失败");
		}
	
	}
	
	//url:http://sso.jt.com/user/login
	/**用户登录*/
	@RequestMapping("/login")
	@ResponseBody
	public SysResult doLogin(@RequestParam("u")String username, @RequestParam("p")String password){
		//System.out.println("UserController.doLogin():username="+username+"pasword="+password);
		try {
			String ticket=userservice.findUserByUP(username,password);
			if(!StringUtils.isEmpty(ticket)){
				return SysResult.oK(ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户登录失败");
	}
	
	//http://sso.jt.com/user/query/{ticket}
	/**用户令牌登录*/
	@RequestMapping("/query/{ticket}")
	@ResponseBody
	public Object findUserByTicket(@PathVariable("ticket")String ticket,String callback){
		//System.out.println("ticket="+ticket);
		//根据ticket在缓存中查找用户信息数据
		String userJSON = jedisCluster.get(ticket);
		try {
			//判断redsi中是否有用户信息
			if(!StringUtils.isEmpty(userJSON)){
				//有则在页面中回显用户信息数据(ajax跨域请求返回数据)
				MappingJacksonValue jacksonValue = new MappingJacksonValue(SysResult.oK(userJSON));
				//callback可跨域请求的返回
				jacksonValue .setJsonpFunction(callback);
				return jacksonValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
}
