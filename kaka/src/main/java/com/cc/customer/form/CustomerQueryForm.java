/**
 * 
 */
package com.cc.customer.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cc.common.web.QueryForm;

/**
 * @author Administrator
 *
 */
public class CustomerQueryForm extends QueryForm {
	
	/**
	 * 会员姓名
	 */
	private String name;
	
	/**
	 * 会员手机号码
	 */
	private String phone;
	
	/**
	 * 会员卡号
	 */
	private String cardNo;
	
	/**
	 * 会员卡级别
	 */
	private String cardLevel;
	
	/**
	 * 客户状态
	 */
	private String status;
	
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeStart;
	
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeEnd;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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

	/**
	 * @return the cardLevel
	 */
	public String getCardLevel() {
		return cardLevel;
	}

	/**
	 * @param cardLevel the cardLevel to set
	 */
	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}

	/**
	 * @return the createTimeStart
	 */
	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	/**
	 * @param createTimeStart the createTimeStart to set
	 */
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	/**
	 * @return the createTimeEnd
	 */
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	/**
	 * @param createTimeEnd the createTimeEnd to set
	 */
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
}
