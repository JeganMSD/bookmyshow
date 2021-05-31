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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.UserRequest;
import com.jrorg.bookmyshow.request.UserRequest.Role;
import com.jrorg.bookmyshow.util.Utilities;

@JsonInclude(Include.NON_NULL)
@Entity(name ="BMS_Users")
@Table(name="BMS_Users")
public class User implements BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	private String name;
	
	private String phone_number;
	
	private String auth_token;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private List<Booking> bookings;
	
	@JsonIgnore
	private String password;
	
	@Column(columnDefinition="int(10) default '0'")
	private int role;
	
	public Role getRole() {
		return Role.parse(role);
	}

	public void setRole(Role role) {
		this.role = role.role_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	@Override
	public Long getId() {
		return this.id;
	}
	public String getAuthToken() {
		return this.auth_token;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void from(BaseRequest request) {
		UserRequest request2= (UserRequest)request;
		this.id=request2.getId();
		this.name = request2.getName();
		this.phone_number =request2.getPhone_number();
		this.password=request2.getPhone_number();
		if(request2.getRole()!=null) {
			this.setRole(request2.getRole());
		}
	}

	public void setAuthToken(String generateToken) {
		this.auth_token =generateToken;
	}
}
