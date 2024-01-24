package com.miniproj.service.board;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.miniproj.domain.Board;
import com.miniproj.domain.PointLog;
import com.miniproj.etc.UploadedFile;
import com.miniproj.persistence.BoardDAO;
import com.miniproj.persistence.MemberDAO;
import com.miniproj.persistence.PointLogDAO;

@Repository
public class BoardServiceImpl implements BoardService {

	@Inject
	BoardDAO bDao;
	
	@Inject
	MemberDAO mDao;
	
	@Inject
	PointLogDAO plDao;
	
	@Override
	public List<Board> getEntireBoard() throws Exception {
	
		List<Board> lst = bDao.selectAllBoard();
		return lst;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveNewBoard(Board newBoard, List<UploadedFile> fileList) throws Exception {
		// 게시판 글 작성
		
		// 1) board 테이블에 글 내용 저장 (insert)
			// if 업로드파일이 있는 경우
			// 1)번에서 insert된 글의 no값을 얻어와서, 
			// 파일의 개수만큼 2)번 반복
			// 2) uploadedfile 테이블에 파일 정보를 저장 (insert) 
		
		// 3) member 테이블에 userpoint를 update
		// 4) pointlog테이블에 insert
		
		// 1) board 테이블에 글 내용 저장 (insert)
		if(bDao.insertNewBoard(newBoard)==1) {
			
			int boardNo = bDao.selectBoardNo();
			
			if(fileList.size()>0) { // 업로드파일이 있는 경우
			// 2) 1)번에서 insert된 글의 no값을 얻어와서, uploadedfile 테이블에 파일 정보를 저장 (insert)
				for(UploadedFile uf : fileList) {
					System.out.println("테이블에 저장될 uf"+ uf.toString());
					bDao.insertUploadedFile(boardNo, uf);
				}
			}
			
			// 3) member 테이블에 userpoint를 update
			mDao.updateUserPoint("게시물작성", newBoard.getWriter());
			
			// 4) pointlog 테이블에 insert
			plDao.insertPointLog(new PointLog(-1, null, "게시물작성", 2, newBoard.getWriter()));
			
		}
	}

}
