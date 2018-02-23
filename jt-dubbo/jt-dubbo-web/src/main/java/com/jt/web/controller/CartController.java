package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.service.CartService;
import com.jt.web.util.UserThreadLocal;


@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	//转向购物车页面/cart/show.html
	@RequestMapping("/show")
	public String findCartByUserId(Model model){
		//Long userId =2L;//展示写死userId
		Long userId =UserThreadLocal.getUser().getId();
		//查询购物车列表信息
		List<Cart> cartList=cartService.findCartByUserId(userId);
		//将山水写入requset域中
		model.addAttribute("cartList",cartList );
		//转向cart.jsp
		return "cart";
	}
	
	//URL:http://www.jt.com/service/cart/update/num/536563/2
	//购物车商品数量修改
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public String updateCartNum(@PathVariable("itemId")Long itemId,@PathVariable("num")Integer num){
		
		//Long userId=2L;
		Long userId =UserThreadLocal.getUser().getId();
		Cart cart=new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cart.setNum(num);
		cartService.updateCartNum(cart);
		return "";
	}
	
	//URL:http://www.jt.com/cart/delete/536563.html
	//删除购物车
	@RequestMapping("/delete/{itemId}")
	public String deleCart(@PathVariable("itemId")Long itemId){
		
		//Long userId=2L;
		Long userId =UserThreadLocal.getUser().getId();
		cartService.deleteCart(userId,itemId);
		//跳转到购物车列表此页面
		return "redirect:/cart/show.html";
	}
	// URL:http://www.jt.com/cart/add/562379.html
	//商品添加购物车
	@RequestMapping("/add/{itemId}")
	public String saveCart(@PathVariable("itemId")Long itemId,Cart cart){
		//Long userId=2l;
		Long userId =UserThreadLocal.getUser().getId();
		//将数据进行封装
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartService.saveCart(cart);
		//转向购物车展示页面
		return "redirect:/cart/show.html";
	}
}
