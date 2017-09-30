package com.framework.quartz.entities;

import java.util.Date;

public class TeacherAssessLimit  {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long sid;
    private Long schSid;
    private Long tchSid;
    private Long subSid;
    private Long classSid;
    private Integer limit;
    private Integer status;
	private Date createdDt;


	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public void setSchSid(Long value) {
		this.schSid = value;
	}
	
	public Long getSchSid() {
		return this.schSid;
	}
	
    public void setTchSid(Long value) {
		this.tchSid = value;
	}
	
	public Long getTchSid() {
		return this.tchSid;
	}
	
    public void setSubSid(Long value) {
		this.subSid = value;
	}
	
	public Long getSubSid() {
		return this.subSid;
	}
	
    public void setClassSid(Long value) {
		this.classSid = value;
	}
	
	public Long getClassSid() {
		return this.classSid;
	}
	
    public void setLimit(Integer value) {
		this.limit = value;
	}
	
	public Integer getLimit() {
		return this.limit;
	}
	
    public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	@Override
	public String toString() {
		return "TeacherAssessLimit{" +
				"sid=" + sid +
				", schSid=" + schSid +
				", tchSid=" + tchSid +
				", subSid=" + subSid +
				", classSid=" + classSid +
				", limit=" + limit +
				", status=" + status +
				", createdDt=" + createdDt +
				'}';
	}
}