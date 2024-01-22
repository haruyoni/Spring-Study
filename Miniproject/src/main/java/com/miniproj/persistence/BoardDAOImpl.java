package com.miniproj.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproj.domain.Board;

@Repository
public class BoardDAOImpl implements BoardDAO {

	private static String ns = "com.miniproj.mappers.boardMapper";
	@Inject
	private SqlSession ses;
	@Override
	public List<Board> selectAllBoard() {
		return ses.selectList(ns + ".getAllBoard");
	}

}
