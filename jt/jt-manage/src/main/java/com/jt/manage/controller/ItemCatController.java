package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.pojo.ItemCat;
import com.jt.manage.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	/*@RequestMapping("/itemcat/findItemCat")
	@ResponseBody
	public List<ItemCat> findItemCat(){
		return itemCatService.findItemCat();
	}*/
	
	/**商品分类目
	 * 通过@ResponseBody将数据转换为JSON数据时就是调用getxxx()方法
	 * 树控件读取URL。子节点的加载依赖于父节点的状态。当展开一个封闭的节点，如果节点没有加载子节点，
	 * 它将会把节点id的值作为http请求参数并命名为'id'，通过URL发送到服务器上面检索子节点。
	 * 
	 * 实现思路:
	 * 	1.当第一次展开节点信息时由于没有点击节点信息所以不会传递id值
	 *  这时需要一个默认值id=0用来加载以及菜单
	 * 	2.如果点击以及菜单,会将当前的以及菜单的id值进行传递查询当前以及菜单的所有二级菜单
	 * 	3.查询二级菜单的步骤和二级菜单步骤一致
	 * */
	//URL:http://localhost:8091/item/cat/list
	@RequestMapping("/list")
	@ResponseBody
	public List<ItemCat> findItemCat(@RequestParam(value="id",defaultValue="0") Long parentId){
		//Long id=0L;//定义一级菜单,动态传参时去掉
		//表示查询所有一级菜单
		List<ItemCat> itemCatList=itemCatService.findItemCatByParentId(parentId);
		//通过工具类展现JSON
		/*ObjectMapper objectMapper = new ObjectMapper(); 
		try {
			String jsonDate = objectMapper.writeValueAsString(itemCatList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}*/
		return itemCatList;
	}
}
