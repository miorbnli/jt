package com.jt.manage.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

import com.jt.common.po.BasePojo;
@Table(name="tb_item_desc")
public class ItemDesc extends BasePojo {

	private static final long serialVersionUID = -263369805754629071L;
	@Id//主键
	//不需要主键自增,通过代码自动赋值
	private Long itemId;//商品id
	private String itemDesc;//商品描述
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	
}
