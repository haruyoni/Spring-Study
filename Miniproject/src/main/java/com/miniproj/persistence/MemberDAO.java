package com.miniproj.persistence;

import com.miniproj.domain.LoginDTO;
import com.miniproj.domain.Member;
import com.miniproj.domain.SessionDTO;

public interface MemberDAO {
	String getDate();
	
	// member 테이블에 userpoint를 update
	int updateUserPoint(String why, String userId) throws Exception;

	// 로그인
	Member login(LoginDTO tmpMember) throws Exception;
	
	
	// 자동로그인 정보 저장
	int updateSession(SessionDTO sesDTO) throws Exception;
}
