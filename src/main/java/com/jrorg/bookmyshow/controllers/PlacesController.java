package com.jrorg.bookmyshow.controllers;

import org.apache.struts2.rest.HttpHeaders;

import com.jrorg.bookmyshow.entity.Place;
import com.jrorg.bookmyshow.request.PlaceRequest;
import com.jrorg.bookmyshow.util.Allowedparams;
import com.jrorg.bookmyshow.util.Mandatoryparams;
import com.jrorg.bookmyshow.util.Permissions;

public class PlacesController extends BaseController<Place,PlaceRequest>{
	
	private static final long serialVersionUID = 1L;
	
	public PlacesController(){
		super(new PlaceRequest(),"place");
	}
	public HttpHeaders index() {
		return super.index();
	}
	@Permissions(adminOnly = "true")
	@Mandatoryparams(params="name,city,state,pincode,country")
	@Allowedparams(params="name,city,state,pincode,country")
	public HttpHeaders create() {
		return super.create();
	}

	@Permissions(adminOnly = "true")
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
