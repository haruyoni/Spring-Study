package com.miniproj.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproj.domain.LoginDTO;
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
	public int updateUserPoint(String why, String userId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("why", why);
		param.put("userId", userId);
		
		return ses.update(ns+".updateUserPoint", param);
	}

	@Override
	public Member login(LoginDTO tmpMember) throws Exception {
		return ses.selectOne(ns+".login", tmpMember);
	}

}
