/**
 * 
 */
package com.cc.wx.http.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ws_yu
 *
 */
public class TemplateListRequest {

	/**
	 * 小程序调用凭证
	 */
	@JsonIgnore
	private String accessToken;
	
	/**
	 * 用于分页，表示从offset开始。从 0 开始计数
	 */
	private Long offset;
	
	/**
	 * 用于分页，表示拉取count条记录。最大为 20。最后一页的list长度可能小于请求的count
	 */
	private Long count;
	
	@JsonIgnore
	private String url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/list";

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the offset
	 */
	public Long getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Long offset) {
		this.offset = offset;
	}

	/**
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
}
