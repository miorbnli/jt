package com.jt.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.pojo.OrderItem;
import com.jt.dubbo.pojo.OrderShipping;
import com.jt.dubbo.service.OrderService;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	/**
	 * 思路:由于上述三证表都有关联,所以一次入库三张表
	 * 实现方法:
	 * 	1.通过啊Mapper实现能够自动的生成Sql简单快捷
	 * 	2.通过sql语句实现  sql比较复杂,开发效率不高
	 * 
	 * 使用通用Mapper的形式实现
	 * 	1.准备orderId 登录用户id+时间戳,为三个对象赋值
	 * 2.添加创建时间和修改时间
	 * 3.order是单个对象
	 * 4.orderItem是List集合   1 批量插入,  2 循环插入
	 * 5.orderShipping对象
	 */
	@Override
	public String saveOrder(Order order) {
		//准备OrderId
		String orderId=""+order.getUserId()+System.currentTimeMillis();
		//为rabbitMQ封装数据
		order.setOrderId(orderId);
		//将order信息发往消息队列rabbitMQ中,定义路由key
		rabbitTemplate.convertAndSend("save.order",order);
		return orderId;
		
		
		
		
		
		
		
		
		
		/*//为order对象补全数据
		order.setOrderId(orderId);;
		order.setCreated(new Date());
		order.setUpdated(order.getCreated());
		order.setStatus(1);
		orderMapper.insert(order);

		//为orderItem补全数据
		*//**
		 * 将List集合实现批量插入
		 * insert into tb_order_item values(id,userId...),(...),(...)
		 *//*
		List<OrderItem> orderItems=order.getOrderItems();
		for(OrderItem orderItem:orderItems){
			orderItem.setOrderId(orderId);
			orderItemMapper.insert(orderItem);
		}

		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(order.getCreated());
		orderShipping.setUpdated(order.getCreated());
		orderShippingMapper.insert(orderShipping);*/
		//return orderId;

	}

	@Override
	public Order findOrderById(String orderId) {
		/*//查询order表
		Order order = orderMapper.selectByPrimaryKey(orderId);
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(orderId);
		//查询orderItem表
		List<OrderItem> orderItemList = 
				orderItemMapper.select(orderItem);
		
		order.setOrderItems(orderItemList);
		//查询物流表
		OrderShipping orderShipping = 
				orderShippingMapper.selectByPrimaryKey(orderId);
		
		order.setOrderShipping(orderShipping);
		return order;*/
		return orderMapper.findOrderById(orderId);

	}
}
