package com.jrorg.bookmyshow.controllers;

import org.apache.struts2.rest.HttpHeaders;

import com.jrorg.bookmyshow.entity.Movie;
import com.jrorg.bookmyshow.request.MovieRequest;
import com.jrorg.bookmyshow.util.Allowedparams;
import com.jrorg.bookmyshow.util.Mandatoryparams;
import com.jrorg.bookmyshow.util.Permissions;

public class MoviesController<K extends Movie,V extends MovieRequest> extends BaseController<Movie, MovieRequest>{
	public MoviesController() {
		super(new MovieRequest(), "movie");
	}
	private static final long serialVersionUID = 1L;
	
	public HttpHeaders index() {
		return super.index();
	}

	@Permissions(adminOnly = "true")
	@Mandatoryparams(params="name,genre,language")
	@Allowedparams(params="name,genre,language")
	public HttpHeaders create() {
		return super.create();
	}


	@Permissions(adminOnly = "true")
	@Allowedparams(params="name,genre,language")
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
