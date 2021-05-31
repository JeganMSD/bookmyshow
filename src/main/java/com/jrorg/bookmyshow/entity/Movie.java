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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.MovieRequest;

@JsonInclude(Include.NON_NULL)
@Entity(name ="BMS_Movies")
@Table(name="BMS_Movies")
public class Movie implements BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	@Column(columnDefinition="VARCHAR(255) NOT NULL")
	private String name;
	
	private String genre;
	
	private String language;	
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
	private List<Show> shows;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	@Override
	public void from(BaseRequest request) {
		MovieRequest request2 = (MovieRequest)request;
		this.id=request2.getId();
		this.genre = request2.getGenre();
		this.language = request2.getLanguage();
		this.name = request2.getName();	
	}

}
