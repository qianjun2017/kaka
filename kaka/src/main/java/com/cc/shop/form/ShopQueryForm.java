/**
 * 
 */
package com.cc.shop.form;

import com.cc.common.web.QueryForm;

/**
 * @author Administrator
 *
 */
public class ShopQueryForm extends QueryForm {

	/**
	 * 门店名称
	 */
	private String name;
	
	/**
	 * 门店代码
	 */
	private String code;
	
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
