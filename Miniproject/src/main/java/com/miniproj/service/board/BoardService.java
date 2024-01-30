package com.miniproj.service.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.miniproj.domain.Board;
import com.miniproj.domain.SearchCriteria;
import com.miniproj.domain.UploadedFile;

public interface BoardService {

	// 전체 게시글 조회
//	List<Board> getEntireBoard() throws Exception;

	// 게시글 저장
	void saveNewBoard(Board newBoard, List<UploadedFile> fileList) throws Exception;

	// no번 게시글 상세 조회
	Map<String, Object> getBoardByNo(int no, String ipAddr) throws Exception;

	// 페이지 번호에 해당하는 게시글 전체 조회
	Map<String, Object> getEntireBoard(int pageNo, SearchCriteria sc) throws Exception;
}
	