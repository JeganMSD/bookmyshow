package com.jrorg.bookmyshow.controllers;

import org.apache.struts2.rest.HttpHeaders;

import com.jrorg.bookmyshow.entity.Screen;
import com.jrorg.bookmyshow.request.ScreenRequest;
import com.jrorg.bookmyshow.util.Allowedparams;
import com.jrorg.bookmyshow.util.Mandatoryparams;
import com.jrorg.bookmyshow.util.Permissions;

public class ScreensController extends BaseController<Screen,ScreenRequest>{

	public ScreensController() {
		super(new ScreenRequest(), "screen");
	}
	private static final long serialVersionUID = 1L;
	
	public HttpHeaders index() {
		return super.index();
	}
	@Permissions(adminOnly = "true")
	@Mandatoryparams(params="name,theatre_id")
	@Allowedparams(params="name,theatre_id")
	public HttpHeaders create() {
		return super.create();
	}
	@Permissions(adminOnly = "true")
	@Allowedparams(params="name,theatre_id")
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
