package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Item;
import com.jt.web.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("/{itemId}")
	public String findItemById(@PathVariable Long itemId,Model model){
		//经过分析前台需要通过${item.id}的方式进行取值
		//1.准备一个item对象并且item对象存入域中(request域)
		//2.根据item查询信息
		Item item = itemService.findItemByIdCahe(itemId);
		model.addAttribute("item",item);
		return "item";
	}

}
