package com.jrorg.bookmyshow.request;

public class TheatreRequest extends BaseRequest{
	private String name;
	private Long place_id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPlace_id() {
		return place_id;
	}

	public void setPlace_id(Long place_id) {
		this.place_id = place_id;
	}

}
