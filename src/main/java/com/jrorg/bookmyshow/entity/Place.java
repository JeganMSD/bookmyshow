package com.jrorg.bookmyshow.entity;

import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.PlaceRequest;

@JsonInclude(Include.NON_NULL)
@Entity(name ="BMS_Places")
@Table(name="BMS_Places",indexes = {
@Index(name = "BMS_Places_IDX1", columnList = "name,city,state,country"),
@Index(name = "BMS_Places_IDX2", columnList = "city,state,country"),
@Index(name = "BMS_Places_IDX3", columnList = "state,country"),
@Index(name = "BMS_Places_IDX4", columnList = "country")
})
public class Place implements BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	@Column(columnDefinition="VARCHAR(255) NOT NULL")
	private String name;
	@Column(columnDefinition="VARCHAR(255) NOT NULL")
	private String city;
	@Column(columnDefinition="VARCHAR(255) NOT NULL")
	private String state;
	@Column(columnDefinition="VARCHAR(255) NOT NULL")
	private String country;
	@Column(nullable = false)
	private Integer pincode;
    @JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	@JoinColumn(name = "place_id", referencedColumnName = "id")
	private List<Theatre> theatres;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public List<Theatre> getTheatres() {
		return theatres;
	}
	public void setTheatres(List<Theatre> theatres) {
		this.theatres = theatres;
	}
	@Override
	public void from(BaseRequest request) {
		PlaceRequest request2 = (PlaceRequest)request;
		this.id=request2.getId();
		this.city=request2.getCity();
		this.country=request2.getCountry();
		this.pincode=request2.getPincode();
		this.state=request2.getState();
		this.name=request2.getName();
		if(request2.getTheatreIds()!=null) {
			List<Theatre> theatres=new ArrayList<Theatre>();
			request2.getTheatreIds().forEach((id)->{
				Theatre theatre= new Theatre();
				theatre.setId(id);
				theatres.add(theatre);
			});
			this.theatres=theatres;
		}
		
	}
}
