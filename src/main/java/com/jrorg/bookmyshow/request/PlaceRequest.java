package com.jrorg.bookmyshow.request;

import java.util.List;

public class PlaceRequest extends BaseRequest{
	private String name;
	private String city;
	private String state;
	private String country;
	private Integer pincode;
	private List<Long> theatre_ids;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public List<Long> getTheatreIds() {
		return theatre_ids;
	}
	public void setTheatreIds(List<Long> theatre_ids) {
		this.theatre_ids = theatre_ids;
	}
}
