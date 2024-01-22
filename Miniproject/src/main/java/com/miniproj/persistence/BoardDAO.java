package com.miniproj.persistence;

import java.util.List;

import com.miniproj.domain.Board;

public interface BoardDAO {

	// 전체 게시글 조회
	List<Board> selectAllBoard();

}
