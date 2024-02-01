package com.miniproj.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.miniproj.domain.Member;
import com.miniproj.etc.DestinationPath;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = false;

		System.out.println("로그인을 했는지 안했는지 검사하러 옴 -- AuthenticationInterceptor preHandle()");

		HttpSession ses = request.getSession();

		if (ses.getAttribute("loginUser") == null) {
			System.out.println("로그인 안함 --> 로그인 페이지로 보내주자");
			response.sendRedirect("/member/login");

			DestinationPath.savePrePath(request);

			return false;
		}

		// 수정/삭제의 경우 : 로그인 userId == 작성자writer 조건을 만족해야함

		String uri = request.getRequestURI();
		if (uri.indexOf("/modifyBoard") != -1 || uri.indexOf("/remBoard") != -1) {
			return checkAuthValid(request, response);
		}

		return true;
	}

	private boolean checkAuthValid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 수정버튼에 writer정보를 쿼리스트링으로 추가하여 처리하는 경우
		String writer = request.getParameter("writer");
		String loginUserId = ((Member) request.getSession().getAttribute("loginUser")).getUserId();
		
		if(!loginUserId.equals(writer)) {
			response.sendRedirect("viewBoard?no="+request.getParameter("no")+
					"&writer="+writer + "&status=noPermission");
			return false;
		}
		return true;
	}

}
