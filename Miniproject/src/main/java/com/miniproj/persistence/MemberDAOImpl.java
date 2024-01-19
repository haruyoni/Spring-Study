package com.miniproj.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproj.domain.Member;

@Repository
public class MemberDAOImpl implements MemberDAO {
	private static String ns = "com.miniproj.mappers.memberMapper";

	@Inject
	private SqlSession ses; // sqlSessionTemplate 객체에 주입
	
	@Override
	public String getDate() {
		String q = ns + ".curDate"; // mapper namespace + 쿼리 id
		return ses.selectOne(q);
	}
	
	@Override
	public List<Member> selectAllMembers() {
		String q = ns + ".getAllMembers";
		return ses.selectList(q);
	}
}
