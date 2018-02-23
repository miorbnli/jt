package com.jt.manage.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.dubbo.pojo.ItemCat;

/**
 * 商品菜单类接口
 * @author tarena
 *
 */
public interface ItemCatMapper extends SysMapper<ItemCat> {
	//传统需要在接口中编辑很多的接口方法方便调用
	 String findItemCatNameById(Long itemCatId);
	 /**
	  * 除非有特顶业务,否则不需要写方法
	  * 规则:使用通用Mapper通常使用与单表操作
	  * 	如果是多表关联需要自己编写sql
	  */
}
