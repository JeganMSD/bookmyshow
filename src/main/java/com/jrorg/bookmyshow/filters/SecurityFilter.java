package com.jrorg.bookmyshow.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.jrorg.bookmyshow.entity.User;
import com.jrorg.bookmyshow.manager.UsersManager;
import com.jrorg.bookmyshow.request.UserRequest;
import com.jrorg.bookmyshow.util.ThreadDetails;

public class SecurityFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest wrapped_request =(HttpServletRequest)request;
		String token = wrapped_request.getHeader("Authorization");
		if(token != null) {
			try {
				User user = new UsersManager<User,UserRequest>().findUser(token);
				ThreadDetails.setRequestProperties(user);
				chain.doFilter(request, response);
			} catch (Exception e) {
			}
			finally {
				ThreadDetails.clear();
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
