package com.jrorg.bookmyshow.request;

public class ShowRequest extends BaseRequest {
	private Long date_millis;
	
	private Long start_time_millis;
	
	private Long end_time_millis;
	
	private Long movie_id;
	
	private Long screen_id;

	public Long getDate_millis() {
		return date_millis;
	}

	public void setDate_millis(Long date_millis) {
		this.date_millis = date_millis;
	}

	public Long getStart_time_millis() {
		return start_time_millis;
	}

	public void setStart_time_millis(Long start_time_millis) {
		this.start_time_millis = start_time_millis;
	}

	public Long getEnd_time_millis() {
		return end_time_millis;
	}

	public void setEnd_time_millis(Long end_time_millis) {
		this.end_time_millis = end_time_millis;
	}

	public Long getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(Long movie_id) {
		this.movie_id = movie_id;
	}

	public Long getScreen_id() {
		return screen_id;
	}

	public void setScreen_id(Long screen_id) {
		this.screen_id = screen_id;
	}
	
	
}
