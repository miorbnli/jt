package com.jt.web.pojo;


import com.jt.common.po.BasePojo;

public class Cart extends BasePojo {

	private static final long serialVersionUID = -6092636696689166722L;
	private Long id;
	private Long userId;
	private Long itemId;
	private String itemTitle;
	private String itemImage;//商品首图
	private Integer num;//商品数量
	private Long itemPrice;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Long getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Long itemPrice) {
		this.itemPrice = itemPrice;
	}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", userId=" + userId + ", itemId=" + itemId + ", itemTitle=" + itemTitle
				+ ", itemImage=" + itemImage + ", num=" + num + ", itemPrice=" + itemPrice + "]";
	}
	
	
	
}
