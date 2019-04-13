/**
 * 
 */
package com.cc.api.bean;

/**
 * @author Administrator
 *
 */
public class RequestBean {

	/**
	 * 应用
	 */
	private String appCode;
	
	/**
	 * 请求时间错
	 */
	private Long timestamp;
	
	/**
	 * 接口版本
	 */
	private String version;
	
	/**
	 * 参数签名
	 */
	private String sign;
	
	/**
	 * 加密参数
	 */
	private String eValue;

	/**
	 * @return the appCode
	 */
	public String getAppCode() {
		return appCode;
	}

	/**
	 * @param appCode the appCode to set
	 */
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @return the eValue
	 */
	public String geteValue() {
		return eValue;
	}

	/**
	 * @param eValue the eValue to set
	 */
	public void seteValue(String eValue) {
		this.eValue = eValue;
	}
}
