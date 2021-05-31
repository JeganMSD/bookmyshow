package com.jrorg.bookmyshow.request;

public class TicketRequest extends BaseRequest{
	private Long booking_id;
	private Long seat_id;
	private Long show_id;
	private Integer cost;
	public Long getBooking_id() {
		return booking_id;
	}
	public void setBooking_id(Long booking_id) {
		this.booking_id = booking_id;
	}
	public Long getSeat_id() {
		return seat_id;
	}
	public void setSeat_id(Long seat_id) {
		this.seat_id = seat_id;
	}
	public Long getShow_id() {
		return show_id;
	}
	public void setShow_id(Long show_id) {
		this.show_id = show_id;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
}
