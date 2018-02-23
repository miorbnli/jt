package com.jt.web.service;

import java.util.List;

import com.jt.web.pojo.Cart;

public interface CartService {
	//查询购物车列表信息
	List<Cart> findCartByUserId(Long userId);
	//修改购物车商品数量
	void updateCartNum(Cart cart);
	
	//删除购物车
	void deleteCart(Long userId, Long itemId);
	//商品添加购物车
	void saveCart(Cart cart);
	

}
