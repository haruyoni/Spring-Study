package com.springbasic.persistence;

import java.util.List;

import com.springbasic.vo.Member;

public interface MemberDAO {
	String getDate();
	
	Member selectMemberByUserId(String userId);
	
	void insertMember(Member member);
	
	List<Member> selectAllMembers();
}
