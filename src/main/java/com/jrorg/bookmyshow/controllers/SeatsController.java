package com.jrorg.bookmyshow.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.jrorg.bookmyshow.entity.Seat;
import com.jrorg.bookmyshow.request.SeatRequest;
import com.jrorg.bookmyshow.util.Allowedparams;
import com.jrorg.bookmyshow.util.Mandatoryparams;
import com.jrorg.bookmyshow.util.Permissions;
import com.jrorg.bookmyshow.util.RestAPIUtil;

public class SeatsController<K extends Seat,V extends SeatRequest> extends BaseController<Seat,SeatRequest>{

	public SeatsController() {
		super(new SeatRequest(), "seats");
	}
	private static final long serialVersionUID = 1L;
	private static final List<String> BATCH_ENDPOINTS = Arrays.asList("seats");
	private List<SeatRequest> batch_request = new ArrayList<SeatRequest>();
	@Override
	public Object getModel() {
		
		if(response_object == null) {
			String request_uri = request.getRequestURI();
			if(BATCH_ENDPOINTS.stream().anyMatch(endpoint -> request_uri.endsWith(endpoint))) {
				request.setAttribute("batch_entity_class", SeatRequest.class);
				return batch_request;
			}
		}
		return super.getModel();
	}
	public HttpHeaders index() {
		return super.index();
	}
	@Permissions(adminOnly = "true")
	@Mandatoryparams(params="name,screen_id")
	@Allowedparams(params="name,screen_id")
	public HttpHeaders create() {
		DefaultHttpHeaders httpheaders = new DefaultHttpHeaders("create");    //No I18N
		response.setContentType("application/json; charset=UTF-8");
		try {
			validateParams(this.getClass(),"create",batch_request);
			List<K> entity= (List<K>) manager.batchCreate( batch_request);
			response_object = RestAPIUtil.getSuccessResponse(entity, module_name,request.getRequestURI());
			httpheaders.setStatus(HttpServletResponse.SC_CREATED);
		}
		catch(Exception exception) {
			handleException(exception);
		}
		return new DefaultHttpHeaders("create").disableCaching();
	}
	@Permissions(adminOnly = "true")
	@Allowedparams(params="name,screen_id")
	public HttpHeaders update() {
		return super.update();
	}
	public HttpHeaders show() {
		return super.show();
	}
	@Permissions(adminOnly = "true")
	public HttpHeaders destroy() {
		return super.destroy();
	}

}
