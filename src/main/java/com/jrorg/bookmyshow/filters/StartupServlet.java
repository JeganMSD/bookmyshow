package com.jrorg.bookmyshow.filters;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.jrorg.bookmyshow.services.BaseService;
import com.jrorg.bookmyshow.services.LogService;

public class StartupServlet extends HttpServlet{
	
	public Logger logger = Logger.getLogger(StartupServlet.class.getName());
	 public void init() throws ServletException
	    {
		 	Long time = System.currentTimeMillis();
		 	BaseService service = new LogService();
		 	service.init(null);
		 	logger.log(Level.INFO, "Logger Initialization Time Taken " + (time - System.currentTimeMillis())); 	
	    }
}
