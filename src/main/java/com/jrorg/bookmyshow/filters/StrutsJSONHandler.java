package com.jrorg.bookmyshow.filters;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.rest.handler.JacksonLibHandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jrorg.bookmyshow.util.Utilities;
import com.opensymphony.xwork2.ActionInvocation;

public class StrutsJSONHandler extends JacksonLibHandler
{
	private ObjectMapper mapper = new ObjectMapper();
	private static final Logger LOGGER = Logger.getLogger(StrutsJSONHandler.class.getName());

	@Override
	public void toObject(ActionInvocation invocation,Reader in, Object target) throws IOException
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String filteredcontent = IOUtils.toString(request.getInputStream());
		String className = target.getClass().getName();
		Class batch_entity_class = (Class)request.getAttribute("batch_entity_class");
		boolean is_batch_api = batch_entity_class!=null;
		if(!className.equalsIgnoreCase(Hashtable.class.getName())){
			try{
				if(Utilities.isNotEmpty(filteredcontent)){
					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
					if(is_batch_api) {
						Object object =mapper.readValue(filteredcontent, mapper.getTypeFactory().constructCollectionType(List.class, batch_entity_class));
						((List)target).addAll((List)object);
						LOGGER.info("JSON to POJO has been converted successfully."+object);
					}
					else {
						Object object =mapper.readValue(filteredcontent, target.getClass());
						mapper.updateValue(target, object);
						LOGGER.info("JSON to POJO has been converted successfully."+object);
					}
				}
				 
			}catch(Exception exception){
				throw exception; 
			}
			
		}else{
		
			Object filteredObj = Utilities.getObject(filteredcontent);
			if(filteredObj != null && filteredObj instanceof Hashtable)
			{
				mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
				ObjectReader or=mapper.readerForUpdating(target);
				or.readValue(filteredcontent);
			}
		}
	}
	

	@Override
	public String fromObject(ActionInvocation invocation, Object obj, String resultCode, Writer stream) throws IOException
	{
		try
		{
			mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			mapper.writeValue(stream, obj);
			return null;
		}
		catch(Exception e)
		{
		}
		return super.fromObject(invocation, obj, resultCode, stream);
	}
}
