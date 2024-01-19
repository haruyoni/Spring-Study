package com.miniproj.persistence;

import java.util.List;

import com.miniproj.domain.Member;

public interface MemberDAO {
	String getDate();
	List<Member> selectAllMembers();
}
