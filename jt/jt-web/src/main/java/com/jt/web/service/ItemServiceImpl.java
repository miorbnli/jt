package com.jt.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;

import redis.clients.jedis.JedisCluster;
@Service
public class ItemServiceImpl implements ItemService {
	@Value("${itemkey}")
	private String ITEM_;
	@Autowired
	private HttpClientService httpClientService;
	@Autowired
	private JedisCluster jedisCluster;
	private static final ObjectMapper objectMapper=new ObjectMapper();
	/***
	 * 前台查询数据依赖后台
	 */
	@Override
	public Item findItemById(Long itemId) {
		String uri="http://manage.jt.com/web/item/"+itemId;
		try {
			//通过后台查询item对象的json数据
			String restJSON=httpClientService.doGet(uri);
			//转化为item对象
			Item item = objectMapper.readValue(restJSON,Item.class);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//redis缓存查询
	public Item findItemByIdCahe(Long itemId) {
		String key=ITEM_+itemId;
		//查询缓存中是否有该商品信息
		String jsonResult = jedisCluster.get(key);
		try {
			if(StringUtils.isEmpty(jsonResult)){
				Item item=findItemById(itemId);
				String jsonData = objectMapper.writeValueAsString(item);
				jedisCluster.set(key, jsonData);
			}else{
				Item item = objectMapper.readValue(jsonResult, Item.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
