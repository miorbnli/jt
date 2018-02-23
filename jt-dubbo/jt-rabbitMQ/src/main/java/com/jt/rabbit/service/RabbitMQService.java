package com.jt.rabbit.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.pojo.OrderItem;
import com.jt.dubbo.pojo.OrderShipping;
import com.jt.rabbit.mapper.OrderItemMapper;
import com.jt.rabbit.mapper.OrderMapper;
import com.jt.rabbit.mapper.OrderShippingMapper;

public class RabbitMQService {
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	/**
	 * 消息队列的实现
	 * 
	 * @param order
	 */
	public void  saveOrder(Order order) {
		//获取OrderId
		String orderId=order.getOrderId();
		
		//为order对象补全数据
		order.setCreated(new Date());
		order.setUpdated(order.getCreated());
		order.setStatus(1);
		orderMapper.insert(order);

		//为orderItem补全数据
		/**
		 * 将List集合实现批量插入
		 * insert into tb_order_item values(id,userId...),(...),(...)
		 */
		List<OrderItem> orderItems=order.getOrderItems();
		for(OrderItem orderItem:orderItems){
			orderItem.setOrderId(orderId);
			orderItemMapper.insert(orderItem);
		}

		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(order.getCreated());
		orderShipping.setUpdated(order.getCreated());
		orderShippingMapper.insert(orderShipping);
		System.out.println("消息队列执行成功");
	}
}
