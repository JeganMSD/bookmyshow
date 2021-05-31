package com.jrorg.bookmyshow.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.jrorg.bookmyshow.util.ConfigurationsUtil;

public class SecurityService implements BaseService{

	@Override
	public void init(Object params) {
		try {
			List<String> parsed_file_names = (List<String>)params;
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();      
			for(String file_name : parsed_file_names) {
				String file_path = new StringBuilder(ConfigurationsUtil.getServerHome()).append(File.separator).append(file_name).toString();		            
				InputStream stream = classLoader.getResourceAsStream(new StringBuilder("../").append(file_name).toString());
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				
			}
		}
		catch(Exception e) {
			
		}


	}

}
