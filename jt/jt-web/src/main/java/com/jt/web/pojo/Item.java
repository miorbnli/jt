package com.jt.web.pojo;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.common.po.BasePojo;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Item extends BasePojo{

	private static final long serialVersionUID = 2918636701220453178L;
	private Long id;//商品ID，也是商品编号
	private String title;//商品标题
	private String sellPoint;//买点
	private Long price;//单位为：分'
	private Integer num;
	private String barcode;
	private String image;//最多5张图片
	private Long cid;
	private Integer status;//默认值为1，可选值：1正常，2下架，3删除
	
	public String[] getImages(){
		return image.split(",");
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
