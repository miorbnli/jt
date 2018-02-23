package com.jt.order.service;

import com.jt.order.pojo.Order;

public interface OrderService {
	//新增商品订单
	String saveOrder(Order order);
	//查询完成订单
	Order findOrderById(String orderId);

}
