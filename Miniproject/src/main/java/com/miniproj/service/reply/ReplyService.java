package com.miniproj.service.reply;

import java.util.List;

import com.miniproj.domain.Reply;

public interface ReplyService {
	// 모든 댓글 가져오기
	List<Reply> getAllReplies(int boardNo) throws Exception;
	
	// 새 댓글 저장
	boolean saveReply(Reply newReply) throws Exception;
	
}
