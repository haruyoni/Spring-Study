package com.miniproj.persistence;

import java.util.List;

import com.miniproj.domain.Board;
import com.miniproj.etc.UploadedFile;

public interface BoardDAO {

	// 전체 게시글 조회
	List<Board> selectAllBoard() throws Exception;
	
	int insertNewBoard(Board newBoard) throws Exception;

	int selectBoardNo() throws Exception;
	
	void insertUploadedFile(int boardNo, UploadedFile uf) throws Exception;
}
