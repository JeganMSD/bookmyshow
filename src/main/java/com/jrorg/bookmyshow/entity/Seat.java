package com.jrorg.bookmyshow.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.SeatRequest;


@JsonInclude(Include.NON_NULL)
@Entity(name="BMS_Seats")
@Table(name="BMS_Seats",indexes = {
@Index(name = "BMS_Seats_IDX1", columnList = "name")
})
public class Seat implements BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	@Column(columnDefinition="VARCHAR(255) NOT NULL")
	private String name;
	
	@JsonBackReference
    @ManyToOne
	private Screen screen;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	@JoinColumn(name = "seat_id", referencedColumnName = "id")
	private List<Ticket> tickets;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Screen getScreen() {
		return screen;
	}
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	@Override
	public void from(BaseRequest request) {
		SeatRequest request2 = (SeatRequest)request;
		this.id=request2.getId();
		if(request2.getScreen_id()!=null) {
			this.screen = new Screen();
			this.screen.setId(request2.getScreen_id());
		}
		this.name=request2.getName();
	}
	public List<Ticket> getTickets() {
		return tickets;
	}
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
}
