package com.jrorg.bookmyshow.controllers;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.jrorg.bookmyshow.entity.Booking;
import com.jrorg.bookmyshow.entity.User;
import com.jrorg.bookmyshow.manager.UsersManager;
import com.jrorg.bookmyshow.request.TicketBookingRequest;
import com.jrorg.bookmyshow.request.UserRequest;
import com.jrorg.bookmyshow.util.Allowedparams;
import com.jrorg.bookmyshow.util.Mandatoryparams;
import com.jrorg.bookmyshow.util.Permissions;
import com.jrorg.bookmyshow.util.RestAPIUtil;

@AllowedMethods({"bookings"})
public class UsersController<K extends User,V extends UserRequest> extends BaseController<User, UserRequest>{
	private TicketBookingRequest ticket_request = new TicketBookingRequest();
	public UsersController() {
		super(new UserRequest(), "user");
	}
	@Override
	public Object getModel() {
		
		if(response_object == null) {
			String request_uri = request.getRequestURI();
			if(request_uri.endsWith("bookings")) {
				return ticket_request;
			}
		}
		return super.getModel();
	}
	private static final long serialVersionUID = 1L;
	@Permissions(adminOnly = "true")
	public HttpHeaders index() {
		return super.index();
	}
	
	@Mandatoryparams(params="name,password,phone_number,role")
	@Allowedparams(params="name,password,phone_number,role")
	public HttpHeaders create() {
		return super.create();
	}

	@Allowedparams(params="name,password,phone_number,role")
	public HttpHeaders update() {
		return super.update();
		
	}
	
	@Permissions(adminOnly = "true")
	public HttpHeaders destroy() {
		return super.destroy();
	}


	@Mandatoryparams(params="ticket_ids,show_id")
	@Allowedparams(params="ticket_ids,show_id")
	@Permissions(sameUser = "true")
	public HttpHeaders bookings() {
		DefaultHttpHeaders httpheaders = new DefaultHttpHeaders("create");    //No I18N
		response.setContentType("application/json; charset=UTF-8");
		try {
			ticket_request.setId(Long.parseLong(this.getId()));
			validateParams(this.getClass(),"bookings",ticket_request);
			Booking booking = new UsersManager().bookTickets( ticket_request);
			response_object = RestAPIUtil.getSuccessResponse(booking, module_name,request.getRequestURI());
			httpheaders.setStatus(HttpServletResponse.SC_CREATED);
		}
		catch(Exception exception) {
			handleException(exception);
		}
		return new DefaultHttpHeaders("create").disableCaching();
	}

	@Permissions(sameUser = "true")	
	public HttpHeaders show() {
		return super.show();
	}
}
