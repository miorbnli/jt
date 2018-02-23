package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.dubbo.pojo.Item;
/**
 * 商品菜单接口
 * @author tarena
 *
 */
public interface ItemMapper extends SysMapper<Item>{
	List<Item> findAll();
	int findItemCount();
	//分页查询数据begin代表起始位置和数据量
	//mybatis中值允许传递单个数据,需要传递多个数据时,需要封装,一般采用map结构
	List<Item> findItemByPage(@Param("begin") int begin, @Param("rows")int rows);
	void updateStatus(@Param("ids")Long[] ids, @Param("status")int status);
}
