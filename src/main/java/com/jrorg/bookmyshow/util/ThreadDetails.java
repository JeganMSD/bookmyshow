package com.jrorg.bookmyshow.util;

import com.jrorg.bookmyshow.entity.User;

public class ThreadDetails {
	private static ThreadLocal<User> usertl = new ThreadLocal<>();
	public static void setRequestProperties(User user) {
		usertl.set(user);
	}
	public static void clear(){
		usertl.set(null);
	}
	public static User getCurrentUser() {
		return usertl.get();
	}
}
