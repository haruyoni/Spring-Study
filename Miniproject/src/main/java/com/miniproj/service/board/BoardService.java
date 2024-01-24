package com.miniproj.service.board;

import java.util.List;

import com.miniproj.domain.Board;
import com.miniproj.etc.UploadedFile;

public interface BoardService {

	// 전체 게시글 조회
	List<Board> getEntireBoard() throws Exception;

	// 게시글 저장
	void saveNewBoard(Board newBoard, List<UploadedFile> fileList) throws Exception;
}
	