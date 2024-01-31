package com.miniproj.persistence;

import java.util.List;

import com.miniproj.domain.LoginDTO;
import com.miniproj.domain.Member;

public interface MemberDAO {
	String getDate();
	
	// member 테이블에 userpoint를 update
	int updateUserPoint(String why, String userId) throws Exception;

	// 로그인
	Member login(LoginDTO tmpMember) throws Exception;
	
}
