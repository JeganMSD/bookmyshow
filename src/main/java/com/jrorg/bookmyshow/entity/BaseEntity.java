package com.jrorg.bookmyshow.entity;

import com.jrorg.bookmyshow.request.BaseRequest;

public interface BaseEntity {
	public Long getId();
	public void setId(Long id);
	public void from(BaseRequest request);
}
