/**
 * 
 */
package com.cc.customer.bean;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
@Table(name="t_bespeak")
public class BespeakBean extends BaseOrm<BespeakBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 265118980720697495L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 会员
	 */
	private Long customerId;
	
	/**
	 * 经销商
	 */
	private Long franchiserId;
	
	/**
	 * 汽车顾问
	 */
	private Long consultantId;
	
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
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the franchiserId
	 */
	public Long getFranchiserId() {
		return franchiserId;
	}

	/**
	 * @param franchiserId the franchiserId to set
	 */
	public void setFranchiserId(Long franchiserId) {
		this.franchiserId = franchiserId;
	}

	/**
	 * @return the consultantId
	 */
	public Long getConsultantId() {
		return consultantId;
	}

	/**
	 * @param consultantId the consultantId to set
	 */
	public void setConsultantId(Long consultantId) {
		this.consultantId = consultantId;
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
}
