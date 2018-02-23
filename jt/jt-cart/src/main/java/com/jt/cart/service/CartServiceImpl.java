package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.common.service.BaseService;


@Service
public class CartServiceImpl extends BaseService<Cart> implements CartService {
	@Autowired
	private CartMapper cartMapper;
	@Value("${itemkey}")
	private String ITEM_;
	//根据用户id查询购物车商品信息
	@Override
	public List<Cart> findCartUserById(Long userId) {
		Cart cart=new Cart();
		cart.setUserId(userId);
		//通过通用mapper查询用户购物车信息
		List<Cart> cartList = cartMapper.select(cart);
		return cartList;
	}
	//更新购物车
	@Override
	public void updateCartNum(Cart cart) {
		//添加修改时间
		cart.setUpdated(new Date());
		cartMapper.updateNum(cart);
	}
	//删除购物车
	@Override
	public void deleteCart(Long userId, Long itemId) {
		Cart cart=new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		super.deleteByWhere(cart);
	}
	//保存购物车
	/**
	 * 1.如果用户第一次添加该商品则进行入库操作
	 * 2.如果用户已经添加过该商品,则更改商品数量
	 */
	@Override
	public void  saveCart(Cart cart) {
		//根据itemId和UserId判断用户是否买过该商品
		Cart cartTemp=new Cart();
		cartTemp.setUserId(cart.getUserId());
		cartTemp.setItemId(cart.getItemId());
		
		Cart cartDB=cartMapper.findCartByUserIdAndItemId(cartTemp);
		if(cartDB !=null){
			//证明原来有该数据
			int count=cartDB.getNum()+cart.getNum();
			cartDB.setNum(count);
			cartDB.setUpdated(new Date());
			cartMapper.updateByPrimaryKeySelective(cartDB);
		}else{
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
			
		}
	}
}
