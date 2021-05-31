package com.jrorg.bookmyshow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.TicketRequest;

@JsonInclude(Include.NON_NULL)
@Entity(name ="BMS_Tickets")
@Table(name="BMS_Tickets")
public class Ticket implements BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	@JsonBackReference
    @ManyToOne
	private Booking booking;
	
	@JsonBackReference
    @ManyToOne
	private Seat seat;
	
	@JsonBackReference
    @ManyToOne
	private Show show;
	
	@Column(columnDefinition="int(10) default '0'")
	private Integer cost;
	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	@Override
	public void from(BaseRequest request) {
		TicketRequest request2 = (TicketRequest)request;
		this.id=request2.getId();
		this.cost=request2.getCost();
		if(request2.getBooking_id()!=null) {
			this.booking = new Booking();
			this.booking.setId(request2.getBooking_id());
		}
		if(request2.getSeat_id()!=null) {
			this.seat = new Seat();
			this.seat.setId(request2.getSeat_id());
		}
		if(request2.getShow_id()!=null) {
			this.show = new Show();
			this.show.setId(request2.getShow_id());
		}
	}

}
