package com.jt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.ItemCatResult;
import com.jt.dubbo.service.manage.ItemCatService;

@Controller
@RequestMapping("/web")
public class WebItemCatController {	
	@Autowired
	private ItemCatService itemCartService ;
	@RequestMapping("/itemcat/all")
	@ResponseBody
	public Object  findItemCatAll(String callback){
		//查询所有的菜单
		ItemCatResult itemCatResult = itemCartService.findItemCatAllByCache();
		MappingJacksonValue jacksonValue = new MappingJacksonValue(itemCatResult);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
}
