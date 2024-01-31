package com.miniproj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = false;
		
		System.out.println("로그인을 했는지 안했는지 검사하러 옴 -- AuthenticationInterceptor preHandle()");
		
		HttpSession ses = request.getSession();
		
		if(ses.getAttribute("loginUser")==null) {
			System.out.println("로그인 안함 --> 로그인 페이지로 보내주자");
			response.sendRedirect("/member/login");
			return false;
		}
		return true;
	}
	
}
