package com.miniproj.service.member;

import com.miniproj.domain.LoginDTO;
import com.miniproj.domain.Member;

public interface MemberService {

	Member login(LoginDTO tmpMember) throws Exception;

}
