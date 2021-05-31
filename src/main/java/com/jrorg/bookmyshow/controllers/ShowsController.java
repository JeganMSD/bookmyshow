package com.jrorg.bookmyshow.controllers;

import org.apache.struts2.rest.HttpHeaders;

import com.jrorg.bookmyshow.entity.Show;
import com.jrorg.bookmyshow.request.ShowRequest;
import com.jrorg.bookmyshow.util.Allowedparams;
import com.jrorg.bookmyshow.util.Mandatoryparams;
import com.jrorg.bookmyshow.util.Permissions;

public class ShowsController<K extends Show,V extends ShowRequest> extends BaseController<Show, ShowRequest> {
	public ShowsController() {
		super(new ShowRequest(), "show");
	}
	private static final long serialVersionUID = 1L;
	
	public HttpHeaders index() {
		return super.index();
	}
	@Mandatoryparams(params="date_millis,start_time_millis,end_time_millis,movie_id,screen_id")
	@Allowedparams(params="date_millis,start_time_millis,end_time_millis,movie_id,screen_id")
	public HttpHeaders create() {
		return super.create();
	}

	@Permissions(adminOnly = "true")
	@Allowedparams(params="date_millis,start_time_millis,end_time_millis,movie_id,screen_id")
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
