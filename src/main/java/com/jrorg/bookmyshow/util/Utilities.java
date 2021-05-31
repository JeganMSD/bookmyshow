package com.jrorg.bookmyshow.util;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utilities {
	public static boolean isNotEmpty(String data) {
		try {
			return data.length()>0;
		}
		catch(Exception e) {
			return false;
		}
	}
	private static Hashtable getObject(JSONObject jo, boolean include_null) throws JSONException {
		Hashtable ht = new Hashtable();
		Iterator it = jo.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = jo.get(key);
			if (!include_null && value.equals(JSONObject.NULL)) {
				continue;
			}
			if (value instanceof JSONObject) {
				ht.put(key, getObject((JSONObject) value, include_null));
			} else if (value instanceof JSONArray) {
				ht.put(key, getObject((JSONArray) value, include_null));
			} else {
				ht.put(key, value);
			}
		}
		return ht;
	}
	
	private static ArrayList getObject(JSONArray ja, boolean include_null) throws JSONException {
		ArrayList al = new ArrayList();
		for (int i = 0; i < ja.length(); ++i) {
			Object value = ja.get(i);
			if (!include_null && value.equals(JSONObject.NULL)) {
				continue;
			}
			if (value instanceof JSONObject) {
				al.add(getObject((JSONObject) value, include_null));
			} else if (value instanceof JSONArray) {
				al.add(getObject((JSONArray) value, include_null));
			} else {
				al.add(value);
			}
		}
		return al;
	}
	public static Object getObject(String input) {
		try {
			return getObject(new JSONObject(input),false);
		}
		catch(Exception e) {
			try {
				return getObject(new JSONArray(input),false);
			}
			catch(Exception e1) {
				return null;
			}
		}
	}
	
	public static List<String> getList(String data){
		return Stream.of(data.split(","))
		  .map(String::trim)
		  .collect(Collectors.toList());
	}
	
	public static String toString(List<? extends Object> list){
		return list.stream().map(Object::toString)
                .collect(Collectors.joining(","));
	}
	
	public static String generateToken() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[64];
		random.nextBytes(bytes);
		String token = new String(bytes);
		return token;
	}
}
