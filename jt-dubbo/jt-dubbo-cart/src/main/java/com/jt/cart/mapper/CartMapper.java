package com.jt.cart.mapper;



import com.jt.common.mapper.SysMapper;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.pojo.Item;
/**
 * 购物车mapper接口
 */
public interface CartMapper extends SysMapper<Cart>{
	//根据userId和itemId修改购物车商品数量
	void updateNum(Cart cart);
	//商品信息查询
	Item fingItemByItemId(Long itemId);
	//查购物车中是否有此商品
	Cart findCartByUserIdAndItemId(Cart cartTemp);

}
