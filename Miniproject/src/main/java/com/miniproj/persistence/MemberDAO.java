package com.miniproj.persistence;

import java.util.List;

import com.miniproj.domain.Member;

public interface MemberDAO {
	String getDate();
	
	// member 테이블에 userpoint를 update
	void updateUserPoint(String why, String userId) throws Exception;
	
}
