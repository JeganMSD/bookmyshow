package com.jrorg.bookmyshow.request;

import java.util.List;

public class TicketBookingRequest extends BaseRequest{
	private List<Long> ticket_ids;
	private Long show_id;
	public List<Long> getTicket_ids() {
		return ticket_ids;
	}
	public void setTicket_ids(List<Long> ticket_ids) {
		this.ticket_ids = ticket_ids;
	}
	public Long getShow_id() {
		return show_id;
	}
	public void setShow_id(Long show_id) {
		this.show_id = show_id;
	}
}
