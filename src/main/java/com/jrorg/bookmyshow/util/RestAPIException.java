package com.jrorg.bookmyshow.util;

public class RestAPIException extends Exception{
	public static enum Type{
		OPERATION_FAILED("GE_101","Internal server error ocured"),
		MISSING_PARAM("GE_102","Parameter $1 is missing"),
		MISSING_PARAM_WITH_VALUES("GE_103","Parameter $1 is missing.Possible values are $2"),
		MISSING_JSON_KEY("GE_104","Parameter $1 is missing"),
		MISSING_JSON_KEY_WITH_VALUES("GE_105","Parameter $1 is missing.Possible values are $2"),
		INVALID_RESOURCE_ID("GE_105","The resource Id for $1 is invalid"),
		UNAUTHORISED("GE_100","Unauthorised request"), 
		ALREADY_RESERVED("GE_106","Already reserved by some other user");
		public final String code,message;
		Type(String code,String message){
			this.code =code;
			this.message = message;
		}
	}
	private final String code;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public RestAPIException(Type type){
		this.code=type.code;
		this.message=type.message;
	}
	public RestAPIException(Type type,String affected_param,Object values){
		this(type);
		if(affected_param != null) {
			this.message = this.message.replace("$1", affected_param);
		}
		if(values != null) {
			this.message = this.message.replace("$2", values.toString());
		}
	}
}
