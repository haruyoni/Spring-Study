package com.springbasic.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository //아래의 클래스가 DAO객체임을 명시
public class MemberDAOImpl implements MemberDAO {

	@Inject
	private SqlSession ses; // sqlSessionTemplate 객체에 주입
	
	@Override
	public String getDate() {
		String q = "com.springbasic.mappers.memberMapper"+".curDate"; // mapper namespace + 쿼리 id
		return ses.selectOne(q);
	}

}
