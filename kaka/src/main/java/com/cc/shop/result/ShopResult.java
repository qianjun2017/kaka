/**
 * 
 */
package com.cc.shop.result;

/**
 * @author Administrator
 *
 */
public class ShopResult {
	
private Long id;
	
	/**
	 * 门店名称
	 */
	private String name;
	
	/**
	 * 经度
	 */
	private String longitude;
	
	/**
	 * 纬度
	 */
	private String latitude;
	
	/**
	 * 误差半径
	 */
	private Long radius;
	
	/**
	 * 所在区域
	 */
	private Long locationId;
	
	/**
	 * 门店详细地址
	 */
	private String address;
	
	/**
	 * 门店代码
	 */
	private String code;
	
	/**
	 * 门店所在区域
	 */
	private Long[] locationArray;

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
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the radius
	 */
	public Long getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(Long radius) {
		this.radius = radius;
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
	 * @return the locationArray
	 */
	public Long[] getLocationArray() {
		return locationArray;
	}

	/**
	 * @param locationArray the locationArray to set
	 */
	public void setLocationArray(Long[] locationArray) {
		this.locationArray = locationArray;
	}
}
