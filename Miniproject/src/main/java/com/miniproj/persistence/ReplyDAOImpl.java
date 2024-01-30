package com.miniproj.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproj.domain.Reply;
import com.miniproj.etc.PagingInfo;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

	private static String ns = "com.miniproj.mappers.replyMapper";
	
	@Inject
	private SqlSession ses;
	
	@Override
	public List<Reply> selectAllReplies(int boardNo) throws Exception {
		return ses.selectList(ns+".selectAllReplies", boardNo);
	}

	@Override
	public int insertNewReply(Reply newReply) throws Exception {
		return ses.insert(ns+".insertNewReply", newReply);
	}

	@Override
	public int selectTotalRepliesCnt(int boardNo) throws Exception {
		return ses.selectOne(ns+".getTotalReplCnt", boardNo);
	}

	@Override
	public List<Reply> selectAllReplies(int boardNo, PagingInfo pi) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("boardNo", boardNo);
		param.put("startRowIndex", pi.getStartRowIndex());
		param.put("viewPostCntPerPage", pi.getViewPostCntPerPage());
		
		return ses.selectList(ns+".selectAllReplies", param);
	}

}
