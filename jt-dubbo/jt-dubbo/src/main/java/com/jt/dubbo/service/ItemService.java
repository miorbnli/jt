package com.jt.dubbo.service;

import java.util.List;

import com.jt.common.vo.EasyUIResult;
import com.jt.dubbo.pojo.Item;
import com.jt.dubbo.pojo.ItemDesc;


public interface ItemService {
	//查询全部的商品信息
	List<Item> findAll();
	EasyUIResult findItemByPage(int page,int rows);
	//查询商品分类名称
	String findItemCatNameById(Long itemCatId);
	//新增商品信息
	void saveItem(Item item,String desc);
	//修改商品
	void updateItem(Item item,String desc);
	//删除商品
	void deleteItem(Long[] ids);
	//上架及下架
	void updateStatus(Long[] ids, int status);
	//查商品描述
	ItemDesc findItemDesc(Long param);
	//前台查询商品信息
	Item findItemById(Long itemId);
	//查商品信息
	Item findItemByIdCahe(Long itemId);



}
