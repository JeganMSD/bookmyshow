package com.jrorg.bookmyshow.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingRequest extends BaseRequest {
	
	public static enum BookingStates{
		@JsonProperty("reserved")
		RESERVED(0),
		@JsonProperty("booked")
		BOOKED(1),
		@JsonProperty("canceled")
		CANCELED(2),
		@JsonProperty("payment_failed")
		PAYMENT_FALED(3),
		@JsonProperty("expired")
		EXPIRED(4);
		
		public final int key;
		BookingStates(int key){
			this.key = key;
		}
	}
	
	private Long booked_time;
	
	private BookingStates status;
	
	private int no_of_seats;
	
	private Long show_id;
	
	private Long user_id;

	public Long getBooked_time() {
		return booked_time;
	}

	public void setBooked_time(Long booked_time) {
		this.booked_time = booked_time;
	}

	public BookingStates getStatus() {
		return status;
	}

	public void setStatus(BookingStates status) {
		this.status = status;
	}

	public int getNo_of_seats() {
		return no_of_seats;
	}

	public void setNo_of_seats(int no_of_seats) {
		this.no_of_seats = no_of_seats;
	}

	public Long getShow_id() {
		return show_id;
	}

	public void setShow_id(Long show_id) {
		this.show_id = show_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
		
}
