package com.miniproj.etc;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

public class DestinationPath {

	public static void savePrePath(HttpServletRequest request) {
		
		String uri = request.getRequestURI();
//		String query = request.getQueryString();
		System.out.println("destination : "+uri);
		
		String query = request.getQueryString();
		
		if(query == null) { // 쿼리스트링이 없다면
			query = "";
		} else {
			query = "?" + query;
		}
		
		if(request.getMethod().equals("GET")) {
			request.getSession().setAttribute("returnPath", uri+query);
		}
	}

}
