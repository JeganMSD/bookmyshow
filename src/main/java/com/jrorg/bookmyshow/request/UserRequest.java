package com.jrorg.bookmyshow.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class UserRequest extends BaseRequest{
	
	public static enum Role{
		@JsonProperty("admin")
		ADMIN(1),
		@JsonProperty("user")
		USER(2);
		public final int role_code;
		Role(int role_code){
			this.role_code =role_code;
		}
		public static Role parse(int role_code) {
			Role role =null;
			for(Role current_role : Role.values()) {
				if(current_role.role_code == role_code) {
					role = current_role;
					break;
				}
			}
			return role;
		}
	}
	
	private String name;
	
	private String password;
	
	private String phone_number;
	
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
}
