package com.jrorg.bookmyshow.controllers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.apache.struts2.rest.RestActionSupport;

import com.jrorg.bookmyshow.entity.BaseEntity;
import com.jrorg.bookmyshow.entity.User;
import com.jrorg.bookmyshow.manager.BaseManager;
import com.jrorg.bookmyshow.manager.BaseManagerFactory;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.UserRequest.Role;
import com.jrorg.bookmyshow.util.Allowedparams;
import com.jrorg.bookmyshow.util.ErrorResponse;
import com.jrorg.bookmyshow.util.ErrorResponse.RestErrorResponse;
import com.jrorg.bookmyshow.util.Mandatoryparams;
import com.jrorg.bookmyshow.util.Permissions;
import com.jrorg.bookmyshow.util.RestAPIException;
import com.jrorg.bookmyshow.util.RestAPIException.Type;
import com.jrorg.bookmyshow.util.RestAPIUtil;
import com.jrorg.bookmyshow.util.RestResponse;
import com.jrorg.bookmyshow.util.ThreadDetails;
import com.jrorg.bookmyshow.util.Utilities;
import com.opensymphony.xwork2.ModelDriven;

public abstract class BaseController<K extends BaseEntity,V extends BaseRequest> extends RestActionSupport implements ModelDriven<Object>,ServletRequestAware,ServletResponseAware{

	protected String id;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected RestResponse response_object;
	protected RestErrorResponse error_response;
	protected BaseManager<K,V> manager;
	protected BaseRequest request_data;
	protected String module_name;
	public BaseController(BaseRequest request,String module_name) {
		this.module_name = module_name;
		this.request_data = request;
		this.manager= BaseManagerFactory.getInstance(request_data.getClass());
	}

