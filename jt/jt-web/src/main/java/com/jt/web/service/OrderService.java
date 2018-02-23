package com.jt.web.service;

import com.jt.web.pojo.Order;

public interface OrderService {
	//保存商品订单
	String saveOrder(Order order);
	//订单成功后再次查找订单结果
	Order findOrderById(String orderId);

}
