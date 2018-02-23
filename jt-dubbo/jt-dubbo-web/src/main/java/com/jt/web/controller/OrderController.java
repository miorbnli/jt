package com.jt.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.service.CartService;
import com.jt.dubbo.service.OrderService;
import com.jt.web.util.UserThreadLocal;
/**
 * 前台order
 * @author tarena
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	
	//URL:http://www.jt.com/order/create.html
	@RequestMapping("/create")
	public String create(Model model){
		//根据当前的用户信息获取购物车信息
		Long userId=UserThreadLocal.getUser().getId();
		List<Cart> carts = cartService.findCartByUserId(userId);
		model.addAttribute("carts", carts);
		return "order-cart";
	}
	//URL:http://www.jt.com/service/order/submit
	@RequestMapping("/submit")
	@ResponseBody
	public  SysResult saveOrder(Order order){
		try {
			//获取userId
			//System.out.println(order);
			Long userId = UserThreadLocal.getUser().getId();
			order.setUserId(userId);
			String orderId=orderService.saveOrder(order);
			//一切正常
			if(!StringUtils.isEmpty(orderId)){
				System.out.println("正常");
				return SysResult.oK(orderId);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "新增失败!");
	}
	
	
	//URL:http://www.jt.com/order/success.html?id=....
	@RequestMapping("/success")
	public String success(@RequestParam("id")String orderId,Model model){
		Order order=orderService.findOrderById(orderId);
		model.addAttribute("order",order);
		return "success";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
