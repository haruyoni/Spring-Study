package com.miniproj.service.member;

import com.miniproj.domain.LoginDTO;
import com.miniproj.domain.Member;
import com.miniproj.domain.SessionDTO;

public interface MemberService {

	Member login(LoginDTO tmpMember) throws Exception;

	boolean remember(SessionDTO sessionDTO) throws Exception;

	Member checkAutoLoginUser(String sessionKey) throws Exception;

}
