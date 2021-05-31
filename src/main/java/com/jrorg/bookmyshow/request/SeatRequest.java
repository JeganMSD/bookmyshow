package com.jrorg.bookmyshow.request;


public class SeatRequest extends BaseRequest{
	private String name;
	private Long screen_id;
	private Boolean status;
	private Integer cost;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getScreen_id() {
		return screen_id;
	}

	public void setScreen_id(Long screen_id) {
		this.screen_id = screen_id;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

}
