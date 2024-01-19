package com.springbasic.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.springbasic.vo.Member;

@Repository //아래의 클래스가 DAO객체임을 명시
public class MemberDAOImpl implements MemberDAO {
	
	private static String ns = "com.springbasic.mappers.memberMapper";

	@Inject
	private SqlSession ses; // sqlSessionTemplate 객체에 주입
	
	@Override
	public String getDate() {
		String q = ns + ".curDate"; // mapper namespace + 쿼리 id
		return ses.selectOne(q);
	}

	@Override
	public Member selectMemberByUserId(String userId) {
		String q = ns + ".viewMemberByUserId";
		return ses.selectOne(q, userId);
	}

	@Override
	public void insertMember(Member member) {
		String q = ns + ".inputMemberWithoutImg";
		ses.insert(q, member);
	}

	@Override
	public List<Member> selectAllMembers() {
		String q = ns + ".getAllMembers";
		return ses.selectList(q);
	}

}