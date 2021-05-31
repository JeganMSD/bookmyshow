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
import com.jrorg.bookmyshow.request.TheatreRequest;


@JsonInclude(Include.NON_NULL)
@Entity(name ="BMS_Theatres")
@Table(name="BMS_Theatres",indexes = {
@Index(name = "BMS_Theatres_IDX1", columnList = "name")
})
public class Theatre implements BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	@Column(columnDefinition="VARCHAR(255) NOT NULL")
	private String name;
    @JsonBackReference
	@ManyToOne
	private Place place;
    @JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	@JoinColumn(name = "theatre_id", referencedColumnName = "id")
	private List<Screen> screens;
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
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public List<Screen> getScreens() {
		return screens;
	}
	public void setScreens(List<Screen> screens) {
		this.screens = screens;
	}
	@Override
	public void from(BaseRequest request) {
		TheatreRequest request2 = (TheatreRequest)request;
		this.id=request2.getId();
		if(request2.getPlace_id()!=null) {
			this.place = new Place();
			place.setId(request2.getPlace_id());
		}
		this.name=request2.getName();
	}
	
}
