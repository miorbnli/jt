package com.jt.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.SysResult;
import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;
/**
 * 订单系统
 * @author tarena
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	private static ObjectMapper objectMapper=new ObjectMapper();
	
	//URL	http://order.jt.com/order/create
	/**
	 * 1.对象和JSON转化的说明
	 * 2.对象了方法颞部使用,最好转化为JSON串
	 * 3.如果在方法内使用,最好转为对象方便获取数据
	 * 总:传递用JSON,使用为对象
	 */
	@RequestMapping("/create")
	@ResponseBody
	public SysResult saveOrder(String orderJSON){
		//System.out.println(orderJSON);
		try {
			Order order = objectMapper.readValue(orderJSON, Order.class);
			
			String orderId=orderService.saveOrder(order);
			//System.out.println(orderId);
			//String orderId1 = objectMapper.writeValueAsString(orderId);
			return SysResult.oK(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201,"新增商品订单失败");
		}

	}
	//http://order.jt.com/order/query/81425700649826
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Order findOrderById(@PathVariable("orderId") String orderId){
		
		return orderService.findOrderById(orderId);
	}

}
