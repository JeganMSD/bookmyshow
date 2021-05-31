package com.jrorg.bookmyshow.util;

import java.util.Hashtable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value=Include.NON_NULL)
public class ErrorResponse {
	private String message;
	private String code;
	
	private Hashtable<String,Object> addInfo;
	
	public ErrorResponse(RestAPIException exception){
		this.message=exception.getMessage();
		this.code=exception.getCode();
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@JsonProperty(value="add_info") //no i18n
	public Hashtable<String, Object> getAddInfo() {
		return addInfo;
	}
	public void setAddInfo(Hashtable<String, Object> addInfo) {
		this.addInfo = addInfo;
	}
	public static class RestErrorResponse{
		
		private ErrorResponse error;

		public ErrorResponse getError() {
			return error;
		}

		public void setError(ErrorResponse error) {
			this.error = error;
		}	
	}	
}