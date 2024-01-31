package com.miniproj.controller.member;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.miniproj.domain.LoginDTO;
import com.miniproj.domain.Member;
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
}
