package com.lendingtree.model;


public class CustomerAddress {

	private String areaCode;
	
	private String city;
	
	private String countryCode;

	private String countryName;
	
	private String dmaCode;
	
	private String latitude;
	
	private String longitude;

	private String metroCode;
	
	private String postalCode;
	
	private String region;
	
	private String regionName;
	
	private String ipAddress;

	public CustomerAddress(String areaCode, String city, String countryCode,
			String countryName, String dmaCode, String latitude,
			String longitude, String metroCode, String postalCode,
			String region, String regionName, String ipAddress) {
		super();
		this.areaCode = areaCode;
		this.city = city;
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.dmaCode = dmaCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.metroCode = metroCode;
		this.postalCode = postalCode;
		this.region = region;
		this.regionName = regionName;
		this.ipAddress = ipAddress;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getDmaCode() {
		return dmaCode;
	}

	public void setDmaCode(String dmaCode) {
		this.dmaCode = dmaCode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getMetroCode() {
		return metroCode;
	}

	public void setMetroCode(String metroCode) {
		this.metroCode = metroCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getIpAddress() {
		return ipAddress;
	}
	
}
