package com.jrorg.bookmyshow.util;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrorg.bookmyshow.entity.BaseEntity;

public class RestAPIUtil {
	public static RestResponse getSuccessResponse(BaseEntity respdata, String type, String url)
	{
		RestResponse response = new RestResponse();
		response.setData(respdata);
		response.setUrl(url);
		response.setObject(type);
		return response;
	}
	public static RestResponse getSuccessResponse(List<? extends BaseEntity> respdata, String type, String url)
	{
		RestResponse response = new RestResponse();
		ObjectMapper mapper = new ObjectMapper();

		response.setList(respdata);
		response.setUrl(url);
		response.setObject(type);
		return response;
	}
}
