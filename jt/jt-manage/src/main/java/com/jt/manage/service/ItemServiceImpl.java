package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;

import redis.clients.jedis.JedisCluster;
@Service
public class ItemServiceImpl implements ItemService {
	@Value("${item_cat_key}")
	private String ITEM_CAT_KEY;
	@Value("${itemkey}")
	private String ITEM_;
	@Value("${item_desc_key}")
	private String ITEM_DESC_;
	@Autowired
	private JedisCluster jedisCluster;
	private static final ObjectMapper objectMapper=new ObjectMapper();

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemCatMapper itemCatMapper;

	@Autowired
	private ItemDescMapper itemDescMapper;
	@Override
	public List<Item> findAll() {

		return itemMapper.findAll();
	}
	//{"total":2000,"rows":[{},{},{}]}
	@Override
	public EasyUIResult findItemByPage(int page, int rows) {
		int total=itemMapper.findItemCount();
		/**
		 * 分页的sql
		 * SELECT * FROM tb_item ORDER BY updated DESC LIMIT 0,5
		 * 通用的参数
		 * 要求:每页展现20条
		 * 第1页:....limit 0,20;
		 * 第2页:....limit 20,20;
		 * ....
		 * 第N页:....limit (page-1)*rows,20;
		 */
		int begin=(page-1)*rows;
		List<Item> items=itemMapper.findItemByPage(begin,rows);
		return new EasyUIResult(total,items);
	}
	//查询商品分类名称
	@Override
	public String findItemCatNameById(Long itemCatId) {

		return itemCatMapper.findItemCatNameById( itemCatId);
	}
	//业务层处理补全数据/新增商品信息
	/**
	 * 思考:
	 * 1.item表的主键是自增的,所以只有当item数据插入才能查询到item的id
	 * 2.如果获取不到item的id只能通过传入的数据进行where条件拼接,但这样的做法不好,不一定满足条件
	 * @param item
	 * @param desc
	 */
	@Override
	public void saveItem(Item item, String desc) {
		//商品表的新增
		item.setStatus(1);//状态为正常
		item.setCreated(new Date());//设定新增时间
		item.setUpdated(item.getCreated());//设定修改时间
		itemMapper.insert(item);


		//新增商品描述表
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);


		try {
			//商品信息存入redis
			String key=ITEM_+item.getId();
			String jsonData= objectMapper.writeValueAsString(item);
			jedisCluster.set(key, jsonData);
			//商品描述存入redis
			String key2=ITEM_DESC_+itemDesc.getItemId();
			String jsonData2= objectMapper.writeValueAsString(itemDesc);
			jedisCluster.set(key2, jsonData2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 动态修过的思路:
	 * 	说明:如果在做修改操作时,若果某项数据为空则会将数据中的原有数据覆盖,这样不好
	 * 动态修改:
	 * 	1.首先判断参数中的数据是否为null,如果数据为null则不参与修改
	 */
	/**
	 * 商品描述修改思路:
	 *  1.信息补全,时间 id 描述
	 *  2.动态更新方式
	 */
	@Override
	public void updateItem(Item item,String desc) {
		//商品表修改
		//设置修改时间
		item.setUpdated(new Date());
		//动态修改
		itemMapper.updateByPrimaryKeySelective(item);




		//商品描述信息修改
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		//删除redis缓存
		String key=ITEM_+item.getId();
		jedisCluster.del(key);

		//删除商品描述redis缓存
		String key2=ITEM_DESC_+itemDesc.getItemId();
		jedisCluster.del(key2);
	}
	//删除商品
	@Override
	public void deleteItem(Long[] ids) {
		//删除商品描述
		itemDescMapper.deleteByIDS(ids);
		for(int i=0;i<ids.length;i++){
			//删除redis缓存
			String key=ITEM_DESC_+ids[i];
			jedisCluster.del(key);
		}



		//删除商品
		itemMapper.deleteByIDS(ids);
		for(int i=0;i<ids.length;i++){
			//删除redis缓存
			String key=ITEM_+ids[i];
			jedisCluster.del(key);
		}
	}
	//更新上架下架
	@Override
	public void updateStatus(Long[] ids, int status) {
		itemMapper.updateStatus(ids, status);
	}
	//查询商品描述
	@Override
	public ItemDesc findItemDesc(Long param) { 
		ItemDesc desc = itemDescMapper.selectByPrimaryKey(param);
		return desc;
	}
	@Override
	public Item findItemById(Long itemId) {
		//System.out.println("进来了!");
		return itemMapper.selectByPrimaryKey(itemId);
	}
	
}