	public Object getModel() {
		if(response_object == null) {
			return request_data;
		}
		else {
			if(response_object.hasError()){
				return error_response;
			}
			else {
				return response_object;
			}
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	public HttpHeaders index() {
		DefaultHttpHeaders httpheaders = new DefaultHttpHeaders("index");    //No I18N
		response.setContentType("application/json; charset=UTF-8");
		try {
			validateParams(this.getClass(),"index",request_data);
			int limit = Integer.parseInt(Optional.ofNullable(request.getParameter("limit")).orElse("10"));
			int page_no = Integer.parseInt(Optional.ofNullable(request.getParameter("page_number")).orElse("1"));
			List<BaseEntity> resultset = (List<BaseEntity>)manager.getList(page_no,limit);
			response_object = RestAPIUtil.getSuccessResponse(resultset, module_name,request.getRequestURI());
			httpheaders.setStatus(HttpServletResponse.SC_CREATED);
		}
		catch(Exception exception) {
			handleException(exception);
		}
		return httpheaders.disableCaching();
	}
	public HttpHeaders create() {
		DefaultHttpHeaders httpheaders = new DefaultHttpHeaders("create");    //No I18N
		response.setContentType("application/json; charset=UTF-8");
		try {
			validateParams(this.getClass(),"create",request_data);
			K entity= manager.create((V) request_data);
			response_object = RestAPIUtil.getSuccessResponse(entity, module_name,request.getRequestURI());
			httpheaders.setStatus(HttpServletResponse.SC_CREATED);
		}
		catch(Exception exception) {
			handleException(exception);
		}
		return httpheaders.disableCaching();
	}
	public HttpHeaders update() {
		DefaultHttpHeaders httpheaders = new DefaultHttpHeaders("update");    //No I18N
		response.setContentType("application/json; charset=UTF-8");
		try {
			validateParams(this.getClass(),"update",request_data);
			request_data.setId(Long.parseLong(id));
			K entity = manager.update((V)request_data);
			response_object = RestAPIUtil.getSuccessResponse(entity, module_name,request.getRequestURI());
			httpheaders.setStatus(HttpServletResponse.SC_OK);
		}
		catch(Exception exception) {
			handleException(exception);
		}
		return httpheaders.disableCaching();
		
	}
	public HttpHeaders destroy() {
		DefaultHttpHeaders httpheaders = new DefaultHttpHeaders("destroy");    //No I18N
		response.setContentType("application/json; charset=UTF-8");
		try {
			manager.delete(Long.parseLong(id));
			httpheaders.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}
		catch(Exception exception) {
			handleException(exception);
		}
		return httpheaders.disableCaching();
	}
	public void handleException(Exception exception) {
		Logger.getLogger(this.getClass().getName()).log(Level.ERROR,exception.getMessage(), exception);
		if(exception instanceof RestAPIException) {
			response_object = new RestResponse();
			response_object.setHasError(true);
			error_response = new RestErrorResponse();
			error_response.setError(new ErrorResponse((RestAPIException)exception));
		}
		else{
			response_object = new RestResponse();
			response_object.setHasError(true);
			error_response = new RestErrorResponse();
			error_response.setError(new ErrorResponse(new RestAPIException(Type.OPERATION_FAILED)));
		}
	}
	public void validateParams(Class<?> clazz,String method_name,List<? extends BaseRequest> batch_request) throws Exception{
		for(BaseRequest request:batch_request) {
			validateParams(clazz, method_name, request);
		}
	}
	public void validateParams(Class<?> clazz,String method_name,BaseRequest request) throws Exception{
		User user = ThreadDetails.getCurrentUser();
		Method method = clazz.getDeclaredMethod(method_name);
		Permissions permission_anotate=method.getAnnotation(Permissions.class);
		Mandatoryparams mandatory_anotate = method.getAnnotation(Mandatoryparams.class);
		Allowedparams allowed_anotate = method.getAnnotation(Allowedparams.class);
		if(permission_anotate!=null) {
			boolean is_admin_request = Boolean.parseBoolean(permission_anotate.adminOnly());
			boolean my_data_request= Boolean.parseBoolean(permission_anotate.sameUser());
			if(user == null ) {
				throw new RestAPIException(Type.UNAUTHORISED);
			}
			if(!Role.ADMIN.equals(user.getRole())){
				if(is_admin_request) {
					throw new RestAPIException(Type.UNAUTHORISED);
				}
			}
			if(my_data_request) {
				if(user.getId()!=request.getId()) {
					throw new RestAPIException(Type.UNAUTHORISED);
				}
			}
		}
		List<String> manadatory_params = new LinkedList<String>(Arrays.asList(mandatory_anotate == null?new String[0]:mandatory_anotate.params().split(",")));
		List<String> allowed_params = new LinkedList<String>(Arrays.asList(allowed_anotate == null?new String[0]:allowed_anotate.params().split(",")));
		Field[] fields = request.getClass().getDeclaredFields();
		for(Field field:fields) {
			if(!Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				if(field.get(request)!=null) {
					manadatory_params.remove(field.getName());
					if(!allowed_params.contains(field.getName())) {
						field.set(request, null);
					}
				}
			}
			
		}
		if(!manadatory_params.isEmpty()) {
			throw new RestAPIException(Type.MISSING_JSON_KEY,Utilities.toString(manadatory_params),null);
		}
		
	}

	public HttpHeaders show() {
		DefaultHttpHeaders httpheaders = new DefaultHttpHeaders("show");    //No I18N
		response.setContentType("application/json; charset=UTF-8");
		try {
			validateParams(this.getClass(),"show",request_data);
			request_data.setId(Long.parseLong(id));
			K entity = manager.show((V)request_data);
			if(entity==null) {
				throw new RestAPIException(Type.INVALID_RESOURCE_ID,module_name,null);
			}
			response_object = RestAPIUtil.getSuccessResponse(entity, module_name,request.getRequestURI());
			httpheaders.setStatus(HttpServletResponse.SC_OK);
		}
		catch(Exception exception) {
			handleException(exception);
		}
		return new DefaultHttpHeaders("show").disableCaching();
	}
}
