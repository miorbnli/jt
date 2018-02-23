package com.jt.web.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Cart;


@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private HttpClientService httpClientService;
	private static ObjectMapper objectMapper=new ObjectMapper();
	//查询购物车列表信息
	@Override
	public List<Cart> findCartByUserId(Long userId) {
		//
		String url="http://cart.jt.com/cart/query/"+userId;

		try {
			String resultJSON = httpClientService.doGet(url);
			//通过objectMaper直接获取json的属性
			//要求:JSON串必须是对象的JSON{id:1,name:tom}
			//而不能是List/array的JSON[value1,value2]

			//{id:1,name:tom}
			JsonNode jsonNode = objectMapper.readTree(resultJSON);
			//将JSON数据转换为String类型的字符串[{},{},{}]
			String cartListJSON = jsonNode.get("data").asText();
			//将cartList的JSON数转化为List集合
			Cart[] carts = objectMapper.readValue(cartListJSON, Cart[].class);
			//通过工具类将数组转化为List集合
			List<Cart> cartList = Arrays.asList(carts);
			return cartList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//修改购物车商品数量
	@Override
	public void updateCartNum(Cart cart) {
		//定义url
		String url="http://cart.jt.com/cart/update/num/"+cart.getUserId()+"/"+cart.getItemId()+"/"+cart.getNum();
		//提交方式
		try {
			httpClientService.doGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//删除购物车中的商品
	@Override
	public void deleteCart(Long userId, Long itemId) {
		String url="http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
		//System.out.println(url);
		try {
			httpClientService.doGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//商品添加购物车
	@Override
	public void saveCart(Cart cart) {
		String url="http://cart.jt.com/cart/save";
		Map<String ,String> map=new HashMap<>();
		map.put("userId",cart.getUserId()+"");
		map.put("itemId", cart.getItemId()+"");
		map.put("itemTitle",cart.getItemTitle());
		map.put("itemImage", cart.getItemImage());
		map.put("itemPrice",cart.getItemPrice()+"");
		map.put("num", cart.getNum()+"");
		try {
			httpClientService.doPost(url,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
