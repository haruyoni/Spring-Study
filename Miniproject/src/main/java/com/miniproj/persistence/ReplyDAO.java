package com.miniproj.persistence;

import java.util.List;

import com.miniproj.domain.Reply;

public interface ReplyDAO {
	
	// 모든 댓글 조회
	List<Reply> selectAllReplies(int boardNo) throws Exception;

	// 새 댓글 저장
	int insertNewReply(Reply newReply) throws Exception;
}
