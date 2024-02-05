package com.miniproj.controller.member;

import java.sql.Timestamp;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import com.miniproj.domain.LoginDTO;
import com.miniproj.domain.Member;
import com.miniproj.domain.SessionDTO;
import com.miniproj.etc.SessionCheck;
import com.miniproj.service.member.MemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService mService;
	
	@RequestMapping("login")
	public void loginGET() {
		logger.info("loginGET 방식 호출됨");
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public void loginPOST(LoginDTO tmpMember, Model model) throws Exception {
		System.out.println(tmpMember.toString() + "으로 로그인해보자.");
		
		Member loginMember = mService.login(tmpMember);
		
		if(loginMember != null) {
			System.out.println("로그인 성공!");
			
			model.addAttribute("loginMember", loginMember);
			// 이 모델 객체를 가지고 인터셉터 postHandle로 감
		} else {
			System.out.println("로그인 실패");
			return;
		}
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession ses = request.getSession();
		System.out.println("로그아웃 : "+ses.getId());
		
//		if(ses.getAttribute("loginUser")!=null) {
//			ses.removeAttribute("loginUser");
//			ses.invalidate();
//		}
		
		
		// 로그아웃 할 때, 세션Map에 담겨진 세션 제거
		if(ses.getAttribute("loginUser") != null) {
			String loginUserId = ((Member)ses.getAttribute("loginUser")).getUserId();
			SessionCheck.removeKey(loginUserId);
			
			
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if(loginCookie != null) {
				loginCookie.setValue(null);
				loginCookie.setMaxAge(0);
				loginCookie.setPath("/");
				response.addCookie(loginCookie);
				mService.remember(new SessionDTO(loginUserId, null, null));
			}
			
		}
		
		
		return "redirect:/";
	}
}
