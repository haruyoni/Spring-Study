package com.miniproj.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproj.domain.PointLog;

@Repository
public class PointLogDAOImpl implements PointLogDAO {
	private static String ns = "com.miniproj.mappers.pointlogMapper";

	@Inject
	private SqlSession ses; // sqlSessionTemplate 객체에 주입
	
	@Override
	public int insertPointLog(PointLog pl) throws Exception {
		return ses.insert(ns+".insertPointLog", pl);
	}

}
