package com.cc.customer.form;

import com.cc.common.web.QueryForm;

public class PointsQueryForm extends QueryForm {
	
	/**
	 * 会员
	 */
	private Long customerId;
	
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
}
