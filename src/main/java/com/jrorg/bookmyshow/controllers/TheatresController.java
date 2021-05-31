package com.jrorg.bookmyshow.controllers;

import org.apache.struts2.rest.HttpHeaders;
import com.jrorg.bookmyshow.entity.Theatre;
import com.jrorg.bookmyshow.request.TheatreRequest;
import com.jrorg.bookmyshow.util.Allowedparams;
import com.jrorg.bookmyshow.util.Mandatoryparams;
import com.jrorg.bookmyshow.util.Permissions;

public class TheatresController extends BaseController<Theatre,TheatreRequest>{

	public TheatresController() {
		super(new TheatreRequest(), "theatre");
	}
	private static final long serialVersionUID = 1L;
	
	public HttpHeaders index() {
		return super.index();
	}
	@Permissions(adminOnly = "true")
	@Mandatoryparams(params="name,place_id")
	@Allowedparams(params="name,place_id")
	public HttpHeaders create() {
		return super.create();
	}

	@Permissions(adminOnly = "true")
	@Allowedparams(params="name,place_id")
	public HttpHeaders update() {
		return super.update();
		
	}
	@Permissions(adminOnly = "true")
	public HttpHeaders destroy() {
		return super.destroy();
	}
	public HttpHeaders show() {
		return super.show();
	}

}
