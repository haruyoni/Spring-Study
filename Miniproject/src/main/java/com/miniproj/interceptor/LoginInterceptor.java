package com.miniproj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.miniproj.domain.Member;
import com.miniproj.etc.DestinationPath;
import com.miniproj.etc.SessionCheck;

// 제어를 빼앗아 실제 로그인을 처리하는 interceptor
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("LoginInterceptor - preHandle(): 로그인 처리하러 왔음...");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("LoginInterceptor - postHandle() : 로그인 처리하러 DB 다녀옴");
		
		ModelMap modelMap = modelAndView.getModelMap();
		Member loginMember = (Member)modelMap.get("loginMember");
		
		HttpSession ses = request.getSession();
		
		if(loginMember != null) {
			System.out.println("현재 로그인한 유저 : "+loginMember.toString());
			ses.setAttribute("loginUser", loginMember);
			
			// 중복 로그인 체크
			SessionCheck.replaceSessionKey(ses, loginMember.getUserId());
			
			// 로그인 성공 후 돌아갈 경로 처리
			String returnPath = "";
			if(ses.getAttribute("returnPath")!=null) {
				returnPath = (String)ses.getAttribute("returnPath");
			} else {
				returnPath = "/";
			}
						
			response.sendRedirect(returnPath);
		}
	}


}
