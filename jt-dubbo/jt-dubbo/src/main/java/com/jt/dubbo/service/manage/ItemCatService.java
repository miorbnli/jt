package com.jt.dubbo.service.manage;

import java.util.List;

import com.jt.common.vo.ItemCatResult;
import com.jt.dubbo.pojo.ItemCat;


public interface ItemCatService {
	//定义业务方法
	List<ItemCat> findItemCat();
	
	List<ItemCat> findItemCatByParentId(Long parentId);
	ItemCatResult findItemCatAll();
	ItemCatResult findItemCatAllByCache();
}
