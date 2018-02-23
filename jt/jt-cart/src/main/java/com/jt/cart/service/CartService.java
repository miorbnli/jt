package com.jt.cart.service;

import java.util.List;

import com.jt.cart.pojo.Cart;

public interface CartService {
	//根据用户id查询购物车商品信息
	List<Cart> findCartUserById(Long userId);
	//修改购物车商品数量
	void updateCartNum(Cart cart);
	//删除购物车信息
	void deleteCart(Long userId, Long itemId);
	//保存购物车
	void saveCart(Cart cart);

}
