package com.jt.dubbo.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jt.common.po.BasePojo;

@Table(name="tb_order")
public class Order extends BasePojo{

	private static final long serialVersionUID = -8843687479617636672L;
	//一对多关联关系
	@Transient//忽略该字段,该注解应用于mybatis
	private List<OrderItem> orderItems;
	//一对一
	@Transient
	private OrderShipping orderShipping;
	
	@Id
	private String orderId;//订单号
	private String payment;//实付金额
	private Integer paymentType;//支付类型:1 在线支付,2 货到付款
	private String postFee;//邮费
	private Integer status;//
	private Date paymentTime;//支付时间
	private Date consignTime;//发货时间
	private Date endTime;//交易完成时间
	private Date closeTime;//交易关闭时间
	private String shippingName;//物流名称
	private Long userId;//用户ID
	private String buyerMessage;//卖家留言
	private String buyerRate;//是否评价
	private Integer buyerNick;//买家呢称
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public OrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(OrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getPostFee() {
		return postFee;
	}
	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Date getConsignTime() {
		return consignTime;
	}
	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBuyerMessage() {
		return buyerMessage;
	}
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}
	public String getBuyerRate() {
		return buyerRate;
	}
	public void setBuyerRate(String buyerRate) {
		this.buyerRate = buyerRate;
	}
	public Integer getBuyerNick() {
		return buyerNick;
	}
	public void setBuyerNick(Integer buyerNick) {
		this.buyerNick = buyerNick;
	}
	@Override
	public String toString() {
		return "Order [orderItems=" + orderItems + ", orderShipping=" + orderShipping + ", orderId=" + orderId
				+ ", payment=" + payment + ", paymentType=" + paymentType + ", postFee=" + postFee + ", status="
				+ status + ", paymentTime=" + paymentTime + ", consignTime=" + consignTime + ", endTime=" + endTime
				+ ", closeTime=" + closeTime + ", shippingName=" + shippingName + ", userId=" + userId
				+ ", buyerMessage=" + buyerMessage + ", buyerRate=" + buyerRate + ", buyerNick=" + buyerNick + "]";
	}
	
	
	
	
}
