package com.jrorg.bookmyshow.entity;

import java.util.List;

import javax.persistence.CascadeType;
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
import com.jrorg.bookmyshow.request.ShowRequest;

@JsonInclude(Include.NON_NULL)
@Entity(name ="BMS_Shows")
@Table(name="BMS_Shows")
public class Show implements BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	private Long date_millis;
	
	private Long start_time_millis;
	
	private Long end_time_millis;
	
    @JsonBackReference
	@ManyToOne
	private Movie movie;
    
    @JsonBackReference
    @ManyToOne
    private Screen screen;
    
    @JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	@JoinColumn(name = "show_id", referencedColumnName = "id")
    private List<Booking> bookings;
    
    @JsonManagedReference
   	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
   	@JoinColumn(name = "show_id", referencedColumnName = "id")
    private List<Ticket> tickets;
    
	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Long getDate_millis() {
		return date_millis;
	}

	public void setDate_millis(Long date_millis) {
		this.date_millis = date_millis;
	}

	public Long getStart_time_millis() {
		return start_time_millis;
	}

	public void setStart_time_millis(Long start_time_millis) {
		this.start_time_millis = start_time_millis;
	}

	public Long getEnd_time_millis() {
		return end_time_millis;
	}

	public void setEnd_time_millis(Long end_time_millis) {
		this.end_time_millis = end_time_millis;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	@Override
	public void from(BaseRequest request) {
		ShowRequest request2=(ShowRequest) request;
		this.id=request2.getId();
		this.date_millis=request2.getDate_millis();
		this.end_time_millis=request2.getEnd_time_millis();
		this.start_time_millis=request2.getStart_time_millis();
		if(request2.getMovie_id()!=null) {
			this.movie=new Movie();
			this.movie.setId(request2.getMovie_id());
		}
		if(request2.getScreen_id()!=null) {
			this.screen=new Screen();
			this.screen.setId(request2.getScreen_id());
		}
	}

}
