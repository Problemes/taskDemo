package com.framework.quartz.entities;


public class School {
	private static final long serialVersionUID = 5454155825314635342L;
			
    private String schNo;
    private String schName;
    private String schIntro;
    private String schMotto;
    private String schDeclare;
    private String schIcon;
    private String provinceCode;
    private String cityCode;
    private String countryCode;
    private String schAdr;
    private String schDesc;
    private Integer status;

    public void setSchNo(String value) {
		this.schNo = value;
	}
	
	public String getSchNo() {
		return this.schNo;
	}
	
    public void setSchName(String value) {
		this.schName = value;
	}
	
	public String getSchName() {
		return this.schName;
	}
	
    public void setSchIntro(String value) {
		this.schIntro = value;
	}
	
	public String getSchIntro() {
		return this.schIntro;
	}
	
    public void setSchMotto(String value) {
		this.schMotto = value;
	}
	
	public String getSchMotto() {
		return this.schMotto;
	}
	
    public void setSchDeclare(String value) {
		this.schDeclare = value;
	}
	
	public String getSchDeclare() {
		return this.schDeclare;
	}
	
    public void setSchIcon(String value) {
		this.schIcon = value;
	}
	
	public String getSchIcon() {
		return this.schIcon;
	}
	
    public void setProvinceCode(String value) {
		this.provinceCode = value;
	}
	
	public String getProvinceCode() {
		return this.provinceCode;
	}
	
    public void setCityCode(String value) {
		this.cityCode = value;
	}
	
	public String getCityCode() {
		return this.cityCode;
	}
	
    public void setCountryCode(String value) {
		this.countryCode = value;
	}
	
	public String getCountryCode() {
		return this.countryCode;
	}
	
    public void setSchAdr(String value) {
		this.schAdr = value;
	}
	
	public String getSchAdr() {
		return this.schAdr;
	}
	
    public void setSchDesc(String value) {
		this.schDesc = value;
	}
	
	public String getSchDesc() {
		return this.schDesc;
	}
	
    public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}


	@Override
	public String toString() {
		return "School{" +
				"schNo='" + schNo + '\'' +
				", schName='" + schName + '\'' +
				", schIntro='" + schIntro + '\'' +
				", schMotto='" + schMotto + '\'' +
				", schDeclare='" + schDeclare + '\'' +
				", schIcon='" + schIcon + '\'' +
				", provinceCode='" + provinceCode + '\'' +
				", cityCode='" + cityCode + '\'' +
				", countryCode='" + countryCode + '\'' +
				", schAdr='" + schAdr + '\'' +
				", schDesc='" + schDesc + '\'' +
				", status=" + status +
				'}';
	}

}