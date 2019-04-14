package com.cc.customer.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cc.common.tools.StringTools;

public class BespeakQueryForm {
	
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
	 * 页码
	 */
	private String page = "1";
	
	/**
	 * 每页数量
	 */
	private String pageSize = "10";
	
	/**
	 * 排序字段
	 */
	private String sort;
	
	/**
	 * 排序方向
	 */
	private String order;

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
	
	/**
	 * @return the page
	 */
	public int getPage() {
		if(!StringTools.isNullOrNone(this.page) && StringTools.isNumber(this.page)){
			return Integer.parseInt(this.page);
		}
		return 1;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		if(!StringTools.isNullOrNone(this.pageSize) && StringTools.isNumber(this.pageSize)){
			return Integer.parseInt(this.pageSize);
		}
		return 10;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		if(StringTools.isNullOrNone(this.sort)){
			return "bespeakTime";
		}
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		if (StringTools.isNullOrNone(this.order)) {
			return "desc";
		}
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}
}
