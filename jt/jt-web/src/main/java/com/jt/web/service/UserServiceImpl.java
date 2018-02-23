package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private HttpClientService httpClientService;
	
	private static ObjectMapper objectMapper=new ObjectMapper();
	@Override
	public String saveUser(User user) {
		String url="http://sso.jt.com/user/register";
		Map<String,String> map=new HashMap<>();
		map.put("username", user.getUsername());
		map.put("password", user.getPassword());
		map.put("phone", user.getPhone());
		map.put("email", user.getEmail());
		
		try {
			//经过httpClient调一下返回SysResult对象,首先应该查看
			//返回的状态码如果为200表示sso一起正常
			String resutlJSON = httpClientService.doPost(url, map);
			SysResult result = objectMapper.readValue(resutlJSON, SysResult.class);
			if(result.getStatus()==200){
				return (String)result.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//用户登录查询
	//返回ticket
	@Override
	public String findUserByUP(String username, String password) {
		//单点登录地址
		String url="http://sso.jt.com/user/login";
		//封装为map通过httpClient跨域请求
		Map<String,String > map=new HashMap<>();
		//key设置为u和u,提高网络传输速度
		map.put("u", username);
		map.put("p", password);
		try {
			String resultJSON = httpClientService.doPost(url,map);
			//从json转为对象
			//防止data数据为null时,返回的字符串为"null",对代码产生影响
			SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
			
			//System.out.println(sysResult.getStatus());
			if(sysResult.getStatus()==200){
				return (String)sysResult.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
