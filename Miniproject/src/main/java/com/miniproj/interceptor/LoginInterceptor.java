package com.miniproj.interceptor;

import java.sql.Timestamp;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.miniproj.domain.Member;
import com.miniproj.domain.SessionDTO;
import com.miniproj.etc.DestinationPath;
import com.miniproj.etc.SessionCheck;
import com.miniproj.service.member.MemberService;

// 제어를 빼앗아 실제 로그인을 처리하는 interceptor
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Inject
	private MemberService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("LoginInterceptor - preHandle(): 로그인 처리하러 왔음...");
		
		boolean showLoginPage = true;
		
		
		// 댓글 작성 로그인(이전 경로 저장)처리
	      if(request.getMethod().equals("GET") && request.getParameter("redirectURL") != null) { // GET방식, 쿼리스트링에 redirectURL이 존재하는 경우
	         if(!request.getParameter("redirectURL").equals("") && request.getParameter("redirectURL").contains("viewBoard")) {
	            String uri = "/board/viewBoard";
	            String queryString = "?no=" + request.getParameter("no");
	            
	            request.getSession().setAttribute("returnPath", uri + queryString);
	         }
	      }
	      
		// 자동로그인을 체크한 유저에 대해 로그인 처리
		// 1) 쿠키가 있는지 검사
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
		System.out.println("자동 로그인 쿠키 검사 : "+loginCookie);
	      if(loginCookie != null) {
				// -> 쿠키에 저장된 세션아이디와 DB에 저장된 sessionKey가 같은지 비교
				// -> DB에 저장된 sessionLimit가 현재시간보다 큰지 비교
				
				String cookieValue = loginCookie.getValue(); // 쿠키에 저장된 세션아이디
				Member autoLoginUser = service.checkAutoLoginUser(cookieValue);
				
				if(autoLoginUser != null) { // 자동로그인 처리해야함
					System.out.println("자동로그인 할 유저 : "+ autoLoginUser.getUserId());
					
					// 로그인 처리
					WebUtils.setSessionAttribute(request, "loginUser", autoLoginUser);
					SessionCheck.replaceSessionKey(request.getSession(), autoLoginUser.getUserId());
					
					System.out.println("자동로그인 되었습니다."+((Member)WebUtils.getSessionAttribute(request, "loginUser")).toString());
					
					if(WebUtils.getSessionAttribute(request, "returnPath")!=null) {
						response.sendRedirect((String)WebUtils.getSessionAttribute(request, "returnPath"));
					} else {
						response.sendRedirect("/");
					}
					showLoginPage = false;
				}
			}
	      
		return showLoginPage;
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
			
			// 자동로그인 처리
			// 자동로그인 체크한 유저이면
			// -> 쿠키 생성 (loginCookie=sessionId)
			// -> DB member 테이블에 sessionLimit, sessionKey를 저장
			
			// 쿠키가 있는지 없는지 검사
			// 쿠키가 있다면 다음을 만족하면 로그인 처리
			// -> 쿠키에 저장된 세션아이디와 DB에 저장된 sessionKey가 같은지 비교
			// -> DB에 저장된 sessionLimit이 현재 시간보다 큰지(유효한지) 비교
			
			//  ㅅ 이거 고민하는거 숙제
			
			System.out.println("자동로그인 체크 여부 : "+request.getParameter("remember"));
			
			if(request.getParameter("remember") != null) {
				System.out.println("자동로그인 유저입니다......");
				// 1) 쿠키 생성 (loginCookie=sessionId, 만료일 : 일주일)
				String sesionValue = ses.getId();
				Timestamp sesLimit = new Timestamp(System.currentTimeMillis()+(7*24*60*60*1000));
				
				Cookie loginCookie = new Cookie("loginCookie", sesionValue);
				loginCookie.setMaxAge(7*24*60*60); // 초(seconds)단위
				loginCookie.setPath("/");

				if(service.remember(new SessionDTO(loginMember.getUserId(), sesLimit, sesionValue))) {
					response.addCookie(loginCookie); // 쿠키 저장
					
				}
			}
			
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
