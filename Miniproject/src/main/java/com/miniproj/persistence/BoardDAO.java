package com.miniproj.persistence;

import java.util.List;

import com.miniproj.domain.Board;
import com.miniproj.domain.ReadCountProcess;
import com.miniproj.domain.SearchCriteria;
import com.miniproj.domain.UploadedFile;
import com.miniproj.etc.PagingInfo;

public interface BoardDAO {

	// 전체 게시글 조회
	List<Board> selectAllBoard(PagingInfo pi) throws Exception;
	
	List<Board> selectAllBoard(PagingInfo pi, SearchCriteria sc) throws Exception;
	
	// 게시글 저장
	int insertNewBoard(Board newBoard) throws Exception;

	// 저장된 게시글의 no값 가져오기 
	int selectBoardNo() throws Exception;
	
	// 업로드된 파일 정보 저장
	void insertUploadedFile(int boardNo, UploadedFile uf) throws Exception;

	// 해당 아이피 주소와 글번호 같은 겂이 있는지 없는지 체크
	ReadCountProcess selectReadCountProcess(ReadCountProcess rcp) throws Exception;

	// ipAddr가 no번 글을 읽은 시간이 현재 시간과 몇시간 차인지 계산해 가져오기
	int selectHourDiffReadTime(ReadCountProcess rcp) throws Exception;

	// readcountprocess테이블에서 readTime을 업데이트
	int updateReadCountProcess(ReadCountProcess rcp) throws Exception;

	// 조회수 증가
	int updateReadCount(int no) throws Exception;

	int insertReadCountProcess(ReadCountProcess rcp) throws Exception;

	Board selectBoardByNo(int no) throws Exception;

	List<UploadedFile> selectUploadedFile(int no) throws Exception;
	
	// 전체 게시글 수
	int selectTotalPostCnt() throws Exception;

	int selectTotalPostCnt(SearchCriteria sc) throws Exception;

	int likeBoard(int boardNo, String who) throws Exception;

	int dislikeBoard(int boardNo, String who) throws Exception;

	int updateBoardLikeCount(int boardNo, int num) throws Exception;

	List<String> selectLikedUsers(int boardNo) throws Exception;

}
