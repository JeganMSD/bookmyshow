package com.jrorg.bookmyshow.manager;

import com.jrorg.bookmyshow.entity.BaseEntity;
import com.jrorg.bookmyshow.entity.Booking;
import com.jrorg.bookmyshow.entity.Movie;
import com.jrorg.bookmyshow.entity.Place;
import com.jrorg.bookmyshow.entity.Screen;
import com.jrorg.bookmyshow.entity.Seat;
import com.jrorg.bookmyshow.entity.Show;
import com.jrorg.bookmyshow.entity.Theatre;
import com.jrorg.bookmyshow.entity.Ticket;
import com.jrorg.bookmyshow.entity.User;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.BookingRequest;
import com.jrorg.bookmyshow.request.MovieRequest;
import com.jrorg.bookmyshow.request.PlaceRequest;
import com.jrorg.bookmyshow.request.ScreenRequest;
import com.jrorg.bookmyshow.request.SeatRequest;
import com.jrorg.bookmyshow.request.ShowRequest;
import com.jrorg.bookmyshow.request.TheatreRequest;
import com.jrorg.bookmyshow.request.TicketRequest;
import com.jrorg.bookmyshow.request.UserRequest;

public class BaseManagerFactory {
	public static <K extends BaseEntity,V extends BaseRequest> BaseManager<K,V> getInstance(Class<?> clazz) {
		String name = clazz.getSimpleName();
		switch(name) {
			case "PlaceRequest":return (BaseManager<K,V>)new PlacesManager<Place,PlaceRequest>();
			case "ScreenRequest":return (BaseManager<K,V>) new ScreensManager<Screen,ScreenRequest>();
			case "SeatRequest":return (BaseManager<K,V>) new SeatsManager<Seat,SeatRequest>();
			case "TheatreRequest":return (BaseManager<K,V>) new TheatresManager<Theatre,TheatreRequest>();
			case "TicketRequest":return (BaseManager<K,V>) new TicketsManager<Ticket,TicketRequest>();
			case "BookingRequest":return (BaseManager<K,V>) new BookingsManager<Booking,BookingRequest>();
			case "UserRequest":return (BaseManager<K,V>) new UsersManager<User,UserRequest>();
			case "ShowRequest":return (BaseManager<K,V>) new ShowsManager<Show,ShowRequest>();
			case "MovieRequest":return (BaseManager<K,V>) new MoviesManager<Movie,MovieRequest>();
		}
		return null;
	}
}
