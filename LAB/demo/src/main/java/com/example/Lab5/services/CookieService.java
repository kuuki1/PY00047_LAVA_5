package com.example.Lab5.services;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService {
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse response;
	
	public Cookie get(String name) {
		Cookie[] cookies = request.getCookies();
		for(Cookie c:cookies) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	public String getValue(String name) {
		Cookie c = this.get(name);
		if (c!=null) {
			String decodeStr = c.getValue();
			byte[] bytes = Base64.getDecoder().decode(decodeStr);
			return new String(bytes);
		}
		return "";
	}
	public Cookie set(String name, String value, int hours) {
		byte[] bytes = value.getBytes();
		String encode = Base64.getEncoder().encodeToString(bytes);
		Cookie cookie = new Cookie(name,encode);
		cookie.setMaxAge(hours*3600);
		cookie.setPath("/");
		response.addCookie(cookie);
		return cookie;
	}
	public void remove(String name) {
		this.set(name, "", 0);
	}
}
