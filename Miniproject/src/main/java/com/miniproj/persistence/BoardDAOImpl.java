package com.miniproj.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproj.domain.Board;
import com.miniproj.domain.ReadCountProcess;
import com.miniproj.domain.UploadedFile;
import com.miniproj.etc.GetUserIPAddr;

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
		param.put("originalFileName", uf.getOriginFileName());
		param.put("newFileName", uf.getNewFileName());
		param.put("fileSize", uf.getSize());
		param.put("boardNo", boardNo);		
		param.put("thumbFileName", uf.getThumbFileName());
		
		ses.insert(ns+".insertUploadedFile", param);
		
	}

	@Override
	public ReadCountProcess selectReadCountProcess(ReadCountProcess rcp) throws Exception {
		return ses.selectOne(ns+".getReadCountProcess", rcp);
	}

	@Override
	public int selectHourDiffReadTime(ReadCountProcess rcp) throws Exception {
		return ses.selectOne(ns+".getHourDiffReadTime", rcp);
	}

	@Override
	public int updateReadCountProcess(ReadCountProcess rcp) throws Exception {
		return ses.update(ns+".updateReadCountProcess", rcp);
	}

	@Override
	public int updateReadCount(int no) throws Exception {
		return ses.update(ns+".updateReadCount", no);
	}

	@Override
	public int insertReadCountProcess(ReadCountProcess rcp) throws Exception {
		return ses.insert(ns+".insertReadCountProcess", rcp);
	}

	@Override
	public Board selectBoardByNo(int no) throws Exception {
		return ses.selectOne(ns+".selectBoardByNo", no);
	}

	@Override
	public List<UploadedFile> selectUploadedFile(int no) throws Exception {
		return ses.selectList(ns+".getUploadedFile", no);
	}
	
	

}
