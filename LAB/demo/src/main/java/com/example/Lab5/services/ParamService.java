package com.example.Lab5.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ParamService {
	@Autowired
	HttpServletRequest request;
	
	public String getString(String name, String defaultValue) {
		if (request.getParameter(name)!=null) {
			return request.getParameter(name);
		}
		return defaultValue;
	}
	public int getInteger(String name, int defaultValue) {
		if (request.getParameter(name)!=null) {
			String val = request.getParameter(name);
			try {
				int valueInt = Integer.parseInt(val);
				return valueInt;
			} catch(Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}
	
	public double getDouble(String name, double defaultValue) {
		if (request.getParameter(name)!=null) {
			String val = request.getParameter(name);
			try {
				double valueDouble = Double.parseDouble(val);
				return valueDouble;
			} catch(Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}
	
	public boolean getBoolean(String name, boolean defaultValue) {
		if (request.getParameter(name)!=null) {
			String val = request.getParameter(name);
			try {
				boolean valueBoolean = Boolean.parseBoolean(val);
				return valueBoolean;
			} catch(Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}
	
	public Date getDate(String name, String pattern) throws ParseException {
		if (request.getParameter(name)!=null) {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			String value = request.getParameter(name);
			Date valueDate = formatter.parse(value);
			return valueDate;
		}
		return null;
		
	}
	
}
