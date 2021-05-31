package com.jrorg.bookmyshow.util;

import java.lang.reflect.Field;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.jrorg.bookmyshow.entity.BaseEntity;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(value={"error","empty"}) //no i18n
public class RestResponse {
	
	private BaseEntity data;
	private List<? extends BaseEntity> list;
	private String url;
	private String object;
	private boolean hasError;

	public boolean hasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(BaseEntity data) {
		this.data = data;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	

	public boolean isEmpty() 
	{
		boolean isEmpty = true;
		try {

			for (Field field : this.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.get(this) != null) {
					isEmpty = false;
					break;
				}

			}
		} catch (Exception e) {}

		return isEmpty;
	}

	public List<? extends BaseEntity> getList() {
		return list;
	}
	public void setList(List<? extends BaseEntity> data) {
		this.list=data;
	}
}