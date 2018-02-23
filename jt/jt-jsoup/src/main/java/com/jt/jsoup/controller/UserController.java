package com.jt.jsoup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.jsoup.pojo.User;
import com.jt.jsoup.service.UserService;
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping("test")
	@ResponseBody
	public List<User> findAll(){
		List<User> userList=userService.findAll();
		return userList;
	}
}
