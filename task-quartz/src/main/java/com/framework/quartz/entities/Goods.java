package com.framework.quartz.entities;


public class Goods {
	private static final long serialVersionUID = 5454155825314635342L;

    private Long schSid;
    private String goodsName;
    private Integer goodsType;
    private Integer goodsPrice;
    private String goodsImg;
    private String goodsDesc;
    private Long addBy;
    private Integer status;

    public void setSchSid(Long value) {
		this.schSid = value;
	}
	
	public Long getSchSid() {
		return this.schSid;
	}
	
    public void setGoodsName(String value) {
		this.goodsName = value;
	}
	
	public String getGoodsName() {
		return this.goodsName;
	}
	
    public void setGoodsType(Integer value) {
		this.goodsType = value;
	}
	
	public Integer getGoodsType() {
		return this.goodsType;
	}
	
    public void setGoodsPrice(Integer value) {
		this.goodsPrice = value;
	}
	
	public Integer getGoodsPrice() {
		return this.goodsPrice;
	}
	
    public void setGoodsImg(String value) {
		this.goodsImg = value;
	}
	
	public String getGoodsImg() {
		return this.goodsImg;
	}
	
    public void setGoodsDesc(String value) {
		this.goodsDesc = value;
	}
	
	public String getGoodsDesc() {
		return this.goodsDesc;
	}
	
    public void setAddBy(Long value) {
		this.addBy = value;
	}
	
	public Long getAddBy() {
		return this.addBy;
	}
	
    public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}


	@Override
	public String toString() {
		return "Goods{" +
				"schSid=" + schSid +
				", goodsName='" + goodsName + '\'' +
				", goodsType=" + goodsType +
				", goodsPrice=" + goodsPrice +
				", goodsImg='" + goodsImg + '\'' +
				", goodsDesc='" + goodsDesc + '\'' +
				", addBy=" + addBy +
				", status=" + status +
				'}';
	}

}