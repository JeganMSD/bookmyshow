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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.ScreenRequest;


@JsonInclude(Include.NON_NULL)
@Entity(name="BMS_Screens")
@Table(name="BMS_Screens")
public class Screen implements BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	
	@Column(columnDefinition="VARCHAR(255) NOT NULL")
	private String name;
	
	
    @JsonBackReference
	@ManyToOne
	private Theatre theatre;
    
    
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	@JoinColumn(name = "screen_id", referencedColumnName = "id")
    @JsonManagedReference
    private List<Seat> seats;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	@JoinColumn(name = "screen_id", referencedColumnName = "id")
    @JsonManagedReference
    private List<Show> shows;
	
	
	public List<Show> getShows() {
		return shows;
	}
	public void setShows(List<Show> shows) {
		this.shows = shows;
	}
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
	public Theatre getTheatre() {
		return theatre;
	}
	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	@Override
	public void from(BaseRequest request) {
		ScreenRequest request2 = (ScreenRequest)request;
		this.id=request2.getId();
		if(request2.getTheatre_id()!=null) {
			this.theatre = new Theatre();
			theatre.setId(request2.getTheatre_id());
		}
		this.name=request2.getName();
	}
	
}
