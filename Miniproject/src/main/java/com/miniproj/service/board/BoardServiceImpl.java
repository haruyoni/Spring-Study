package com.miniproj.service.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.miniproj.domain.Board;
import com.miniproj.domain.PointLog;
import com.miniproj.domain.ReadCountProcess;
import com.miniproj.domain.UploadedFile;
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
			plDao.insertPointLog(new PointLog(-1, null, "게시물작성", 2, newBoard.getWriter()));
//			if( == 1) {
//				// 4) pointlog 테이블에 insert
//				
//			}
			
			
		}
	}

	@Override
	public Map<String, Object> getBoardByNo(int no, String ipAddr) throws Exception{
//		💡 **해당 아이피 주소와 글번호가 같은 것이 없으면**
//
//		-> 해당 아이피주소가 해당 글을 **최초로 조회한 경우**
//
//		-> **아이피 주소**와 **글번호**와 **읽은 시간**을 **readcountprocess Table에 insert**
//		-> 해당 글번호의 readcount를 증가(update)
//		-> 해당 글을 가져옴(select)

//		**해당 아이피 주소와 글번호가 같은 것이 있으면**
//
//		1. 시간이 24시간이 지난 경우
//		-> 아이피 주소와 글번호와 읽은 시간을 readcountprocess Table에서 update
//		-> 해당 글번호의 readcount를 증가(update)
//		-> 해당 글을 가져옴(select)

//		1. **시간이 24시간이 지나지 않은 경우**
//		-> 해당 글을 가져옴(**select**)

// ======================================================================== //
		ReadCountProcess rcp = new ReadCountProcess(-1, ipAddr, no, null);
		int readCount = 0;
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 해당 아이피와 글번호 테이블에 있는지 없는지 판단 
		if (bDao.selectReadCountProcess(rcp)!=null){ // 조회한 적이 있음
			
			if(bDao.selectHourDiffReadTime(rcp)>23) { // 24시간 지난 경우
				// readTime을 readcountprocess테이블에서 update
				if(bDao.updateReadCountProcess(rcp) == 1) {
					// 해당 글번호의 readcount를 증가 (update)
					readCount = bDao.updateReadCount(no);
				}
			} else { // 24시간이 지나지 않은 경우
				readCount = 1;
			}
		} else { // 최초 조회
			// 아이피주소 글번호 읽은 시간을 readcountprocess테이블에 insert
			if(bDao.insertReadCountProcess(rcp)==1) {
				// 해당글의 조회수를 +1 증가 (update)
				readCount = bDao.updateReadCount(no);
			}
		}
		
		// 해당글을 조회
		if(readCount==1) {
			Board board = bDao.selectBoardByNo(no);
			List<UploadedFile> upFileList = bDao.selectUploadedFile(no);
			System.out.println(board.toString());
			System.out.println(upFileList.toString());
			
			result.put("board", board);
			result.put("upFileList", upFileList);
		}
		
		return result;
	}

}
