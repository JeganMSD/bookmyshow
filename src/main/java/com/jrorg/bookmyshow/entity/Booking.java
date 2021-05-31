package com.jrorg.bookmyshow.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.BookingRequest;

@JsonInclude(Include.NON_NULL)
@Entity(name ="BMS_Bookings")
@Table(name="BMS_Bookings")
public class Booking implements BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	@JsonBackReference
    @ManyToOne
	private Show show;
	
	private Long booked_time;
	
	@Column(columnDefinition="int(10) default '0'")
	private Integer status;
	
	private Integer no_of_seats;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	@JoinColumn(name = "booking_id", referencedColumnName = "id")
	private List<Ticket> tickets;
	
	@JsonBackReference
	@ManyToOne
	private User user;
	
	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Long getBooked_time() {
		return booked_time;
	}

	public void setBooked_time(Long booked_time) {
		this.booked_time = booked_time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getNo_of_seats() {
		return no_of_seats;
	}

	public void setNo_of_seats(Integer no_of_seats) {
		this.no_of_seats = no_of_seats;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id =id;
	}

	@Override
	public void from(BaseRequest request) {
		BookingRequest request2 =(BookingRequest)request;
		this.id=request2.getId();
		this.booked_time = request2.getBooked_time();
		if(request2.getStatus()!=null) {
			this.status= request2.getStatus().key;
		}
		this.no_of_seats =request2.getNo_of_seats();
		if(request2.getUser_id()!=null) {
			this.user=new User();
			this.user.setId(id);
		}
		if(request2.getShow_id()!=null) {
			this.show = new Show();
			this.show.setId(id);
		}
		
	}

}
