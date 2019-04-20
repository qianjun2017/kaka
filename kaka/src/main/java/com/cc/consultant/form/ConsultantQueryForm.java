/**
 * 
 */
package com.cc.consultant.form;

import com.cc.common.web.QueryForm;

/**
 * @author Administrator
 *
 */
public class ConsultantQueryForm extends QueryForm {

	/**
	 * 汽车顾问名称
	 */
	private String name;
	
	/**
	 * 电话号码
	 */
	private String phone;

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
}
