package com.jt.web.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jt.dubbo.pojo.Item;
import com.jt.dubbo.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	// URL:http://www.jt.com/search.html?q=%E5%8D%8E%E4%B8%BA
	@RequestMapping("/search")
	public String findItemByKey(@RequestParam("q")String keyword,Model model){
		//由于get提交中文必定乱码
		try {
			keyword=new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Item> itemList =searchService.findItemByKey(keyword);
		//返回页面关键字
		model.addAttribute("query",keyword);
		model.addAttribute("itemList", itemList);
		return "search";
	}
}
