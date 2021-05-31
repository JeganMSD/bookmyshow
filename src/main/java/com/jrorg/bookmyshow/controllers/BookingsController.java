package com.jrorg.bookmyshow.controllers;

import org.apache.struts2.rest.HttpHeaders;

import com.jrorg.bookmyshow.entity.Booking;
import com.jrorg.bookmyshow.request.BookingRequest;
import com.jrorg.bookmyshow.util.Allowedparams;
import com.jrorg.bookmyshow.util.Mandatoryparams;

public class BookingsController<K extends Booking,V extends BookingRequest> extends BaseController<Booking,BookingRequest>{
	public BookingsController() {
		super(new BookingRequest(), "booking");
	}
	private static final long serialVersionUID = 1L;
	
	public HttpHeaders index() {
		return super.index();
	}

	@Allowedparams(params="booked_time,status,no_of_seats,show_id,user_id")
	public HttpHeaders update() {
		return super.update();
		
	}
	public HttpHeaders destroy() {
		return super.destroy();
	}
	public HttpHeaders show() {
		return super.show();
	}

}
