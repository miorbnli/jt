package com.jt.dubbo.service;

import java.util.List;

import com.jt.dubbo.pojo.Cart;

public interface CartService {
	//根据userId查询购物车信息
	public List<Cart> findCartByUserId(Long userId);
	//购物车商品数量修改
	public void updateCartNum(Cart cart);
	public void deleteCart(Long userId, Long itemId);
	public void saveCart(Cart cart);
}
