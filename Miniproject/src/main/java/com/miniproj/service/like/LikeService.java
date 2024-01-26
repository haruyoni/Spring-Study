package com.miniproj.service.like;

import com.miniproj.domain.Like;

public interface LikeService {
	// 유저의 해당 게시물 좋아요 유무 가져오기
	boolean getIsLiked(int boardNo, String memberId);
	
	// 좋아요 추가
	int addLike(int boardNo, String memberId);
	
	// 좋아요 취소
	int removeLike(int boardNo, String memberId);
	
	
}
