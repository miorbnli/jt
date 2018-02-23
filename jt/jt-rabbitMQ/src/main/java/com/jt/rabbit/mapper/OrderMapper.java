package com.jt.rabbit.mapper;

import java.util.Date;

import com.jt.common.mapper.SysMapper;
import com.jt.dubbo.pojo.Order;

public interface OrderMapper extends SysMapper<Order>{

	void updateStatusByDate(Date time);
	
	Order findOrderById(String orderId);

}
