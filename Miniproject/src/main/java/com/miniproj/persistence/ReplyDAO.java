package com.miniproj.persistence;

import java.util.List;

import com.miniproj.domain.Reply;
import com.miniproj.etc.PagingInfo;

public interface ReplyDAO {
	
	// 모든 댓글 조회
	List<Reply> selectAllReplies(int boardNo) throws Exception;

	// 새 댓글 저장
	int insertNewReply(Reply newReply) throws Exception;

	// 총 댓글 수 가져오기
	int selectTotalRepliesCnt(int boardNo) throws Exception;

	// 댓글 페이징 조회
	List<Reply> selectAllReplies(int boardNo, PagingInfo pi);
}
