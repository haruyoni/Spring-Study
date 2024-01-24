package com.miniproj.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproj.domain.Board;
import com.miniproj.etc.UploadedFile;

@Repository
public class BoardDAOImpl implements BoardDAO {

	private static String ns = "com.miniproj.mappers.boardMapper";
	
	@Inject
	private SqlSession ses;
	
	@Override
	public List<Board> selectAllBoard() throws Exception {
		return ses.selectList(ns + ".getAllBoard");
	}

	@Override
	public int insertNewBoard(Board newBoard) throws Exception {
		return ses.insert(ns+".insertNewBoard", newBoard);
	}

	@Override
	public int selectBoardNo() throws Exception {
		return ses.selectOne(ns+".getNo");
	}

	@Override
	public void insertUploadedFile(int boardNo, UploadedFile uf) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("originalFileName", uf.getOriginalFileName());
		param.put("newFileName", uf.getNewFileName());
		param.put("fileSize", uf.getSize());
		param.put("boardNo", boardNo);		
		param.put("thumbFileName", uf.getOriginalFileName());
		
		ses.insert(ns+".insertUploadedFile", param);
		
	}
	
	

}
