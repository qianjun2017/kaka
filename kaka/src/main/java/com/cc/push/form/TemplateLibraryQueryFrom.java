package com.cc.push.form;

import com.cc.common.tools.StringTools;

public class TemplateLibraryQueryFrom {

	/**
	 * 页码
	 */
	private String page = "1";
	
	/**
	 * 每页数量
	 */
	private String pageSize = "10";
	
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
}
