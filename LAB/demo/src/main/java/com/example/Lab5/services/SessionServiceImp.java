package com.example.Lab5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionServiceImp implements SessionService {
    @Autowired
	HttpSession session;
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		Object value = session.getAttribute(name);
		T value2 = (T) value;
		return value2;
	}
	

	@Override
	public void set(String name, Object value) {
		session.setAttribute(name, value);
	}
	

	@Override
	public void remove(String name) {
		session.removeAttribute(name);
	}
}
