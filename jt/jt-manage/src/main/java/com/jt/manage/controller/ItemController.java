package com.jt.manage.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	//定义logger对象方便日志输出
	private static final Logger logger=Logger.getLogger(ItemController.class);
	@RequestMapping("/findAll")
	@ResponseBody//将数据转换为JSON
	public List<Item> findAll(){
		return itemService.findAll();
	}
	
	/**
	 * http://localhost:8091/item/query?page=1&rows=20
	 *规定:
	 *	如果使用easyUI进行分页操作则返回值必须满足easyUI的格式否则将不能实现正常操作
	 *结构:
	 *	{"total":2000,"rows":[{},{},{}]}
	 *	total 是记录总数
	 *	rows表示查询的数据
	 *补充:
	 *	JSON格式一般有3种
	 *	1.Map 2.Array 3.复杂格式(前两种格式嵌套)
	 */
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemByPage(int page,int rows){
		return itemService.findItemByPage(page,rows);
	}
	
	//url:http://localhost:8091/item/cat/queryItemName
	/**
	 * 配置了全站乱码解决,为什么还要配置utf-8
	 * ajax是一部提交,在源码中有两种不同配置
	 * 其中一个定义了utf-8编码,另外一个工具类中定义了GBK
	 * 如果不是异步提交则通过过滤器后设置utf-8保证数据有效
	 * @param itemId
	 * @param response
	 */
	//采用response方式回传数据
	/*@RequestMapping("cat/queryItemName")
	public void queryItemCatName(Long itemId,HttpServletResponse response){

		String name = itemService.findItemCatNameById(itemId);
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	//通过@ResponseBody定义utf-8编码格式
	@RequestMapping(value="cat/queryItemName",produces="text/html;charset=utf-8")
	@ResponseBody
	public String queryItemCatName(Long itemId,HttpServletResponse response){
		return itemService.findItemCatNameById(itemId);
	}
	
	/**
	 * 新增商品
	 * 思路:
	 * 	1.编辑请求接收
	 * 	2.接收form表单数据
	 * 	3.新增商品信息
	 * 		3.1调用service
	 * 		3.2调用通用mapper实现入库操作
	 * 	4.返回json数据并且返回状态码
	 * 
	 */
	/**
	 * 说明:
	 * 	由于数据表的定义不能将desc属性直接写到Item对象中,所以通过String desc参数的接收
	 * 新增思路:
	 * 	1.添加新增参数实现数据的传递String desc
	 * 	2.同构controller调用servic完成多表同时操作
	 * 		注意事项:1.如果是多张表同时更新,切记事务控制
	 * 			  2.如果使用spring控制事务,切记方法名迎合事务策略一致
	 * 
	 * @param item
	 * @param desc
	 * @return
	 */
	//URL:http://localhost:8091/item/save
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc){
		try {
			itemService.saveItem(item,desc);
			logger.info("~~~~~~~~~~~~~~~商品新增成功"+item.getId());
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!!!!!"+e.getMessage());
			return SysResult.build(201,"新增商品失败!");
		}
		
	}
	/**商品描述查询*/
	 // URL:http://localhost:8091/item/query/item/desc/1474391956
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult queryItemDesc(@PathVariable Long itemId){		
		try {
			ItemDesc desc = itemService.findItemDesc(itemId);
			//添加成功日志
			logger.info("~~~~~~~~~~~~~~~商品描述查询成功");
			return SysResult.oK(desc);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!!!!!"+e.getMessage());
			return SysResult.build(201,"商品描述查询失败!");
		}
	}
	/**商品规格查询*/
	 // URL:http://localhost:8091/item/param/item/query/1474391956
	//商品规格表:tb_item_param_item
	@RequestMapping("/param/item/query/{itemId}")
	@ResponseBody
	public SysResult queryItemParamItem(@PathVariable Long itemId){
		try {
			//添加成功日志
			logger.info("~~~~~~~~~~~~~~~商品规格查询成功");
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!!!!!"+e.getMessage());
			return SysResult.build(201,"商品规格查询失败!");
		}
	}
	/**
	 * 更新商品信息
	 */
	//URL:http://localhost:8091/item/update
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc){
		try {
			itemService.updateItem(item,desc);
			//添加成功日志
			logger.info("~~~~~~~~~~~~~~~商品修改成功"+item.getId());
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!!!!!"+e.getMessage());
			return SysResult.build(201,"修改商品失败!");
		}
	}
	/**删除商品信息*/
	//URL:http://localhost:8091/item/delete
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids){
		try {
			itemService.deleteItem(ids);
			
			//添加成功日志
			logger.info("~~~~~~~~~~~~~~~商品删除成功"+Arrays.toString(ids));
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!!!!!"+e.getMessage());
			return SysResult.build(201,"删除商品失败!");
		}
	}
	/**商品上架*/
	//URL:http://localhost:8091/item/reshelf
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult updateReshelf(Long[] ids){
		try {
			int status=1;
			itemService.updateStatus(ids,status);
			//添加成功日志
			logger.info("~~~~~~~~~~~~~~~商品上架成功"+Arrays.toString(ids));
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!!!!!"+e.getMessage());
			return SysResult.build(201,"商品上架失败!");
		}
	}
	/**商品下架*/
	//URL:http://localhost:8091/item/instock
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult updateInstock(Long[] ids){
		try {
			int status=2;
			itemService.updateStatus(ids,status);
			//添加成功日志
			logger.info("~~~~~~~~~~~~~~~商品下架成功"+Arrays.toString(ids));
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("!!!!!!!!!!"+e.getMessage());
			return SysResult.build(201,"商品下架失败!");
		}
	}
	
	
	
	
	
	
	
	
	
	
}
