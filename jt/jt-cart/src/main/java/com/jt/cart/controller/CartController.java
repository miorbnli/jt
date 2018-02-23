package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	private static ObjectMapper objectMapper=new ObjectMapper();
	
	/**
	 * 根据用户id查询用户的购物车信息
	 * 返回Sysresult对象,并且data数据中保存cartlist的JSON数据
	 */
	//URL	http://cart.jt.com/cart/query/{userId}
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult findCartByUserId(@PathVariable("userId") Long userId){
		try {
			//获取cartList数据
			List<Cart> cartList=cartService.findCartUserById(userId); 
			String cartListJSON = objectMapper.writeValueAsString(cartList);
			return SysResult.oK(cartListJSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "查询失败");
	}
	
	//URL	http://cart.jt.com/cart/update/num/{userId}/{itemId}/{num}
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable("userId")Long userId,@PathVariable("itemId")Long itemId,@PathVariable("num")Integer num){
		try {
			
			Cart cart=new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);
			
			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201,"修改商品数量失败");
	}
	
	
	//URL	http://cart.jt.com/cart/delete/{userId}/{itemId}
	//删除购物商品
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(@PathVariable("userId")Long userId,@PathVariable("itemId")Long itemId){
		try {
			cartService.deleteCart(userId,itemId);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "删除失败");
		}
	}
	//URL	http://cart.jt.com/cart/save
	@RequestMapping("save")
	@ResponseBody
	public SysResult saveCart(Cart cart){
		//System.out.println("userId="+userId+"  itemId="+itemId);
		if(cart==null){
			return SysResult.build(400, "参数不合法!");
		}
		try {
			 cartService.saveCart(cart);
			
				return SysResult.oK();
			
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "保存商品失败!");
		}
	}
}
