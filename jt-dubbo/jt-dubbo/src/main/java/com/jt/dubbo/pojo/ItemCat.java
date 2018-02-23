package com.jt.dubbo.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.common.po.BasePojo;
/**
 * 通用Mapper的配置:
 * 	1.匹配对应的表
 *  2.匹配id
 *  3.如果是自增需要设定
 * @author tarena
 *
 */
@Table(name="tb_item_cat")
//忽略为止属性,在爬虫时经常使用
@JsonIgnoreProperties(ignoreUnknown=true)
public class ItemCat extends BasePojo {

	private static final long serialVersionUID = 5292876094351592104L;
	@Id//主键定义
	@GeneratedValue(strategy=GenerationType.IDENTITY)//主键自增
	private Long id;//商品分类id
	private Long parentId;//父级id
	private String name;//名称
	private Integer status;//1 正常,2 删除
	private Integer sortOrder;//排序号
	private Boolean isParent;//定义是否为上级
	
	/**
	 * 为了实现树形结构满足格式,添加getxxx方法
	 * @return
	 */
	public String getText(){
		return name;
	}
	/**
	 * 控制节点是否关闭
	 * @return
	 */
	public String getState(){
		return isParent ?"closed":"open";//时父级返回close
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
}
