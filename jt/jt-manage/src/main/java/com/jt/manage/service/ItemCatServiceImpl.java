package com.jt.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;

import redis.clients.jedis.JedisCluster;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	//定义格式转化工具
	private static final ObjectMapper objectMapper=new  ObjectMapper();
	@Autowired
	private ItemCatMapper itemCatMapper;
	//单节点缓存操作(不实用)
	//@Autowired
	//private Jedis jedis;
	//@Autowired//哨兵
	//private RedisService redisService;
	@Autowired//集群
	private JedisCluster jedisCluster;
	@Value("${item_cat_key}")
	private String ITEM_CAT_KEY;
	@Value("${itemkey}")
	private String ITEM_;
	@Override
	public List<ItemCat> findItemCat() {
		//如果查询全部数据则不需要设定参数直接为null

		return itemCatMapper.select(null);
	}
	/**
	 * 将商品分类的信息插入到缓存中
	 * 思路:redis通过key value保存数据
	 * 	1.当用户查询数据时首先应该先从缓存中获取数据
	 * 	2.如果数据不为null,则代表缓存中有该数据,数据类型为JSON
	 * 	3.需要将JSON转化为对象后返回给用户
	 * 	4.如果数据为null,表示缓存中没有该数据,则需要连接数据库进行查询
	 * 	5.将查询到的数据转化为JSON对象存入到redis中方便下次使用
	 */
	@Override
	public List<ItemCat> findItemCatByParentId(Long parentId) {
		/**
		 * 通用mapper带参数的查询
		 * 如果带参数,只需要将参数set到具体的对象中即可
		 * 实现的思路:
		 * 	通用mapper会将对象中不为null的数据当作where条件进行查询
		 * lg:select id,parent_id,.. from tn_item_cat where parent_id=0  and status=1 and...
		 */
		//1.定义查询的key值
		String key=ITEM_+parentId;

		//2.根据key查询缓存数据
		String dataJSON=jedisCluster.get(key);
		//最后定义公用List集合
		List<ItemCat> itemCatlist=new ArrayList<>();
		//3.判断返回值是否含有数据
		try {
			if(StringUtils.isEmpty(dataJSON)){
				//缓存中没有数据
				ItemCat itemCat=new ItemCat();
				itemCat.setParentId(parentId);//设定父级id
				itemCat.setStatus(1);//设定正常的分类
				itemCatlist = itemCatMapper.select(itemCat);
				//转为JSON串[{},{},{}...]
				String jsonResult = objectMapper.writeValueAsString(itemCatlist);
				//将数据存入缓存中
				jedisCluster.set(key, jsonResult);
			}else{//表示数据有值,数据不为空,将JSON转化为list<ItemCat>集合对象

				ItemCat[] itemCats = objectMapper.readValue(dataJSON,ItemCat[].class);
				for(ItemCat itemCat:itemCats){
					itemCatlist.add(itemCat);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCatlist;
	}
	//前台商品目录
	@Override
	public ItemCatResult findItemCatAll() {
		ItemCat itemCatTemp = new ItemCat();
		itemCatTemp.setStatus(1);

		List<ItemCat> itemCatAll = itemCatMapper.select(itemCatTemp);
		Map<Long,List<ItemCat>> map=new HashMap<Long,List<ItemCat>>();//用于存放父级菜单
		//1.遍历所有菜单,并将将它们根据父类存入map中
		for(ItemCat itemcat:itemCatAll){
			//1.1.判断是map中是否存有父级id key,如果有将其添加进,父级菜单的list中
			if(map.containsKey(itemcat.getParentId())){
				map.get(itemcat.getParentId()).add(itemcat);
			}else{
				//1.2如果没有则创建一个新的菜单list,并存入map中
				List<ItemCat> tempList = new ArrayList<ItemCat>();
				tempList.add(itemcat);
				map.put(itemcat.getParentId(), tempList);
			}
		}
		//2.获取一级菜单
		List<ItemCatData> itemCatDataList1=new ArrayList<ItemCatData>();
		for(ItemCat itemCat1:map.get(0l)){
			//2.1封装一级菜单
			ItemCatData itemCatData1=new ItemCatData();
			//2.2u设置
			itemCatData1.setUrl("/products/"+itemCat1.getId()+".html");
			//2.3n设置
			itemCatData1.setName("<a href='"+itemCatData1.getUrl()+"'>"+itemCat1.getName()+"</a>");

			//3获取二级菜单
			List<ItemCatData> itemCatDataList2=new ArrayList<>();
			for(ItemCat itemCat2:map.get(itemCat1.getId())){
				//3.1封装二级菜单
				ItemCatData itemCatData2=new ItemCatData();
				//3.2设置u
				itemCatData2.setUrl("/product/"+itemCat2.getId());
				//3.3设置n
				itemCatData2.setName(itemCat2.getName());

				//4.获取3级菜单
				List<String> itemCatDataList3=new ArrayList<>();
				for(ItemCat itemCat3:map.get(itemCat2.getId())){
					itemCatDataList3.add("/products/"+itemCat3.getId()+"|"+itemCat3.getName());
				}					
				//3.4将三级菜单添加进二级菜单数据
				itemCatData2.setItems(itemCatDataList3);
				itemCatDataList2.add(itemCatData2);
			}
			//2.4将二级菜单添加进一级菜单数据中
			itemCatData1.setItems(itemCatDataList2);
			itemCatDataList1.add(itemCatData1);
			//控制菜单的个数 数量达标后跳出循环
			if(itemCatDataList1.size() >13)
				break;
		}
		ItemCatResult itemCatResult = new ItemCatResult();
		//5.将以及菜单封装并返回
		itemCatResult.setItemCats(itemCatDataList1);
		return itemCatResult;
	}

	/**
	 * 编程思想:
	 * 	1.当原有的业务逻辑已经很复杂时,不建议进行二次开发
	 * 	2.当实现自己业务逻辑方法时不要修改别人的方法
	 * 	3.如果业务逻辑相对复杂,尽可能将方法拆分
	 * 	需求:引入缓存技术
	 * 	说明:如果key的关键字是自己单独使用,可以直接写死,如果key需要多人一起
	 * 		则最好依赖spring进行注入.
	 */
	public ItemCatResult findItemCatAllByCache(){
		String jsonDate = jedisCluster.get(ITEM_CAT_KEY);
		ItemCatResult itemCatResult = new ItemCatResult();

		try {
			//判断redis缓存中是否有数据
			if(StringUtils.isEmpty(jsonDate)){
				itemCatResult=findItemCatAll();
				String jsonResult= objectMapper.writeValueAsString(itemCatResult);
				jedisCluster.set(ITEM_CAT_KEY, jsonResult);
			}else{
				//表示JSON中有数据
				itemCatResult=objectMapper.readValue(jsonDate, ItemCatResult.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCatResult;
	}
























}
