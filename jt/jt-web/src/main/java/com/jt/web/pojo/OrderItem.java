package com.jt.web.pojo;

import java.io.Serializable;


//对象最好序列化
public class OrderItem implements Serializable{

	private static final long serialVersionUID = -6022401382427831293L;
	private String itemId;//商品id
	private String OrderId;//订单编号
	private Integer num;//购买数量
	private String title;//商品标题
	private Long price ;//商品价格
	private String totalFee;//商品总价
	private String picPath;//图片路径
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	@Override
	public String toString() {
		return "OrderItem [itemId=" + itemId + ", OrderId=" + OrderId + ", num=" + num + ", title=" + title + ", price="
				+ price + ", totalFee=" + totalFee + ", picPath=" + picPath + "]";
	}
	
}
