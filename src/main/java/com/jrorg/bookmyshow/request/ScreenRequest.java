package com.jrorg.bookmyshow.request;


public class ScreenRequest extends BaseRequest{
	private String name;
	private Long theatre_id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTheatre_id() {
		return theatre_id;
	}

	public void setTheatre_id(Long theatre_id) {
		this.theatre_id = theatre_id;
	}
}
