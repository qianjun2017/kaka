/**
 * 
 */
package com.cc.carousel.form;

import com.cc.common.web.QueryForm;

/**
 * @author Administrator
 *
 */
public class CarouselQueryForm extends QueryForm {

	
	/**
	 * 轮播图名称
	 */
	private String name;
	
	/**
	 * 轮播图状态
	 */
	private String status;
	
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

}
