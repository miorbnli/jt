package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Order;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private HttpClientService httpClientService;
	private static ObjectMapper objectMapper=new ObjectMapper();
	//商品订单
	@Override
	public String saveOrder(Order order) {
		
		//URL	http://order.jt.com/order/create
		String url="http://order.jt.com/order/create";
		try {
			String orderJSON = objectMapper.writeValueAsString(order);
			//System.out.println(orderJSON);
			//准备map进行数据传输
			Map<String,String> param=new HashMap<>();
			param.put("orderJSON",orderJSON);
			String resultJSON = httpClientService.doPost(url,param);
			System.out.println(resultJSON);
			//将resultJSON转化为SysResult对象
			SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
			//判断远程返回值是否正确
			if(sysResult.getStatus()==200){
				String orderId=(String)sysResult.getData();
				return orderId;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Order findOrderById(String orderId) {
		//URL	http://order.jt.com/order/query/81425700649826
		String url="http://order.jt.com/order/query/"+orderId;
		try {
			String orderJSON = httpClientService.doGet(url);
			Order order = objectMapper.readValue(orderJSON,Order.class);
			return order;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
