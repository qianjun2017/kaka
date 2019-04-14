/**
 * 
 */
package com.cc.customer.result;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
public class BespeakResult {

	private Long id;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
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
	 * 汽车顾问手机号
	 */
	private String consultantPhone;
	
	/**
	 * 经销商名称
	 */
	private String franchiserName;
	
	/**
	 * 经销商详细地址
	 */
	private String address;
	
	/**
	 * 会员姓名
	 */
	private String customerName;
	
	/**
	 * 会员手机号
	 */
	private String customerPhone;
	
	/**
	 * 会员积分
	 */
	private Long points;
	
	/**
	 * 会员等级
	 */
	private String level;
	
	/**
	 * 会员卡号
	 */
	private String cardNo;

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
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @return the consultantPhone
	 */
	public String getConsultantPhone() {
		return consultantPhone;
	}

	/**
	 * @param consultantPhone the consultantPhone to set
	 */
	public void setConsultantPhone(String consultantPhone) {
		this.consultantPhone = consultantPhone;
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the points
	 */
	public Long getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(Long points) {
		this.points = points;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}
