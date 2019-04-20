/**
 * 
 */
package com.cc.franchiser.form;

import com.cc.common.web.QueryForm;

/**
 * @author Administrator
 *
 */
public class FranchiserQueryForm extends QueryForm {

	/**
	 * 经销商名称
	 */
	private String name;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 地区
	 */
	private Long locationId;

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
}
