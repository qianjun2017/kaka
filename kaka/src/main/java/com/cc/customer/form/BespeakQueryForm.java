package com.cc.customer.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cc.common.web.QueryForm;

public class BespeakQueryForm extends QueryForm {
	
	/**
	 * 汽车顾问姓名
	 */
	private String consultantName;
	
	/**
	 * 经销商名称
	 */
	private String franchiserName;
	
	/**
	 * 经销商区域
	 */
	private Long locationId;

	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bespeakTimeStart;
	
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bespeakTimeEnd;

	/**
	 * @return the consultantName
	 */
	public String getConsultantName() {
		return consultantName;
	}

	/**
	 * @param consultantName the consultantName to set
	 */
	public void setConsultantName(String consultantName) {
		this.consultantName = consultantName;
	}

	/**
	 * @return the franchiserName
	 */
	public String getFranchiserName() {
		return franchiserName;
	}

	/**
	 * @param franchiserName the franchiserName to set
	 */
	public void setFranchiserName(String franchiserName) {
		this.franchiserName = franchiserName;
	}

	/**
	 * @return the locationId
	 */
	public Long getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the bespeakTimeStart
	 */
	public Date getBespeakTimeStart() {
		return bespeakTimeStart;
	}

	/**
	 * @param bespeakTimeStart the bespeakTimeStart to set
	 */
	public void setBespeakTimeStart(Date bespeakTimeStart) {
		this.bespeakTimeStart = bespeakTimeStart;
	}

	/**
	 * @return the bespeakTimeEnd
	 */
	public Date getBespeakTimeEnd() {
		return bespeakTimeEnd;
	}

	/**
	 * @param bespeakTimeEnd the bespeakTimeEnd to set
	 */
	public void setBespeakTimeEnd(Date bespeakTimeEnd) {
		this.bespeakTimeEnd = bespeakTimeEnd;
	}
}
