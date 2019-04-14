package com.cc.franchiser.result;

public class FranchiserResult {

	private Long id;
	
	/**
	 * 经销商名称
	 */
	private String name;
	
	/**
	 * 经销商所在地区
	 */
	private Long locationId;
	
	/**
	 * 经销商所在地区
	 */
	private String locationName;
	
	/**
	 * 经销商详细地址
	 */
	private String address;
	
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
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
