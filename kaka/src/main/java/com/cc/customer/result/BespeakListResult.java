package com.cc.customer.result;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BespeakListResult {

	private Long id;
	
	/**
	 * 预约时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bespeakTime;
	
	/**
	 * 汽车顾问姓名
	 */
	private String consultantName;
	
	/**
	 * 经销商名称
	 */
	private String franchiserName;
	
	/**
	 * 会员姓名
	 */
	private String customerName;
	
	/**
	 * 会员手机号
	 */
	private String customerPhone;
	
	/**
	 * 看车地区
	 */
	private String locationName;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the bespeakTime
	 */
	public Date getBespeakTime() {
		return bespeakTime;
	}

	/**
	 * @param bespeakTime the bespeakTime to set
	 */
	public void setBespeakTime(Date bespeakTime) {
		this.bespeakTime = bespeakTime;
	}

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
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * @param customerPhone the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
